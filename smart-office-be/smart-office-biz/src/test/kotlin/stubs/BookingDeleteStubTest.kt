package ru.otus.otuskotlin.smartoffice.biz.stubs

import kotlinx.datetime.Instant
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BookingDeleteStubTest {

    private val processor = OfficeBookingProcessor()
    val bookingId = OfficeBookingId("00200")
    val userId = OfficeUserId("8gj4")
    val floorId = OfficeFloorId("floor_9")
    val roomId = OfficeRoomId("room_404")
    val workspaceId = OfficeWorkspaceId("009")
    val startTime = Instant.parse("2025-01-30T09:00:00Z")
    val endTime = Instant.parse("2025-01-30T18:00:00Z")
    val status = OfficeBookingStatus.ACTIVE


    @Test
    fun delete() = runTest {

        val ctx = OfficeContext(
            command = OfficeCommand.DELETE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.SUCCESS,
            bookingRequest = OfficeBooking(
                id = bookingId,
                userId = userId,
                floorId = floorId,
                roomId = roomId,
                workspaceId = workspaceId,
                startTime = startTime,
                endTime = endTime,
                status = status,
            ),
        )
        processor.exec(ctx)
        assertNotEquals(OfficeBookingStub.get().id, ctx.bookingResponse.id)
        assertEquals(bookingId, ctx.bookingResponse.id)
        assertEquals(OfficeBookingStub.get().userId, ctx.bookingResponse.userId)
        assertEquals(OfficeBookingStub.get().floorId, ctx.bookingResponse.floorId)
        assertEquals(OfficeBookingStub.get().roomId, ctx.bookingResponse.roomId)
        assertEquals(OfficeBookingStub.get().workspaceId, ctx.bookingResponse.workspaceId)
        assertEquals(OfficeBookingStub.get().startTime, ctx.bookingResponse.startTime)
        assertEquals(OfficeBookingStub.get().endTime, ctx.bookingResponse.endTime)
        assertEquals(OfficeBookingStub.get().status, ctx.bookingResponse.status)
    }
    @Test
    fun badId() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.DELETE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.BAD_ID,
            bookingRequest = OfficeBooking(
                id = bookingId,
                userId = userId,
                floorId = floorId,
                roomId = roomId,
                workspaceId = workspaceId,
                startTime = startTime,
                endTime = endTime,
                status = status,
            ),
        )
        processor.exec(ctx)
        assertEquals(OfficeBooking(), ctx.bookingResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun stubTestCannotDelete() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.DELETE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.CANNOT_DELETE,
            bookingRequest = OfficeBooking(
                id = bookingId,
                userId = userId,
                floorId = floorId,
                roomId = roomId,
                workspaceId = workspaceId,
                startTime = startTime,
                endTime = endTime,
                status = status,
            ),
        )
        processor.exec(ctx)
        assertEquals(OfficeBooking(), ctx.bookingResponse)
        assertEquals("logic-cannotDelete", ctx.errors.firstOrNull()?.code)
        assertEquals("logic", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun stubTestNotFound() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.DELETE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.NOT_FOUND,
            bookingRequest = OfficeBooking(
                id = bookingId,
                userId = userId,
                floorId = floorId,
                roomId = roomId,
                workspaceId = workspaceId,
                startTime = startTime,
                endTime = endTime,
                status = status,
            ),
        )
        processor.exec(ctx)
        assertEquals(OfficeBooking(), ctx.bookingResponse)
        assertEquals("logic-notFound", ctx.errors.firstOrNull()?.code)
        assertEquals("logic", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.DELETE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.DB_ERROR,
            bookingRequest = OfficeBooking(
                id = bookingId,
                userId = userId,
                floorId = floorId,
                roomId = roomId,
                workspaceId = workspaceId,
                startTime = startTime,
                endTime = endTime,
                status = status,
            ),
        )
        processor.exec(ctx)
        assertEquals(OfficeBooking(), ctx.bookingResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.DELETE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.CANNOT_UPDATE,
            bookingRequest = OfficeBooking(
                id = bookingId,
                userId = userId,
                floorId = floorId,
                roomId = roomId,
                workspaceId = workspaceId,
                startTime = startTime,
                endTime = endTime,
                status = status,
            ),
        )
        processor.exec(ctx)
        assertEquals(OfficeBooking(), ctx.bookingResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
