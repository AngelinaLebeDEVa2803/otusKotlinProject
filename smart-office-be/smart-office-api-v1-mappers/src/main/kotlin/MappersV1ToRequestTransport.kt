package ru.otus.otuskotlin.smartoffice.mappers.v1

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.models.*

fun OfficeBooking.toTransportCreate() = BookingCreateObject(
    userId = userId.toTransportBooking(),
    floorId = floorId.toTransportBooking(),
    roomId = roomId.toTransportBooking(),
    workspaceId = workspaceId.toTransportBooking(),
    startTime = startTime.takeIf { it != Instant.NONE }?.toString(),
    endTime = endTime.takeIf { it != Instant.NONE }?.toString(),
    status = status.toTransportBooking(),
)

fun OfficeBooking.toTransportRead() = BookingReadObject(
    id = id.takeIf { it != OfficeBookingId.NONE }?.asString(),
)

fun OfficeBooking.toTransportUpdate() = BookingUpdateObject(
    id = id.takeIf { it != OfficeBookingId.NONE }?.asString(),
    userId = userId.toTransportBooking(),
    floorId = floorId.toTransportBooking(),
    roomId = roomId.toTransportBooking(),
    workspaceId = workspaceId.toTransportBooking(),
    startTime = startTime.takeIf { it != Instant.NONE }?.toString(),
    endTime = endTime.takeIf { it != Instant.NONE }?.toString(),
    status = status.toTransportBooking(),
    lock = lock.takeIf { it != OfficeBookingLock.NONE }?.asString(),
)

fun OfficeBooking.toTransportDelete() = BookingDeleteObject(
    id = id.takeIf { it != OfficeBookingId.NONE }?.asString(),
    lock = lock.takeIf { it != OfficeBookingLock.NONE }?.asString(),
)
