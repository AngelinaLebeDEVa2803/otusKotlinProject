package ru.otus.otuskotlin.smartoffice.biz.stubs

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeError
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs

fun ICorChainDsl<OfficeContext>.stubNotFound(title: String) = worker {
    this.title = title
    this.description = """
        Бронирование не найдено
    """.trimIndent()
    on { stubCase == OfficeStubs.NOT_FOUND && state == OfficeState.RUNNING }
    handle {
        fail(
            OfficeError(
                group = "logic",  //validation
                code = "logic-notFound",
                message = "No booking with id provided"
            )
        )
    }
}
