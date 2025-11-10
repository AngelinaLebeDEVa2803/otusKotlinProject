package ru.otus.otuskotlin.smartoffice.api.log1.mapper

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.api.log1.models.*
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.models.*

fun OfficeContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "smart-office",
    booking = toOfficeLog(),
    errors = errors.map { it.toLog() },
)

private fun OfficeContext.toOfficeLog(): OfficeBookingLogModel? {
    val bookingNone = OfficeBooking()
    return OfficeBookingLogModel(
        requestId = requestId.takeIf { it != OfficeRequestId.NONE }?.asString(),
        requestBooking = bookingRequest.takeIf { it != bookingNone }?.toLog(),
        requestFilter = bookingFilterRequest.takeIf { it != OfficeBookingFilter() }?.toLog(),
        responseBooking = bookingResponse.takeIf { it != bookingNone }?.toLog(),
        responseBookings = bookingsResponse.takeIf { it.isNotEmpty() }?.filter { it != bookingNone }?.map { it.toLog() },
    ).takeIf { it != OfficeBookingLogModel() }
}

private fun OfficeBookingFilter.toLog() = BookingFilterLog(
    userId = userId.takeIf { it != OfficeUserId.NONE }?.asString(),
    startTime = startTime.toLogBookingTime(),
    endTime = endTime.toLogBookingTime(),
    status = status.toLogFilterStatus(),
)

private fun OfficeError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

private fun OfficeBooking.toLog() = BookingLog(
    id = id.takeIf { it != OfficeBookingId.NONE }?.asString(),
    userId = userId.takeIf { it != OfficeUserId.NONE }?.asString(),
    floorId = floorId.takeIf { it != OfficeFloorId.NONE }?.asString(),
    roomId = roomId.takeIf { it != OfficeRoomId.NONE }?.asString(),
    workspaceId = workspaceId.takeIf { it != OfficeWorkspaceId.NONE }?.asString(),
    startTime = startTime.toLogBookingTime(),
    endTime = endTime.toLogBookingTime(),
    status = status.toLogStatus(),
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)

private fun Instant?.toLogBookingTime(): String? =
    this?.takeIf { it != Instant.NONE }?.toString()

internal fun OfficeBookingStatus.toLogFilterStatus() = when (this) {
    OfficeBookingStatus.ACTIVE -> BookingFilterLog.Status.ACTIVE
    OfficeBookingStatus.CANCELLED -> BookingFilterLog.Status.CANCELLED
    OfficeBookingStatus.COMPLETED -> BookingFilterLog.Status.COMPLETED
    OfficeBookingStatus.NONE -> null
}

internal fun OfficeBookingStatus.toLogStatus() = when (this) {
    OfficeBookingStatus.ACTIVE -> BookingLog.Status.ACTIVE
    OfficeBookingStatus.CANCELLED -> BookingLog.Status.CANCELLED
    OfficeBookingStatus.COMPLETED -> BookingLog.Status.COMPLETED
    OfficeBookingStatus.NONE -> null
}