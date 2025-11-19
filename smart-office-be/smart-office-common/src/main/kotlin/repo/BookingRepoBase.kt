package ru.otus.otuskotlin.smartoffice.common.repo

import ru.otus.otuskotlin.smartoffice.common.helpers.errorSystem

abstract class BookingRepoBase: IRepoBooking {

    protected suspend fun tryBookingMethod(block: suspend () -> IDbBookingResponse) = try {
        block()
    } catch (e: Throwable) {
        DbBookingResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryBookingsMethod(block: suspend () -> IDbBookingsResponse) = try {
        block()
    } catch (e: Throwable) {
        DbBookingsResponseErr(errorSystem("methodException", e = e))
    }

}
