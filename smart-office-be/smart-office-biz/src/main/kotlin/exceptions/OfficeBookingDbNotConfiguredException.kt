package ru.otus.otuskotlin.smartoffice.biz.exceptions

import ru.otus.otuskotlin.smartoffice.common.models.OfficeWorkMode

class OfficeBookingDbNotConfiguredException(val workMode: OfficeWorkMode): Exception(
    "Database is not configured properly for workmode $workMode"
)
