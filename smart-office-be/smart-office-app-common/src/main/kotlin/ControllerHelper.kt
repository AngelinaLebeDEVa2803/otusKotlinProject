package ru.otus.otuskotlin.smartoffice.app.common

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.smartoffice.api.log1.mapper.toLog
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.asOfficeError
import ru.otus.otuskotlin.smartoffice.common.models.OfficeCommand
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import kotlin.reflect.KClass

suspend inline fun <T> IOfficeAppSettings.controllerHelper(
    crossinline getRequest: suspend OfficeContext.() -> Unit,
    crossinline toResponse: suspend OfficeContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = OfficeContext(
        ctxTimeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId),
            e = e,
        )
        ctx.state = OfficeState.FAILING
        ctx.errors.add(e.asOfficeError())
        processor.exec(ctx)
        if (ctx.command == OfficeCommand.NONE) {
            ctx.command = OfficeCommand.READ
        }
        ctx.toResponse()
    }
}
