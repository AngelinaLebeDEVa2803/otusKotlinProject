package ru.otus.otuskotlin.smartoffice.common

//import ru.otus.otuskotlin.smartoffice.logging.common.MpLoggerProvider

data class MkplCorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
) {
    companion object {
        val NONE = MkplCorSettings()
    }
}
