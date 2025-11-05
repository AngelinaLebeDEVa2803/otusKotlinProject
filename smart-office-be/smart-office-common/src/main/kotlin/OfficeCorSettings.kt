package ru.otus.otuskotlin.smartoffice.common

import ru.otus.otuskotlin.smartoffice.logging.common.OfficeLoggerProvider

data class OfficeCorSettings(
    val loggerProvider: OfficeLoggerProvider = OfficeLoggerProvider(),
) {
    companion object {
        val NONE = OfficeCorSettings()
    }
}
