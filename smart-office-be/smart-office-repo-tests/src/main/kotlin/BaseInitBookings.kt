package ru.otus.otuskotlin.smartoffice.repo.tests

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.models.*

abstract class BaseInitBookings(private val op: String): IInitObjects<OfficeBooking> {
    open val lockOld: OfficeBookingLock = OfficeBookingLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: OfficeBookingLock = OfficeBookingLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        userId: OfficeUserId = OfficeUserId("999"),
        floorId: OfficeFloorId = OfficeFloorId("floor_1"),
        roomId: OfficeRoomId = OfficeRoomId("room_1"),
        workspaceId: OfficeWorkspaceId = OfficeWorkspaceId("23456"),
        status: OfficeBookingStatus = OfficeBookingStatus.ACTIVE,
        startTime: Instant = Instant.parse("2026-10-10T10:00:00Z"),
        endTime: Instant = Instant.parse("2026-10-10T15:00:00Z"),
        lock: OfficeBookingLock = lockOld,
    ) = OfficeBooking(
        id = OfficeBookingId("booking-repo-$op-$suf"),
        userId = userId,
        floorId = floorId,
        roomId = roomId,
        workspaceId = workspaceId,
        startTime = startTime,
        endTime = endTime,
        status = status,
        lock = lock,
    )
}
