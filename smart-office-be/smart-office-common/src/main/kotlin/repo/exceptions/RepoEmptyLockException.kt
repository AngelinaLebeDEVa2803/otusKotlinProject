package ru.otus.otuskotlin.smartoffice.common.repo.exceptions

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingId

class RepoEmptyLockException(id: OfficeBookingId): RepoBookingException(
    id,
    "Lock is empty in DB"
)
