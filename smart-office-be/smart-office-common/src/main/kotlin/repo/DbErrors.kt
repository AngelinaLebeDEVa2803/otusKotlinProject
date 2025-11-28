package ru.otus.otuskotlin.smartoffice.common.repo

import ru.otus.otuskotlin.smartoffice.common.helpers.errorSystem
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingId
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingLock
import ru.otus.otuskotlin.smartoffice.common.models.OfficeError
import ru.otus.otuskotlin.smartoffice.common.repo.exceptions.RepoConcurrencyException
import ru.otus.otuskotlin.smartoffice.common.repo.exceptions.RepoException

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: OfficeBookingId) = DbBookingResponseErr(
    OfficeError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorEmptyId = DbBookingResponseErr(
    OfficeError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Id must not be null or blank"
    )
)

fun errorRepoConcurrency(
    oldBooking: OfficeBooking,
    expectedLock: OfficeBookingLock,
    exception: Exception = RepoConcurrencyException(
        id = oldBooking.id,
        expectedLock = expectedLock,
        actualLock = oldBooking.lock,
    ),
) = DbBookingResponseErrWithData(
    booking = oldBooking,
    err = OfficeError(
        code = "$ERROR_GROUP_REPO-concurrency",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "The object with ID ${oldBooking.id.asString()} has been changed concurrently by another user or process",
        exception = exception,
    )
)

fun errorEmptyLock(id: OfficeBookingId) = DbBookingResponseErr(
    OfficeError(
        code = "$ERROR_GROUP_REPO-lock-empty",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "Lock for Booking ${id.asString()} is empty that is not admitted"
    )
)

fun errorDb(e: RepoException) = DbBookingResponseErr(
    errorSystem(
        violationCode = "dbLockEmpty",
        e = e
    )
)
