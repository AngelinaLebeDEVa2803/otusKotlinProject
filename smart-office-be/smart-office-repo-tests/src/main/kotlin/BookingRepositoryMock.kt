package ru.otus.otuskotlin.smartoffice.backend.repo.tests

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.common.repo.*

class BookingRepositoryMock(
    private val invokeCreateBooking: (DbBookingRequest) -> IDbBookingResponse = { DEFAULT_AD_SUCCESS_EMPTY_MOCK },
    private val invokeReadBooking: (DbBookingIdRequest) -> IDbBookingResponse = { DEFAULT_AD_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateBooking: (DbBookingRequest) -> IDbBookingResponse = { DEFAULT_AD_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteBooking: (DbBookingIdRequest) -> IDbBookingResponse = { DEFAULT_AD_SUCCESS_EMPTY_MOCK },
    private val invokeAllBooking: (DbBookingFilterRequest) -> IDbBookingsResponse = { DEFAULT_ADS_SUCCESS_EMPTY_MOCK },
): IRepoBooking {
    override suspend fun createBooking(rq: DbBookingRequest): IDbBookingResponse {
        return invokeCreateBooking(rq)
    }

    override suspend fun readBooking(rq: DbBookingIdRequest): IDbBookingResponse {
        return invokeReadBooking(rq)
    }

    override suspend fun updateBooking(rq: DbBookingRequest): IDbBookingResponse {
        return invokeUpdateBooking(rq)
    }

    override suspend fun deleteBooking(rq: DbBookingIdRequest): IDbBookingResponse {
        return invokeDeleteBooking(rq)
    }

    override suspend fun allBooking(rq: DbBookingFilterRequest): IDbBookingsResponse {
        return invokeAllBooking(rq)
    }

    companion object {
        val DEFAULT_AD_SUCCESS_EMPTY_MOCK = DbBookingResponseOk(OfficeBooking())
        val DEFAULT_ADS_SUCCESS_EMPTY_MOCK = DbBookingsResponseOk(emptyList())
    }
}
