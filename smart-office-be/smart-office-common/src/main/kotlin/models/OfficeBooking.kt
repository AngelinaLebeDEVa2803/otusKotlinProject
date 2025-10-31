package ru.otus.otuskotlin.smartoffice.common

import kotlinx.datetime.Instant


data class OfficeBooking (
    var id: OfficeBookingId = OfficeBookingId.NONE,
    var userId: OfficeUserId = OfficeUserId.NONE,

    var floorId: OfficeFloorId = OfficeFloorId.NONE,
    var roomId: OfficeRoomId = OfficeRoomId.NONE,
    var workspaceId: OfficeWorkspaceId = OfficeWorkspaceId.NONE,

    var startTime: Instant = Instant.NONE,
    var endTime: Instant = Instant.NONE,
    var status: OfficeBookingStatus = OfficeBookingStatus.NONE,

    var lock: OfficeBookingLock = OfficeBookingLock.NONE,
    val permissionsClient: MutableSet<OfficeBookingPermissions> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = OfficeBooking()
    }

}