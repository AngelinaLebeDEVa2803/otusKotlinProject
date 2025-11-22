package ru.otus.otuskotlin.smartoffice.app.spring.repo

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.slot
import kotlinx.datetime.Instant
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import ru.otus.otuskotlin.smartoffice.app.spring.config.BookingConfig
import ru.otus.otuskotlin.smartoffice.app.spring.controllers.BookingControllerV1Fine
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingFilterRequest
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingIdRequest
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingRequest
import ru.otus.otuskotlin.smartoffice.common.repo.IRepoBooking
import ru.otus.otuskotlin.smartoffice.repo.common.BookingRepoInitialized
import ru.otus.otuskotlin.smartoffice.repo.inmemory.BookingRepoInMemory
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub
import kotlin.test.Test

// Temporary simple test with stubs
@WebFluxTest(BookingControllerV1Fine::class, BookingConfig::class)
internal class BookingRepoInMemoryV1Test : BookingRepoBaseV1Test() {
    @Autowired
    override lateinit var webClient: WebTestClient

    @MockkBean
    @Qualifier("testRepo")
    lateinit var testTestRepo: IRepoBooking

    @BeforeEach
    fun tearUp() {
        val slotBooking = slot<DbBookingRequest>()
        val slotId = slot<DbBookingIdRequest>()
        val slotFl = slot<DbBookingFilterRequest>()
        val repo = BookingRepoInitialized(
            repo = BookingRepoInMemory(randomUuid = { uuidNew }),
            initObjects = OfficeBookingStub.prepareBookingsList(userId =  OfficeUserId("test_all_spring"),
                startTime = Instant.parse("2026-07-01T09:00:00Z"),
                endTime = Instant.parse("2026-07-01T19:00:00Z"),
                status =  OfficeBookingStatus.CANCELLED) + OfficeBookingStub.get()
        )
        coEvery { testTestRepo.createBooking(capture(slotBooking)) } coAnswers { repo.createBooking(slotBooking.captured) }
        coEvery { testTestRepo.readBooking(capture(slotId)) } coAnswers { repo.readBooking(slotId.captured) }
        coEvery { testTestRepo.updateBooking(capture(slotBooking)) } coAnswers { repo.updateBooking(slotBooking.captured) }
        coEvery { testTestRepo.deleteBooking(capture(slotId)) } coAnswers { repo.deleteBooking(slotId.captured) }
        coEvery { testTestRepo.allBooking(capture(slotFl)) } coAnswers { repo.allBooking(slotFl.captured) }
    }

    @Test
    override fun createBooking() = super.createBooking()

    @Test
    override fun readBooking() = super.readBooking()

    @Test
    override fun updateBooking() = super.updateBooking()

    @Test
    override fun deleteBooking() = super.deleteBooking()

    @Test
    override fun allBooking() = super.allBooking()

}
