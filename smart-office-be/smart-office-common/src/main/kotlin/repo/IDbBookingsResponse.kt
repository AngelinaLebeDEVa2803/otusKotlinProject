package ru.otus.otuskotlin.smartoffice.common.repo

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.common.models.OfficeError

sealed interface IDbBookingsResponse: IDbResponse<List<OfficeBooking>>

data class DbBookingsResponseOk(
    val data: List<OfficeBooking>
): IDbBookingsResponse

@Suppress("unused")
data class DbBookingsResponseErr(
    val errors: List<OfficeError> = emptyList()
): IDbBookingsResponse {
    constructor(err: OfficeError): this(listOf(err))
}
