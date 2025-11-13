package ru.otus.otuskotlin.smartoffice.biz.stub

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

class BookingCreateStubTest {

    private val processor = OfficeBookingProcessor()
    val bookingId = OfficeBookingId("00100")
    val userId = OfficeUserId("123")
    val floorId = OfficeFloorId("floor_6")
    val roomId = OfficeRoomId("room_404")
    val workspaceId = OfficeWorkspaceId("009")
    val startTime = Instant.parse("2025-03-30T09:00:00Z")
    val endTime = Instant.parse("2025-03-30T18:00:00Z")
    val status = OfficeBookingStatus.ACTIVE


    @Test
    fun create() = runTest {

        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
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
        assertEquals(OfficeBookingStub.get().id, ctx.bookingResponse.id)
        assertNotEquals(bookingId, ctx.bookingResponse.id)
        assertEquals(userId, ctx.bookingResponse.userId)
        assertEquals(floorId, ctx.bookingResponse.floorId)
        assertEquals(roomId, ctx.bookingResponse.roomId)
        assertEquals(workspaceId, ctx.bookingResponse.workspaceId)
        assertEquals(startTime, ctx.bookingResponse.startTime)
        assertEquals(endTime, ctx.bookingResponse.endTime)
        assertEquals(status, ctx.bookingResponse.status)
    }

    @Test
    fun badUserId() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.BAD_USER_ID,
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
        assertEquals("userId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badFloorId() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.BAD_FLOOR_ID,
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
        assertEquals("floorId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badRoomId() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.BAD_ROOM_ID,
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
        assertEquals("roomId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badWorkspaceId() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.BAD_WORKSPACE_ID,
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
        assertEquals("workspaceId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badStartTime() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.BAD_START_TIME,
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
        assertEquals("startTime", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badEndTime() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.BAD_END_TIME,
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
        assertEquals("endTime", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badTimeRange() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.BAD_TIME_RANGE,
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
        assertEquals("validation-timeRange", ctx.errors.firstOrNull()?.code)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badStatus() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.BAD_STATUS,
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
        assertEquals("status", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun stubTestCannotCreate() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
            state = OfficeState.NONE,
            workMode = OfficeWorkMode.STUB,
            stubCase = OfficeStubs.CANNOT_CREATE,
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
        assertEquals("logic-cannotCreate", ctx.errors.firstOrNull()?.code)
        assertEquals("logic", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = OfficeContext(
            command = OfficeCommand.CREATE,
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
            command = OfficeCommand.CREATE,
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
