package ru.otus.otuskotlin.smartoffice.app.spring.base

import ru.otus.otuskotlin.smartoffice.app.common.IOfficeAppSettings
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings

data class OfficeAppSettings(
    override val corSettings: OfficeCorSettings,
    override val processor: OfficeBookingProcessor,
): IOfficeAppSettings
