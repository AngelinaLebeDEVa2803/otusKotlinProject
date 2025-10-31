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