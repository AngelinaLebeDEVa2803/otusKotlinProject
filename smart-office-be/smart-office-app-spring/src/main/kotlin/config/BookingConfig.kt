package ru.otus.otuskotlin.smartoffice.app.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
//import ru.otus.otuskotlin.smartoffice.biz.MkplAdProcessor
//import ru.otus.otuskotlin.smartoffice.common.MkplCorSettings
//import ru.otus.otuskotlin.smartoffice.logging.common.MpLoggerProvider
//import ru.otus.otuskotlin.smartoffice.logging.jvm.mpLoggerLogback

@Suppress("unused")
@Configuration
class BookingConfig {
    @Bean
    fun processor(corSettings: MkplCorSettings) = MkplAdProcessor(corSettings = corSettings)

    @Bean
    fun loggerProvider(): MpLoggerProvider = MpLoggerProvider { mpLoggerLogback(it) }

    @Bean
    fun corSettings(): MkplCorSettings = MkplCorSettings(
        loggerProvider = loggerProvider(),
    )

    @Bean
    fun appSettings(
        corSettings: MkplCorSettings,
        processor: MkplAdProcessor,
    ) = MkplAppSettings(
        corSettings = corSettings,
        processor = processor,
    )
}
