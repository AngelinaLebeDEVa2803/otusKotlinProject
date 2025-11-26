package ru.otus.otuskotlin.smartoffice.repo.pgjvm


import kotlinx.datetime.Instant
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import kotlinx.datetime.toJavaInstant
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.helpers.asOfficeError
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.*
import ru.otus.otuskotlin.smartoffice.common.repo.errorNotFound
import ru.otus.otuskotlin.smartoffice.repo.common.IRepoBookingInitializable

class RepoBookingSql(
    properties: SqlProperties,
    private val randomUuid: () -> String = { uuid4().toString() }
) : IRepoBooking, IRepoBookingInitializable {
    private val bookingTable = BookingTable("${properties.schema}.${properties.table}")

    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )

    fun clear(): Unit = transaction(conn) {
        bookingTable.deleteAll()
    }

    private fun saveObj(booking: OfficeBooking): OfficeBooking = transaction(conn) {
        val res = bookingTable
            .insert {
                it.to(booking, randomUuid)
            }
            .resultedValues
            ?.map { bookingTable.from(it) }
        res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    private suspend inline fun <T> transactionWrapper(crossinline block: () -> T, crossinline handle: (Exception) -> T): T =
        withContext(Dispatchers.IO) {
            try {
                transaction(conn) {
                    block()
                }
            } catch (e: Exception) {
                handle(e)
            }
        }

    private suspend inline fun transactionWrapper(crossinline block: () -> IDbBookingResponse): IDbBookingResponse =
        transactionWrapper(block) { DbBookingResponseErr(it.asOfficeError()) }

    override fun save(bookings: Collection<OfficeBooking>): Collection<OfficeBooking> = bookings.map { saveObj(it) }
    override suspend fun createBooking(rq: DbBookingRequest): IDbBookingResponse = transactionWrapper {
        DbBookingResponseOk(saveObj(rq.booking))
    }

    private fun read(id: OfficeBookingId): IDbBookingResponse {
        val res = bookingTable.selectAll().where {
            bookingTable.id eq id.asString()
        }.singleOrNull() ?: return errorNotFound(id)
        return DbBookingResponseOk(bookingTable.from(res))
    }

    override suspend fun readBooking(rq: DbBookingIdRequest): IDbBookingResponse = transactionWrapper { read(rq.id) }

    private suspend fun update(
        id: OfficeBookingId,
        lock: OfficeBookingLock,
        block: (OfficeBooking) -> IDbBookingResponse
    ): IDbBookingResponse =
        transactionWrapper {
            if (id == OfficeBookingId.NONE) return@transactionWrapper errorEmptyId

            val current = bookingTable.selectAll().where { bookingTable.id eq id.asString() }
                .singleOrNull()
                ?.let { bookingTable.from(it) }

            when {
                current == null -> errorNotFound(id)
                current.lock != lock -> errorRepoConcurrency(current, lock)
                else -> block(current)
            }
        }


    override suspend fun updateBooking(rq: DbBookingRequest): IDbBookingResponse = update(rq.booking.id, rq.booking.lock) {
        bookingTable.updateReturning(where = { bookingTable.id eq rq.booking.id.asString() }) {
            it.to(rq.booking.copy(lock = OfficeBookingLock(randomUuid())), randomUuid)
        }.singleOrNull()
            ?.let { DbBookingResponseOk(bookingTable.from(it)) }
            ?: errorNotFound(rq.booking.id)
    }

    override suspend fun deleteBooking(rq: DbBookingIdRequest): IDbBookingResponse = update(rq.id, rq.lock) {
        bookingTable.deleteWhere { id eq rq.id.asString() }
        DbBookingResponseOk(it)
    }

    override suspend fun allBooking(rq: DbBookingFilterRequest): IDbBookingsResponse =
        transactionWrapper({
            val res = bookingTable.selectAll().where {
                buildList {
                    add(Op.TRUE)
                    if (rq.userId != OfficeUserId.NONE) {
                        add(bookingTable.userId eq rq.userId.asString())
                    }
                    if (rq.status != OfficeBookingStatus.NONE) {
                        add(bookingTable.status eq rq.status)
                    }
                    if (rq.startTime != Instant.NONE) {
                        add(bookingTable.startTime greaterEq rq.startTime.toJavaInstant())
                    }
                    if (rq.endTime != Instant.NONE) {
                        add(bookingTable.endTime lessEq rq.endTime.toJavaInstant())
                    }
                }.reduce { a, b -> a and b }
            }
            DbBookingsResponseOk(data = res.map { bookingTable.from(it) })
        }, {
            DbBookingsResponseErr(it.asOfficeError())
        })
}
