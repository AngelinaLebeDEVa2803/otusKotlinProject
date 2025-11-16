package ru.otus.otuskotlin.smartoffice.biz.validation

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


fun validationTestUserIdCorrect(command: OfficeCommand, processor: OfficeBookingProcessor) = runTest {
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
            startTime = Instant.parse("2026-03-30T09:00:00Z"),
            endTime = Instant.parse("2026-03-30T18:00:00Z"),
            status = OfficeBookingStatus.ACTIVE,
            lock = OfficeBookingLock("123"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(OfficeState.FAILING, ctx.state)
}

fun validationTestUserIdTrim(command: OfficeCommand, processor: OfficeBookingProcessor) = runTest {
    val ctx = OfficeContext(
        command = command,
        state = OfficeState.NONE,
        workMode = OfficeWorkMode.TEST,
        bookingRequest = OfficeBooking(
            id = OfficeBookingId("9637f27d-5b70-498d-8b4d-9b1c94dc9c6e"),
            userId = OfficeUserId(" \n\t     123        \n\t "),
            floorId = OfficeFloorId("floor_6"),
            roomId = OfficeRoomId("room_404"),
            workspaceId = OfficeWorkspaceId("009"),
            startTime = Instant.parse("2026-03-30T09:00:00Z"),
            endTime = Instant.parse("2026-03-30T18:00:00Z"),
            status = OfficeBookingStatus.ACTIVE,
            lock = OfficeBookingLock("123"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(OfficeState.FAILING, ctx.state)
}

fun validationTestUserIdEmpty(command: OfficeCommand, processor: OfficeBookingProcessor) = runTest {
    val ctx = OfficeContext(
        command = command,
        state = OfficeState.NONE,
        workMode = OfficeWorkMode.TEST,
        bookingRequest = OfficeBooking(
            id = OfficeBookingId("9637f27d-5b70-498d-8b4d-9b1c94dc9c6e"),
            userId = OfficeUserId(""),
            floorId = OfficeFloorId("floor_6"),
            roomId = OfficeRoomId("room_404"),
            workspaceId = OfficeWorkspaceId("009"),
            startTime = Instant.parse("2026-03-30T09:00:00Z"),
            endTime = Instant.parse("2026-03-30T18:00:00Z"),
            status = OfficeBookingStatus.ACTIVE,
            lock = OfficeBookingLock("123"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(OfficeState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("userId", error?.field)
    assertContains(error?.message ?: "", "userId")
}

fun validationTestUserIdFormat(command: OfficeCommand, processor: OfficeBookingProcessor) = runTest {
    val ctx = OfficeContext(
        command = command,
        state = OfficeState.NONE,
        workMode = OfficeWorkMode.TEST,
        bookingRequest = OfficeBooking(
            id = OfficeBookingId("9637f27d-5b70-498d-8b4d-9b1c94dc9c6e"),
            userId = OfficeUserId("!@#\\\$%^&*(),.{}"),
            floorId = OfficeFloorId("floor_6"),
            roomId = OfficeRoomId("room_404"),
            workspaceId = OfficeWorkspaceId("009"),
            startTime = Instant.parse("2026-03-30T09:00:00Z"),
            endTime = Instant.parse("2026-03-30T18:00:00Z"),
            status = OfficeBookingStatus.ACTIVE,
            lock = OfficeBookingLock("123"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(OfficeState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("userId", error?.field)
    assertContains(error?.message ?: "", "userId")
}

