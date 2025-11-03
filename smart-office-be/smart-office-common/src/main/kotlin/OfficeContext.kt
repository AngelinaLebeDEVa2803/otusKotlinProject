package ru.otus.otuskotlin.smartoffice.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.stubs.*
import ru.otus.otuskotlin.smartoffice.common.models.*

data class OfficeContext(
    var command: OfficeCommand = OfficeCommand.NONE,
    var state: OfficeState = OfficeState.NONE,
    val errors: MutableList<OfficeError> = mutableListOf(),

    var workMode: OfficeWorkMode = OfficeWorkMode.PROD,
    var stubCase: OfficeStubs = OfficeStubs.NONE,

    var requestId: OfficeRequestId = OfficeRequestId.NONE,
    var ctxTimeStart: Instant = Instant.NONE,
    var bookingRequest: OfficeBooking = OfficeBooking(),
    var bookingFilterRequest: OfficeBookingFilter = OfficeBookingFilter(),

    var bookingResponse: OfficeBooking = OfficeBooking(),
    var bookingsResponse: MutableList<OfficeBooking> = mutableListOf(),

    )
