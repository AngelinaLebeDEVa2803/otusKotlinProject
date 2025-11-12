package ru.otus.otuskotlin.smartoffice.stubs

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingId
import ru.otus.otuskotlin.smartoffice.common.models.OfficeUserId
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingStatus
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStubExample.MY_BOOKING1

object OfficeBookingStub {
    fun get(): OfficeBooking = MY_BOOKING1.copy()

    fun prepareResult(block: OfficeBooking.() -> Unit): OfficeBooking = get().apply(block)

    fun prepareBookingsList(userId: OfficeUserId, startTime: Instant, endTime: Instant, status: OfficeBookingStatus) = listOf(
        bookingElement(MY_BOOKING1, id = "booking_001", userId = userId, startTime = startTime, endTime = endTime, status = status),
        bookingElement(MY_BOOKING1, id = "booking_002", userId = userId, startTime = startTime, endTime = endTime, status = status),
    )

    private fun bookingElement(base: OfficeBooking, id: String, userId: OfficeUserId, startTime: Instant, endTime: Instant, status: OfficeBookingStatus) = base.copy(
        id = OfficeBookingId(id),
        userId = userId,
        startTime = startTime,
        endTime = endTime,
        status = status,
    )

}
