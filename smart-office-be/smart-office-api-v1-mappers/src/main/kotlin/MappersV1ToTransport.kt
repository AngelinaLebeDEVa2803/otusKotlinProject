package ru.otus.otuskotlin.smartoffice.mappers.v1

import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.exceptions.UnknownOfficeCommand
import ru.otus.otuskotlin.smartoffice.common.models.*

fun OfficeContext.toTransportBooking(): IResponse = when (val cmd = command) {
    OfficeCommand.CREATE -> toTransportCreate()
    OfficeCommand.READ -> toTransportRead()
    OfficeCommand.UPDATE -> toTransportUpdate()
    OfficeCommand.DELETE -> toTransportDelete()
    OfficeCommand.ALL -> toTransportAll()
    OfficeCommand.NONE -> throw UnknownOfficeCommand(cmd)
}

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
fun List<OfficeBooking>.toTransportAd(): List<BookingResponseObject>? = this
    .map { it.toTransportBooking() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun OfficeBooking.toTransportBooking(): BookingResponseObject = BookingResponseObject(
    id = id.toTransportBooking(),

//    title = title.takeIf { it.isNotBlank() },
//    description = description.takeIf { it.isNotBlank() },
//    ownerId = ownerId.takeIf { it != MkplUserId.NONE }?.asString(),
//    adType = adType.toTransportAd(),
//    visibility = visibility.toTransportAd(),

    permissions = permissionsClient.toTransportBooking(),
)

internal fun OfficeBookingId.toTransportBooking() = takeIf { it != OfficeBookingId.NONE }?.asString()

private fun Set<OfficeBookingPermissions>.toTransportBooking(): Set<BookingPermissions>? = this
    .map { it.toTransportBooking() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun OfficeBookingPermissions.toTransportBooking() = when (this) {
    OfficeBookingPermissions.READ -> BookingPermissions.READ
    OfficeBookingPermissions.UPDATE -> BookingPermissions.UPDATE
    OfficeBookingPermissions.DELETE -> BookingPermissions.DELETE
}