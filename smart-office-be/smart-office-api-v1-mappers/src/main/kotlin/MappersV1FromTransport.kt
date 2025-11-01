package ru.otus.otuskotlin.smartoffice.mappers.v1

import ru.otus.otuskotlin.smartoffice.api.v1.models.*
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

fun OfficeContext.fromTransport(request: BookingCreateRequest) {
    command = OfficeCommand.CREATE
    bookingRequest = request.booking?.toInternal() ?: OfficeBooking()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}