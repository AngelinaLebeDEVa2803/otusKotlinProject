package ru.otus.otuskotlin.smartoffice.app.spring.config

//import ru.otus.otuskotlin.smartoffice.app.common.IMkplAppSettings
//import ru.otus.otuskotlin.smartoffice.biz.MkplAdProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings

data class MkplAppSettings(
    override val corSettings: OfficeCorSettings,
    override val processor: MkplAdProcessor,
): IMkplAppSettings
