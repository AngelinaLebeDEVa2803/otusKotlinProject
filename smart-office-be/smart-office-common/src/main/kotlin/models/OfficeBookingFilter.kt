package ru.otus.otuskotlin.smartoffice.common.models

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.NONE


data class OfficeBookingFilter (
    var userId: OfficeUserId = OfficeUserId.NONE,
    var startTime: Instant = Instant.NONE,
    var endTime: Instant = Instant.NONE,
    var status: OfficeBookingStatus = OfficeBookingStatus.NONE
    )

