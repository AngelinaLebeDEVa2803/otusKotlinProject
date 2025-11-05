package ru.otus.otuskotlin.smartoffice.app.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
//import ru.otus.otuskotlin.smartoffice.biz.MkplAdProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.logging.common.OfficeLoggerProvider
import ru.otus.otuskotlin.smartoffice.logging.jvm.officeLoggerLogback

@Suppress("unused")
@Configuration
class BookingConfig {
    @Bean
    fun processor(corSettings: OfficeCorSettings) = MkplAdProcessor(corSettings = corSettings)

    @Bean
    fun loggerProvider(): OfficeLoggerProvider = OfficeLoggerProvider { officeLoggerLogback(it) }

    @Bean
    fun corSettings(): OfficeCorSettings = OfficeCorSettings(
        loggerProvider = loggerProvider(),
    )

    @Bean
    fun appSettings(
        corSettings: OfficeCorSettings,
        processor: MkplAdProcessor,
    ) = OfficeAppSettings(
        corSettings = corSettings,
        processor = processor,
    )
}
