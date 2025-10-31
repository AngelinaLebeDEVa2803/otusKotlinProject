package ru.otus.otuskotlin.smartoffice.common

import kotlinx.datetime.Instant


data class OfficeBookingFilter (
    var userId: OfficeUserId = OfficeUserId.NONE,
    var startTime: Instant = Instant.NONE,
    var endTime: Instant = Instant.NONE,
    var status: OfficeBookingStatus = OfficeBookingStatus.NONE
    )

