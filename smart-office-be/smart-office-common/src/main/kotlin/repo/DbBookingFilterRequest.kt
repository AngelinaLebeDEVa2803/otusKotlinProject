package ru.otus.otuskotlin.smartoffice.common.repo

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.NONE
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingStatus
import ru.otus.otuskotlin.smartoffice.common.models.OfficeUserId

data class DbBookingFilterRequest(
    val userId: OfficeUserId = OfficeUserId.NONE,
    val startTime: Instant = Instant.NONE,
    val endTime: Instant = Instant.NONE,
    val status: OfficeBookingStatus = OfficeBookingStatus.NONE,
)
