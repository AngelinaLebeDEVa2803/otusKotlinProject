package ru.otus.otuskotlin.smartoffice.biz.stubs

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeError
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs

fun ICorChainDsl<OfficeContext>.stubCannotUpdate(title: String) = worker {
    this.title = title
    this.description = """
        Невозможно обновить бронирование (например, отменить нельзя, если завершено и т.п.)
    """.trimIndent()
    on { stubCase == OfficeStubs.CANNOT_UPDATE && state == OfficeState.RUNNING }
    handle {
        fail(
            OfficeError(
                group = "logic",
                code = "logic-cannotUpdate",
                message = "Cannot update booking"
            )
        )
    }
}
