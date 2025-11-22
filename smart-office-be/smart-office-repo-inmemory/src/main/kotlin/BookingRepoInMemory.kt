package ru.otus.otuskotlin.smartoffice.repo.inmemory

import kotlinx.datetime.Instant
import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.repo.*
import ru.otus.otuskotlin.smartoffice.common.repo.exceptions.RepoEmptyLockException
import ru.otus.otuskotlin.smartoffice.repo.common.IRepoBookingInitializable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class BookingRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : BookingRepoBase(), IRepoBooking, IRepoBookingInitializable {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, BookingEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(bookings: Collection<OfficeBooking>) = bookings.map { booking ->
        val entity = BookingEntity(booking)
        require(entity.id != null)
        cache.put(entity.id, entity)
        booking
    }

    override suspend fun createBooking(rq: DbBookingRequest): IDbBookingResponse = tryBookingMethod {
        val key = randomUuid()
        val booking = rq.booking.copy(id = OfficeBookingId(key), lock = OfficeBookingLock(randomUuid()))
        val entity = BookingEntity(booking)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbBookingResponseOk(booking)
    }

    override suspend fun readBooking(rq: DbBookingIdRequest): IDbBookingResponse = tryBookingMethod {
        val key = rq.id.takeIf { it != OfficeBookingId.NONE }?.asString() ?: return@tryBookingMethod errorEmptyId
        mutex.withLock {
            cache.get(key)
                ?.let {
                    DbBookingResponseOk(it.toInternal())
                } ?: errorNotFound(rq.id)
        }
    }

    override suspend fun updateBooking(rq: DbBookingRequest): IDbBookingResponse = tryBookingMethod {
        val rqBooking = rq.booking
        val id = rqBooking.id.takeIf { it != OfficeBookingId.NONE } ?: return@tryBookingMethod errorEmptyId
        val key = id.asString()
        val oldLock = rqBooking.lock.takeIf { it != OfficeBookingLock.NONE } ?: return@tryBookingMethod errorEmptyLock(id)

        mutex.withLock {
            val oldBooking = cache.get(key)?.toInternal()
            when {
                oldBooking == null -> errorNotFound(id)
                oldBooking.lock == OfficeBookingLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldBooking.lock != oldLock -> errorRepoConcurrency(oldBooking, oldLock)
                else -> {
                    val newBooking = rqBooking.copy(lock = OfficeBookingLock(randomUuid()))
                    val entity = BookingEntity(newBooking)
                    cache.put(key, entity)
                    DbBookingResponseOk(newBooking)
                }
            }
        }
    }


    override suspend fun deleteBooking(rq: DbBookingIdRequest): IDbBookingResponse = tryBookingMethod {
        val id = rq.id.takeIf { it != OfficeBookingId.NONE } ?: return@tryBookingMethod errorEmptyId
        val key = id.asString()
        val oldLock = rq.lock.takeIf { it != OfficeBookingLock.NONE } ?: return@tryBookingMethod errorEmptyLock(id)

        mutex.withLock {
            val oldBooking = cache.get(key)?.toInternal()
            when {
                oldBooking == null -> errorNotFound(id)
                oldBooking.lock == OfficeBookingLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldBooking.lock != oldLock -> errorRepoConcurrency(oldBooking, oldLock)
                else -> {
                    cache.invalidate(key)
                    DbBookingResponseOk(oldBooking)
                }
            }
        }
    }

    /**
     * Отображение бронирований по фильтру.
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun allBooking(rq: DbBookingFilterRequest): IDbBookingsResponse = tryBookingsMethod {
        val result: List<OfficeBooking> = cache.asMap().asSequence()
            .filter { entry ->
                rq.userId.takeIf { it != OfficeUserId.NONE }?.let {
                    it.asString() == entry.value.userId
                } ?: true
            }
            .filter { entry ->
                rq.status.takeIf { it != OfficeBookingStatus.NONE }?.let {
                    it.name == entry.value.status
                } ?: true
            }
            .filter { entry ->
                rq.startTime.takeIf { it != Instant.NONE }?.let {
                    it <= entry.value.startTime
                } ?: true
            }
            .filter { entry ->
                rq.endTime.takeIf { it != Instant.NONE }?.let {
                    it >= entry.value.endTime
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbBookingsResponseOk(result)
    }
}
