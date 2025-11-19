package ru.otus.otuskotlin.smartoffice.repo.inmemory

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.models.*

data class BookingEntity(
    val id: String? = null,
    val userId: String? = null,
    val floorId: String? = null,
    val roomId: String? = null,
    val workspaceId: String? = null,
    val startTime: Instant? = null,
    val endTime: Instant? = null,
    val status: String? = null,
    val lock: String? = null,
) {
    constructor(model: OfficeBooking): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        userId = model.userId.asString().takeIf { it.isNotBlank() },
        floorId = model.floorId.asString().takeIf { it.isNotBlank() },
        roomId = model.roomId.asString().takeIf { it.isNotBlank() },
        workspaceId = model.workspaceId.asString().takeIf { it.isNotBlank() },
        startTime = model.startTime,
        endTime = model.endTime,
        status = model.status.takeIf { it != OfficeBookingStatus.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
        // Не нужно сохранять permissions, потому что он ВЫЧИСЛЯЕМЫЙ, а не хранимый
    )

    fun toInternal() = OfficeBooking(
        id = id?.let { OfficeBookingId(it) }?: OfficeBookingId.NONE,
        userId = userId?.let { OfficeUserId(it) }?: OfficeUserId.NONE,
        floorId = floorId?.let { OfficeFloorId(it) }?: OfficeFloorId.NONE,
        roomId = roomId?.let { OfficeRoomId(it) }?: OfficeRoomId.NONE,
        workspaceId = workspaceId?.let { OfficeWorkspaceId(it) }?: OfficeWorkspaceId.NONE,
        startTime = startTime?: Instant.NONE,
        endTime = endTime?: Instant.NONE,
        status = status?.let { OfficeBookingStatus.valueOf(it) }?: OfficeBookingStatus.NONE,
        lock = lock?.let { OfficeBookingLock(it) } ?: OfficeBookingLock.NONE,
    )
}
