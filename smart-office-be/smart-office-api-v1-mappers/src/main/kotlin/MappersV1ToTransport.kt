package ru.otus.otuskotlin.smartoffice.mappers.v1

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.exceptions.UnknownOfficeCommand
import ru.otus.otuskotlin.smartoffice.common.models.*

fun OfficeContext.toTransportBooking(): IResponse = when (val cmd = command) {
    OfficeCommand.CREATE -> toTransportCreate()
    OfficeCommand.READ -> toTransportRead()
    OfficeCommand.UPDATE -> toTransportUpdate()
    OfficeCommand.DELETE -> toTransportDelete()
    OfficeCommand.ALL -> toTransportAll()
    OfficeCommand.INIT -> toTransportInit()
    OfficeCommand.FINISH -> object: IResponse {
        override val responseType: String? = null
        override val result: ResponseResult? = null
        override val errors: List<Error>? = null
    }
    OfficeCommand.NONE -> throw UnknownOfficeCommand(cmd)
}

fun OfficeContext.toTransportInit() = BookingInitResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
)

fun OfficeContext.toTransportCreate() = BookingCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    booking = bookingResponse.toTransportBooking()
)

fun OfficeContext.toTransportRead() = BookingReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    booking = bookingResponse.toTransportBooking()
)

fun OfficeContext.toTransportUpdate() = BookingUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    booking = bookingResponse.toTransportBooking()
)

fun OfficeContext.toTransportDelete() = BookingDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    booking = bookingResponse.toTransportBooking()
)

fun OfficeContext.toTransportAll() = BookingAllResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    bookings = bookingsResponse.toTransportBooking()
)

// errors and state
private fun OfficeError.toTransportBooking() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun List<OfficeError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportBooking() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun OfficeState.toResult(): ResponseResult? = when (this) {
    OfficeState.RUNNING -> ResponseResult.SUCCESS
    OfficeState.FAILING -> ResponseResult.ERROR
    OfficeState.FINISHING -> ResponseResult.SUCCESS
    OfficeState.NONE -> null
}

// booking response
fun List<OfficeBooking>.toTransportBooking(): List<BookingResponseObject>? = this
    .map { it.toTransportBooking() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun OfficeBooking.toTransportBooking(): BookingResponseObject = BookingResponseObject(
    id = id.toTransportBooking(),
    userId = userId.toTransportBooking(),
    floorId = floorId.toTransportBooking(),
    roomId = roomId.toTransportBooking(),
    workspaceId = workspaceId.toTransportBooking(),

    startTime = startTime.toTransportBookingTime(),
    endTime = endTime.toTransportBookingTime(),

    status = status.toTransportBooking(),
    lock = lock.toTransportBooking(),
    permissions = permissionsClient.toTransportBooking(),
)

internal fun OfficeBookingId.toTransportBooking() = takeIf { it != OfficeBookingId.NONE }?.asString()
internal fun OfficeUserId.toTransportBooking() = takeIf { it != OfficeUserId.NONE }?.asString()
internal fun OfficeFloorId.toTransportBooking() = takeIf { it != OfficeFloorId.NONE }?.asString()
internal fun OfficeRoomId.toTransportBooking() = takeIf { it != OfficeRoomId.NONE }?.asString()
internal fun OfficeWorkspaceId.toTransportBooking() = takeIf { it != OfficeWorkspaceId.NONE }?.asString()
internal fun OfficeBookingLock.toTransportBooking() = takeIf { it != OfficeBookingLock.NONE }?.asString()

internal fun Instant?.toTransportBookingTime(): String? =
    this?.takeIf { it != Instant.NONE }?.toString()

private fun Set<OfficeBookingPermissions>.toTransportBooking(): Set<BookingPermissions>? = this
    .map { it.toTransportBooking() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun OfficeBookingPermissions.toTransportBooking() = when (this) {
    OfficeBookingPermissions.READ -> BookingPermissions.READ
    OfficeBookingPermissions.UPDATE -> BookingPermissions.UPDATE
    OfficeBookingPermissions.DELETE -> BookingPermissions.DELETE
}

internal fun OfficeBookingStatus.toTransportBooking() = when (this) {
    OfficeBookingStatus.ACTIVE -> BookingStatus.ACTIVE
    OfficeBookingStatus.CANCELLED -> BookingStatus.CANCELLED
    OfficeBookingStatus.COMPLETED -> BookingStatus.COMPLETED
    OfficeBookingStatus.NONE -> null
}