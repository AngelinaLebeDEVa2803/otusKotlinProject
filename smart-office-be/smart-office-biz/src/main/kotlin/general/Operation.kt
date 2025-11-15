package ru.otus.otuskotlin.smartoffice.biz.general

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.OfficeCommand
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.chain

fun ICorChainDsl<OfficeContext>.operation(
    title: String,
    command: OfficeCommand,
    block: ICorChainDsl<OfficeContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == OfficeState.RUNNING }
}
