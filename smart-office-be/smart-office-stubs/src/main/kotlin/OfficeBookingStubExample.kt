package ru.otus.otuskotlin.smartoffice.stubs

import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.hours
import ru.otus.otuskotlin.smartoffice.common.models.*

object OfficeBookingStubExample {
    val MY_BOOKING1: OfficeBooking
        get() = OfficeBooking(
            id = OfficeBookingId("59de1f8e-c5ab-42f8-92e3-9cf3e47e6641"),
            userId = OfficeUserId("user1"),
            floorId = OfficeFloorId("floor_2"),
            roomId = OfficeRoomId("room_418"),
            workspaceId = OfficeWorkspaceId("007"),
            startTime = Instant.parse("2027-09-01T10:00:00Z"),
            endTime = Instant.parse("2027-09-01T19:00:00Z"),
            status = OfficeBookingStatus.ACTIVE,
            lock = OfficeBookingLock("123"),
            permissionsClient = mutableSetOf(
                OfficeBookingPermissions.READ,
                OfficeBookingPermissions.UPDATE,
                OfficeBookingPermissions.DELETE,
            )
        )
    val MY_BOOKING2 = MY_BOOKING1.copy(id = OfficeBookingId("002"),
                                       startTime = MY_BOOKING1.startTime + 24.hours,
                                       endTime = MY_BOOKING1.endTime + 24.hours)
}
