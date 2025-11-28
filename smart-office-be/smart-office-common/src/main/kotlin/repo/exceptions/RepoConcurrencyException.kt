package ru.otus.otuskotlin.smartoffice.common.repo.exceptions

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingId
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingLock

class RepoConcurrencyException(id: OfficeBookingId, expectedLock: OfficeBookingLock, actualLock: OfficeBookingLock?): RepoBookingException(
    id,
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
