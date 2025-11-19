package ru.otus.otuskotlin.smartoffice.common.repo

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking

data class DbBookingRequest(
    val booking: OfficeBooking
)
