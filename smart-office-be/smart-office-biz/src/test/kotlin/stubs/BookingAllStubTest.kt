package ru.otus.otuskotlin.smartoffice.biz.stub

import kotlinx.datetime.Instant
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class BookingAllStubTest {

    private val processor = OfficeBookingProcessor()
    val userId = OfficeUserId("8gj4")
    val startTime = Instant.parse("2022-01-30T09:00:00Z")
    val endTime = Instant.parse("2025-01-30T18:00:00Z")
    val status = OfficeBookingStatus.ACTIVE

    val filter = OfficeBookingFilter(
        userId = userId,
        startTime = startTime,
        endTime = endTime,
        status = status,
    )


    @Test
    fun all() = runTest {

        val ctx = OfficeContext(
            command = OfficeCommand.ALL,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.SUCCESS,
            bookingFilterRequest = filter,
        )
        processor.exec(ctx)

        assertTrue(ctx.bookingsResponse.size > 1)
        val first = ctx.bookingsResponse.firstOrNull() ?: fail("Empty response list")
        with (filter) {
            assertEquals(userId, first.userId)
            assertTrue(startTime >= first.startTime)
            assertTrue(endTime <= first.endTime)
            assertEquals(status, first.status)
        }
    }


    @Test
    fun badUserId() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.ALL,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.BAD_USER_ID,
            bookingFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(OfficeBooking(), ctx.bookingResponse)
        assertEquals("userId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }


//    @Test
//    fun badStartTime() = runTest {
//        val ctx = OfficeContext(
//            command = OfficeCommand.ALL,
//            state = OfficeState.NONE,
//            workMode = OfficeWorkMode.STUB,
//            stubCase = OfficeStubs.BAD_START_TIME,
//            bookingFilterRequest = filter,
//        )
//        processor.exec(ctx)
//        assertEquals(OfficeBooking(), ctx.bookingResponse)
//        assertEquals("startTime", ctx.errors.firstOrNull()?.field)
//        assertEquals("validation", ctx.errors.firstOrNull()?.group)
//    }

//    @Test
//    fun badEndTime() = runTest {
//        val ctx = OfficeContext(
//            command = OfficeCommand.ALL,
//            state = OfficeState.NONE,
//            workMode = OfficeWorkMode.STUB,
//            stubCase = OfficeStubs.BAD_END_TIME,
//            bookingFilterRequest = filter,
//        )
//        processor.exec(ctx)
//        assertEquals(OfficeBooking(), ctx.bookingResponse)
//        assertEquals("endTime", ctx.errors.firstOrNull()?.field)
//        assertEquals("validation", ctx.errors.firstOrNull()?.group)
//    }

    @Test
    fun badTimeRange() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.ALL,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.BAD_TIME_RANGE,
            bookingFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(OfficeBooking(), ctx.bookingResponse)
        assertEquals("validation-timeRange", ctx.errors.firstOrNull()?.code)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

//    @Test
//    fun badStatus() = runTest {
//        val ctx = OfficeContext(
//            command = OfficeCommand.ALL,
//            state = OfficeState.NONE,
//            workMode = OfficeWorkMode.STUB,
//            stubCase = OfficeStubs.BAD_STATUS,
//            bookingFilterRequest = filter,
//        )
//        processor.exec(ctx)
//        assertEquals(OfficeBooking(), ctx.bookingResponse)
//        assertEquals("status", ctx.errors.firstOrNull()?.field)
//        assertEquals("validation", ctx.errors.firstOrNull()?.group)
//    }


    @Test
    fun databaseError() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.ALL,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.DB_ERROR,
            bookingFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(OfficeBooking(), ctx.bookingResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.ALL,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.CANNOT_DELETE,
            bookingFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(OfficeBooking(), ctx.bookingResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
