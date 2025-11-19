package ru.otus.otuskotlin.smartoffice.common.repo.exceptions

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingId

open class RepoBookingException(
    @Suppress("unused")
    val bookingId: OfficeBookingId,
    msg: String,
): RepoException(msg)
