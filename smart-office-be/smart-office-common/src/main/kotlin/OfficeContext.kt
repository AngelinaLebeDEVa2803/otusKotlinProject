package ru.otus.otuskotlin.smartoffice.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.stubs.*
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.IRepoBooking
import ru.otus.otuskotlin.smartoffice.common.ws.IOfficeWsSession

data class OfficeContext(
    var command: OfficeCommand = OfficeCommand.NONE,
    var state: OfficeState = OfficeState.NONE,
    val errors: MutableList<OfficeError> = mutableListOf(),

    var corSettings: OfficeCorSettings = OfficeCorSettings(),
    var workMode: OfficeWorkMode = OfficeWorkMode.PROD,
    var stubCase: OfficeStubs = OfficeStubs.NONE,
    var wsSession: IOfficeWsSession = IOfficeWsSession.NONE,

    var requestId: OfficeRequestId = OfficeRequestId.NONE,
    var ctxTimeStart: Instant = Instant.NONE,

    var bookingRequest: OfficeBooking = OfficeBooking(),
    var bookingFilterRequest: OfficeBookingFilter = OfficeBookingFilter(),

    var bookingValidating: OfficeBooking = OfficeBooking(),
    var bookingFilterValidating: OfficeBookingFilter = OfficeBookingFilter(),

    var bookingValidated: OfficeBooking = OfficeBooking(),
    var bookingFilterValidated: OfficeBookingFilter = OfficeBookingFilter(),

    var bookingRepo: IRepoBooking = IRepoBooking.NONE,
    var bookingRepoRead: OfficeBooking = OfficeBooking(), // То, что прочитали из репозитория
    var bookingRepoPrepare: OfficeBooking = OfficeBooking(), // То, что готовим для сохранения в БД
    var bookingRepoDone: OfficeBooking = OfficeBooking(),  // Результат, полученный из БД
    var bookingsRepoDone: MutableList<OfficeBooking> = mutableListOf(),

    var bookingResponse: OfficeBooking = OfficeBooking(),
    var bookingsResponse: MutableList<OfficeBooking> = mutableListOf(),

    )
