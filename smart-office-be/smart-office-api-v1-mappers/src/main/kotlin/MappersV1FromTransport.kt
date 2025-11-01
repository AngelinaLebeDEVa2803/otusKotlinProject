package ru.otus.otuskotlin.smartoffice.mappers.v1

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.stubs.*
import ru.otus.otuskotlin.smartoffice.mappers.v1.exceptions.UnknownRequestClass

fun OfficeContext.fromTransport(request: IRequest) = when (request) {
    is BookingCreateRequest -> fromTransport(request)
    is BookingReadRequest -> fromTransport(request)
    is BookingUpdateRequest -> fromTransport(request)
    is BookingDeleteRequest -> fromTransport(request)
    is BookingAllRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

// тут не забыть функции для заполнения value классов
private fun String?.toBookingId() = this?.let { OfficeBookingId(it) } ?: OfficeBookingId.NONE
private fun String?.toBookingLock() = this?.let { OfficeBookingLock(it) } ?: OfficeBookingLock.NONE

private fun String?.toBookingUserId() = this?.let { OfficeUserId(it) } ?: OfficeUserId.NONE
private fun String?.toBookingFloorId() = this?.let { OfficeFloorId(it) } ?: OfficeFloorId.NONE
private fun String?.toBookingRoomId() = this?.let { OfficeRoomId(it) } ?: OfficeRoomId.NONE
private fun String?.toBookingWorkspaceId() = this?.let { OfficeWorkspaceId(it) } ?: OfficeWorkspaceId.NONE


// режим работы прод, тест, стаб
private fun BookingDebug?.transportToWorkMode(): OfficeWorkMode = when (this?.mode) {
    BookingRequestDebugMode.PROD -> OfficeWorkMode.PROD
    BookingRequestDebugMode.TEST -> OfficeWorkMode.TEST
    BookingRequestDebugMode.STUB -> OfficeWorkMode.STUB
    null -> OfficeWorkMode.PROD
}

private fun BookingDebug?.transportToStubCase(): OfficeStubs = when (this?.stub) {
    BookingRequestDebugStubs.SUCCESS -> OfficeStubs.SUCCESS
    BookingRequestDebugStubs.NOT_FOUND -> OfficeStubs.NOT_FOUND
    BookingRequestDebugStubs.BAD_ID -> OfficeStubs.BAD_ID
    BookingRequestDebugStubs.BAD_USER_ID -> OfficeStubs.BAD_USER_ID
    BookingRequestDebugStubs.BAD_FLOOR_ID -> OfficeStubs.BAD_FLOOR_ID
    BookingRequestDebugStubs.BAD_ROOM_ID -> OfficeStubs.BAD_ROOM_ID
    BookingRequestDebugStubs.BAD_WORKSPACE_ID -> OfficeStubs.BAD_WORKSPACE_ID
    BookingRequestDebugStubs.BAD_START_TIME -> OfficeStubs.BAD_START_TIME
    BookingRequestDebugStubs.BAD_END_TIME -> OfficeStubs.BAD_END_TIME
    BookingRequestDebugStubs.BAD_TIME_RANGE -> OfficeStubs.BAD_TIME_RANGE
    BookingRequestDebugStubs.BAD_STATUS -> OfficeStubs.BAD_STATUS
    BookingRequestDebugStubs.CANNOT_CREATE -> OfficeStubs.CANNOT_CREATE
    BookingRequestDebugStubs.CANNOT_UPDATE -> OfficeStubs.CANNOT_UPDATE
    BookingRequestDebugStubs.CANNOT_DELETE -> OfficeStubs.CANNOT_DELETE
    null -> OfficeStubs.NONE
}

// create
fun OfficeContext.fromTransport(request: BookingCreateRequest) {
    command = OfficeCommand.CREATE
    bookingRequest = request.booking?.toInternal() ?: OfficeBooking()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

// read
private fun BookingReadObject?.toInternal(): OfficeBooking = if (this != null) {
    OfficeBooking(id = id.toBookingId())
} else {
    OfficeBooking()
}

fun OfficeContext.fromTransport(request: BookingReadRequest) {
    command = OfficeCommand.READ
    bookingRequest = request.booking.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

// delete
private fun BookingDeleteObject?.toInternal(): OfficeBooking = if (this != null) {
    OfficeBooking(
        id = id.toBookingId(),
        lock = lock.toBookingLock(),
    )
} else {
    OfficeBooking()
}

fun OfficeContext.fromTransport(request: BookingDeleteRequest) {
    command = OfficeCommand.DELETE
    bookingRequest = request.booking.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

// update
fun OfficeContext.fromTransport(request: BookingUpdateRequest) {
    command = OfficeCommand.UPDATE
    bookingRequest = request.booking?.toInternal() ?: OfficeBooking()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

// all
private fun BookingAllFilter?.toInternal(): OfficeBookingFilter = OfficeBookingFilter(
    userId = this.userId.toBookingUserId(),
    startTime = this.startTime, // сделать в датетайм
    endTime = this.endTime,  // аналогично
    status = this.status.fromTransport(),
)

fun OfficeContext.fromTransport(request: BookingAllRequest) {
    command = OfficeCommand.ALL
    bookingFilterRequest = request.bookingFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}


//internals full obj for create and update
private fun BookingCreateObject.toInternal(): OfficeBooking = OfficeBooking(
    userId = this.userId.toBookingUserId(),
    floorId = this.floorId.toBookingFloorId(),
    roomId = this.roomId.toBookingRoomId(),
    workspaceId = this.workspaceId.toBookingWorkspaceId(),
    startTime = this.startTime, // сделать в датетайм
    endTime = this.endTime,  // аналогично
    status = this.status.fromTransport(),
)

private fun BookingUpdateObject.toInternal(): OfficeBooking = OfficeBooking(
    id = this.id.toBookingId(),
    userId = this.userId.toBookingUserId(),
    floorId = this.floorId.toBookingFloorId(),
    roomId = this.roomId.toBookingRoomId(),
    workspaceId = this.workspaceId.toBookingWorkspaceId(),
    startTime = this.startTime, // сделать в датетайм
    endTime = this.endTime,  // аналогично
    status = this.status.fromTransport(),
    lock = lock.toBookingLock(),
)


private fun BookingStatus?.fromTransport(): OfficeBookingStatus = when (this) {
    BookingStatus.ACTIVE -> OfficeBookingStatus.ACTIVE
    BookingStatus.CANCELLED -> OfficeBookingStatus.CANCELLED
    BookingStatus.COMPLETED -> OfficeBookingStatus.COMPLETED
    null -> OfficeBookingStatus.NONE
}
