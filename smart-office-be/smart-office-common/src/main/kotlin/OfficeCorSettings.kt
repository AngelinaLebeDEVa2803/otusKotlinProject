package ru.otus.otuskotlin.smartoffice.common

import ru.otus.otuskotlin.smartoffice.logging.common.OfficeLoggerProvider
import ru.otus.otuskotlin.smartoffice.common.ws.IOfficeWsSessionRepo

data class OfficeCorSettings(
    val loggerProvider: OfficeLoggerProvider = OfficeLoggerProvider(),
    val wsSessions: IOfficeWsSessionRepo = IOfficeWsSessionRepo.NONE,
) {
    companion object {
        val NONE = OfficeCorSettings()
    }
}
