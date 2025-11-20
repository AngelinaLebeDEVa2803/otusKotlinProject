package ru.otus.otuskotlin.smartoffice.backend.repo.tests

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.common.models.OfficeUserId
import ru.otus.otuskotlin.smartoffice.common.repo.*
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class BookingRepositoryMockTest {
    private val repo = BookingRepositoryMock(
        invokeCreateBooking = { DbBookingResponseOk(OfficeBookingStub.prepareResult { userId = OfficeUserId("user4create") }) },
        invokeReadBooking = { DbBookingResponseOk(OfficeBookingStub.prepareResult { userId = OfficeUserId("user4read") }) },
        invokeUpdateBooking = { DbBookingResponseOk(OfficeBookingStub.prepareResult {  userId = OfficeUserId("user4update") }) },
        invokeDeleteBooking = { DbBookingResponseOk(OfficeBookingStub.prepareResult { userId = OfficeUserId("user4delete") }) },
        invokeAllBooking = { DbBookingsResponseOk(listOf(OfficeBookingStub.prepareResult { userId = OfficeUserId("user4all") })) },
    )

    @Test
    fun mockCreate() = runTest {
        val result = repo.createBooking(DbBookingRequest(OfficeBooking()))
        assertIs<DbBookingResponseOk>(result)
        assertEquals("user4create", result.data.userId.asString())
    }

    @Test
    fun mockRead() = runTest {
        val result = repo.readBooking(DbBookingIdRequest(OfficeBooking()))
        assertIs<DbBookingResponseOk>(result)
        assertEquals("user4read", result.data.userId.asString())
    }

    @Test
    fun mockUpdate() = runTest {
        val result = repo.updateBooking(DbBookingRequest(OfficeBooking()))
        assertIs<DbBookingResponseOk>(result)
        assertEquals("user4update", result.data.userId.asString())
    }

    @Test
    fun mockDelete() = runTest {
        val result = repo.deleteBooking(DbBookingIdRequest(OfficeBooking()))
        assertIs<DbBookingResponseOk>(result)
        assertEquals("user4delete", result.data.userId.asString())
    }

    @Test
    fun mockAll() = runTest {
        val result = repo.allBooking(DbBookingFilterRequest())
        assertIs<DbBookingsResponseOk>(result)
        assertEquals("user4all", result.data.first().userId.asString())
    }

}
