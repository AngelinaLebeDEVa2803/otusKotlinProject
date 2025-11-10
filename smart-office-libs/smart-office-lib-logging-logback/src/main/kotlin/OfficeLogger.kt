package ru.otus.otuskotlin.smartoffice.logging.jvm

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.smartoffice.logging.common.IOfficeLogWrapper
import kotlin.reflect.KClass

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun officeLoggerLogback(logger: Logger): IOfficeLogWrapper = OfficeLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun officeLoggerLogback(clazz: KClass<*>): IOfficeLogWrapper = officeLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun officeLoggerLogback(loggerId: String): IOfficeLogWrapper = officeLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
