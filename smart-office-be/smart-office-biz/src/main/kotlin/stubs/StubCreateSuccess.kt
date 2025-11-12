package ru.otus.otuskotlin.smartoffice.biz.stubs

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.marketplace.common.models.MkplVisibility
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs
import ru.otus.otuskotlin.smartoffice.logging.common.LogLevel
import ru.otus.otuskotlin.smartoffice.stubs.MkplAdStub

fun ICorChainDsl<OfficeContext>.stubCreateSuccess(title: String, corSettings: OfficeCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для создания бронирования
    """.trimIndent()
    on { stubCase == OfficeStubs.SUCCESS && state == OfficeState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubOffersSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = OfficeState.FINISHING
            val stub = MkplAdStub.prepareResult {
                adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
                adRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
                adRequest.adType.takeIf { it != MkplDealSide.NONE }?.also { this.adType = it }
                adRequest.visibility.takeIf { it != MkplVisibility.NONE }?.also { this.visibility = it }
            }
            adResponse = stub
        }
    }
}
