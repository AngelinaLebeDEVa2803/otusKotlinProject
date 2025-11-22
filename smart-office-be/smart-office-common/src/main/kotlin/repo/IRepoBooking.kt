package ru.otus.otuskotlin.smartoffice.common.repo

interface IRepoBooking {
    suspend fun createBooking(rq: DbBookingRequest): IDbBookingResponse
    suspend fun readBooking(rq: DbBookingIdRequest): IDbBookingResponse
    suspend fun updateBooking(rq: DbBookingRequest): IDbBookingResponse
    suspend fun deleteBooking(rq: DbBookingIdRequest): IDbBookingResponse
    suspend fun allBooking(rq: DbBookingFilterRequest): IDbBookingsResponse
    companion object {
        val NONE = object : IRepoBooking {
            override suspend fun createBooking(rq: DbBookingRequest): IDbBookingResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readBooking(rq: DbBookingIdRequest): IDbBookingResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateBooking(rq: DbBookingRequest): IDbBookingResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteBooking(rq: DbBookingIdRequest): IDbBookingResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun allBooking(rq: DbBookingFilterRequest): IDbBookingsResponse {
                throw NotImplementedError("Must not be used")
            }
        }
    }
}
