package ru.otus.otuskotlin.smartoffice.app.common

import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings

interface IOfficeAppSettings {
    val processor: OfficeBookingProcessor
    val corSettings: OfficeCorSettings
}
