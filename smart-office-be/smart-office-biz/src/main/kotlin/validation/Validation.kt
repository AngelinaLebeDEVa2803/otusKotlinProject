package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.chain

fun ICorChainDsl<OfficeContext>.validation(block: ICorChainDsl<OfficeContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == OfficeState.RUNNING }
}