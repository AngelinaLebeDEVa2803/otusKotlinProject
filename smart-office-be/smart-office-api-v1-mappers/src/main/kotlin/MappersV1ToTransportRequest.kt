package ru.otus.otuskotlin.smartoffice.mappers.v1

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.api.v1.models.BookingCreateObject
import ru.otus.otuskotlin.smartoffice.api.v1.models.BookingDeleteObject
import ru.otus.otuskotlin.smartoffice.api.v1.models.BookingReadObject
import ru.otus.otuskotlin.smartoffice.api.v1.models.BookingUpdateObject
import ru.otus.otuskotlin.smartoffice.api.v1.models.BookingAllFilter
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking

// создание объектов для реквестов из контекста
fun OfficeBooking.toTransportCreateBooking() = BookingCreateObject(
    userId = userId.toTransportBooking(),
    floorId = floorId.toTransportBooking(),
    roomId = roomId.toTransportBooking(),
    workspaceId = workspaceId.toTransportBooking(),
    startTime = startTime.takeIf { it != Instant.NONE }?.toString(),
    endTime = endTime.takeIf { it != Instant.NONE }?.toString(),
    status = status.toTransportBooking(),
)

fun OfficeBooking.toTransportReadBooking() = BookingReadObject(
    id = id.toTransportBooking()
)

fun OfficeBooking.toTransportUpdateBooking() = BookingUpdateObject(
    id = id.toTransportBooking(),
    userId = userId.toTransportBooking(),
    floorId = floorId.toTransportBooking(),
    roomId = roomId.toTransportBooking(),
    workspaceId = workspaceId.toTransportBooking(),
    startTime = startTime.takeIf { it != Instant.NONE }?.toString(),
    endTime = endTime.takeIf { it != Instant.NONE }?.toString(),
    status = status.toTransportBooking(),
    lock = lock.toTransportBooking(),
)

fun OfficeBooking.toTransportDeleteBooking() = BookingDeleteObject(
    id = id.toTransportBooking(),
    lock = lock.toTransportBooking(),
)

fun OfficeBooking.toTransportAllBooking() = BookingAllFilter(
    userId = userId.toTransportBooking(),
    startTime = startTime.takeIf { it != Instant.NONE }?.toString(),
    endTime = endTime.takeIf { it != Instant.NONE }?.toString(),
    status = status.toTransportBooking(),
)