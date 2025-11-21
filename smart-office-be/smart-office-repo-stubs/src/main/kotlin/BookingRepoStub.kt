package ru.otus.otuskotlin.smartoffice.repo.stubs

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.*
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub

class BookingRepoStub() : IRepoBooking {
    override suspend fun createBooking(rq: DbBookingRequest): IDbBookingResponse {
        return DbBookingResponseOk(
            data = OfficeBookingStub.get(),
        )
    }

    override suspend fun readBooking(rq: DbBookingIdRequest): IDbBookingResponse {
        return DbBookingResponseOk(
            data = OfficeBookingStub.get(),
        )
    }

    override suspend fun updateBooking(rq: DbBookingRequest): IDbBookingResponse {
        return DbBookingResponseOk(
            data = OfficeBookingStub.get(),
        )
    }

    override suspend fun deleteBooking(rq: DbBookingIdRequest): IDbBookingResponse {
        return DbBookingResponseOk(
            data = OfficeBookingStub.get(),
        )
    }

    override suspend fun allBooking(rq: DbBookingFilterRequest): IDbBookingsResponse {
        return DbBookingsResponseOk(
            data = OfficeBookingStub.prepareBookingsList(userId = OfficeUserId("123"),
                startTime = Instant.parse("2026-01-15T09:00:00Z"),
                endTime = Instant.parse("2026-12-15T09:00:00Z"),
                status = OfficeBookingStatus.ACTIVE)
        )
    }
}
