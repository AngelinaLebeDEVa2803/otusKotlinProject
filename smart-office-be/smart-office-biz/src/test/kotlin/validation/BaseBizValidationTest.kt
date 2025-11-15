package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.common.models.OfficeCommand

abstract class BaseBizValidationTest {
    protected abstract val command: OfficeCommand
    private val settings by lazy { OfficeCorSettings() }
    protected val processor by lazy { OfficeBookingProcessor(settings) }
}
