package ru.otus.otuskotlin.smartoffice.biz.general

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.models.OfficeWorkMode
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.chain

fun ICorChainDsl<OfficeContext>.stubs(title: String, block: ICorChainDsl<OfficeContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == OfficeWorkMode.STUB && state == OfficeState.RUNNING }
}
