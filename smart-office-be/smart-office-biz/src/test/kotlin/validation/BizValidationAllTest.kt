package ru.otus.otuskotlin.smartoffice.biz.validation

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.NONE
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizValidationSearchTest: BaseBizValidationTest() {
    override val command = OfficeCommand.ALL

    @Test
    fun correctEmpty() = runTest {
        val ctx = OfficeContext(
            command = command,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.TEST,
            bookingFilterRequest = OfficeBookingFilter(userId = OfficeUserId("123"),
                startTime = Instant.NONE,
                endTime = Instant.NONE,
                status = OfficeBookingStatus.NONE)
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(OfficeState.FAILING, ctx.state)
    }

    @Test
    fun emptyUserIdFilter() = runTest {
        val ctx = OfficeContext(
            command = command,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.TEST,
//            bookingFilterRequest = OfficeBookingFilter(userId = OfficeUserId(""),
//                startTime = Instant.NONE,
//                endTime = Instant.NONE,
//                status = OfficeBookingStatus.NONE)
            bookingFilterRequest = OfficeBookingFilter()
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(OfficeState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("userId", error?.field)
        assertContains(error?.message ?: "", "userId")
    }

    @Test
    fun incorrectStartTimeFilter() = runTest {
        val ctx = OfficeContext(
            command = command,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.TEST,
            bookingFilterRequest = OfficeBookingFilter(userId = OfficeUserId("123"),
                startTime = Instant.NONE,
                endTime = Instant.parse("2025-02-02T09:00:00Z"),
                status = OfficeBookingStatus.NONE)
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(OfficeState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("startTime", error?.field)
        assertContains(error?.message ?: "", "startTime")
    }

    @Test
    fun incorrectEndTimeFilter() = runTest {
        val ctx = OfficeContext(
            command = command,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.TEST,
            bookingFilterRequest = OfficeBookingFilter(userId = OfficeUserId("123"),
                startTime = Instant.parse("2025-02-02T09:00:00Z"),
                endTime = Instant.NONE,
                status = OfficeBookingStatus.NONE)
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(OfficeState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("endTime", error?.field)
        assertContains(error?.message ?: "", "endTime")
    }

    @Test
    fun incorrectTimeRangeFilter() = runTest {
        val ctx = OfficeContext(
            command = command,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.TEST,
            bookingFilterRequest = OfficeBookingFilter(userId = OfficeUserId("123"),
                startTime = Instant.parse("2025-02-02T09:00:00Z"),
                endTime = Instant.parse("2020-02-02T09:00:00Z"),
                status = OfficeBookingStatus.NONE)
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(OfficeState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("startTime_endTime", error?.field)
        assertContains(error?.message ?: "", "range")
    }

    @Test
    fun correctTimeRange() = runTest {
        val ctx = OfficeContext(
            command = command,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.TEST,
            bookingFilterRequest = OfficeBookingFilter(userId = OfficeUserId("123"),
                startTime = Instant.parse("2025-02-01T09:00:00Z"),
                endTime = Instant.parse("2025-02-07T09:00:00Z"),
                status = OfficeBookingStatus.NONE)
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(OfficeState.FAILING, ctx.state)
    }
}
