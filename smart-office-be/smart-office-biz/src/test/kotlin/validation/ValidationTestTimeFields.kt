package ru.otus.otuskotlin.smartoffice.biz.validation

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


fun validationTestUndefinedStartTime(command: OfficeCommand, processor: OfficeBookingProcessor) = runTest {
    val ctx = OfficeContext(
        command = command,
        state = OfficeState.NONE,
        workMode = OfficeWorkMode.TEST,
        bookingRequest = OfficeBooking(
            id = OfficeBookingId("9637f27d-5b70-498d-8b4d-9b1c94dc9c6e"),
            userId = OfficeUserId("123"),
            floorId = OfficeFloorId("floor_6"),
            roomId = OfficeRoomId("room_404"),
            workspaceId = OfficeWorkspaceId("009"),
            startTime = Instant.NONE,
            endTime = Instant.parse("2026-03-30T18:00:00Z"),
            status = OfficeBookingStatus.ACTIVE,
            lock = OfficeBookingLock("123"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(OfficeState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("startTime", error?.field)
    assertContains(error?.message ?: "", "startTime")
}

fun validationTestUndefinedEndTime(command: OfficeCommand, processor: OfficeBookingProcessor) = runTest {
    val ctx = OfficeContext(
        command = command,
        state = OfficeState.NONE,
        workMode = OfficeWorkMode.TEST,
        bookingRequest = OfficeBooking(
            id = OfficeBookingId("9637f27d-5b70-498d-8b4d-9b1c94dc9c6e"),
            userId = OfficeUserId("123"),
            floorId = OfficeFloorId("floor_6"),
            roomId = OfficeRoomId("room_404"),
            workspaceId = OfficeWorkspaceId("009"),
            startTime =  Instant.parse("2026-03-30T09:00:00Z"),
            endTime = Instant.NONE,
            status = OfficeBookingStatus.ACTIVE,
            lock = OfficeBookingLock("123"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(OfficeState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("endTime", error?.field)
    assertContains(error?.message ?: "", "endTime")
}

fun validationTestIncorrectTimeRange(command: OfficeCommand, processor: OfficeBookingProcessor) = runTest {
    val ctx = OfficeContext(
        command = command,
        state = OfficeState.NONE,
        workMode = OfficeWorkMode.TEST,
        bookingRequest = OfficeBooking(
            id = OfficeBookingId("9637f27d-5b70-498d-8b4d-9b1c94dc9c6e"),
            userId = OfficeUserId("123"),
            floorId = OfficeFloorId("floor_6"),
            roomId = OfficeRoomId("room_404"),
            workspaceId = OfficeWorkspaceId("009"),
            startTime =  Instant.parse("2026-03-30T09:00:00Z"),
            endTime = Instant.parse("2025-03-30T09:00:00Z"),
            status = OfficeBookingStatus.ACTIVE,
            lock = OfficeBookingLock("123"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(OfficeState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("startTime_endTime", error?.field)
    assertContains(error?.message ?: "", "range")
}

fun validationTestLongBooking(command: OfficeCommand, processor: OfficeBookingProcessor) = runTest {
    val ctx = OfficeContext(
        command = command,
        state = OfficeState.NONE,
        workMode = OfficeWorkMode.TEST,
        bookingRequest = OfficeBooking(
            id = OfficeBookingId("9637f27d-5b70-498d-8b4d-9b1c94dc9c6e"),
            userId = OfficeUserId("123"),
            floorId = OfficeFloorId("floor_6"),
            roomId = OfficeRoomId("room_404"),
            workspaceId = OfficeWorkspaceId("009"),
            startTime =  Instant.parse("2026-03-30T09:00:00Z"),
            endTime = Instant.parse("2026-04-30T09:00:00Z"),
            status = OfficeBookingStatus.ACTIVE,
            lock = OfficeBookingLock("123"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(OfficeState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("startTime_endTime", error?.field)
    assertContains(error?.message ?: "", "more")
}

fun validationTestShortBooking(command: OfficeCommand, processor: OfficeBookingProcessor) = runTest {
    val ctx = OfficeContext(
        command = command,
        state = OfficeState.NONE,
        workMode = OfficeWorkMode.TEST,
        bookingRequest = OfficeBooking(
            id = OfficeBookingId("9637f27d-5b70-498d-8b4d-9b1c94dc9c6e"),
            userId = OfficeUserId("123"),
            floorId = OfficeFloorId("floor_6"),
            roomId = OfficeRoomId("room_404"),
            workspaceId = OfficeWorkspaceId("009"),
            startTime =  Instant.parse("2026-03-30T09:00:00Z"),
            endTime = Instant.parse("2026-03-30T09:20:00Z"),
            status = OfficeBookingStatus.ACTIVE,
            lock = OfficeBookingLock("123"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(OfficeState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("startTime_endTime", error?.field)
    assertContains(error?.message ?: "", "less")
}