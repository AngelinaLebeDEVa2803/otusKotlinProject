package ru.otus.otuskotlin.smartoffice.common.repo

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.common.models.OfficeError

sealed interface IDbBookingResponse: IDbResponse<OfficeBooking>

data class DbBookingResponseOk(
    val data: OfficeBooking
): IDbBookingResponse

data class DbBookingResponseErr(
    val errors: List<OfficeError> = emptyList()
): IDbBookingResponse {
    constructor(err: OfficeError): this(listOf(err))
}

data class DbBookingResponseErrWithData(
    val data: OfficeBooking,
    val errors: List<OfficeError> = emptyList()
): IDbBookingResponse {
    constructor(booking: OfficeBooking, err: OfficeError): this(booking, listOf(err))
}
