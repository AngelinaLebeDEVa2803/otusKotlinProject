package ru.otus.otuskotlin.smartoffice.common.repo

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingId
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingLock

data class DbBookingIdRequest(
    val id: OfficeBookingId,
    val lock: OfficeBookingLock = OfficeBookingLock.NONE,
) {
    constructor(booking: OfficeBooking): this(booking.id, booking.lock)
}
