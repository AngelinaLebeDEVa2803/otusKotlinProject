package ru.otus.otuskotlin.smartoffice.biz.stubs

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeError
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs

fun ICorChainDsl<OfficeContext>.stubValidationBadStatus(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для статуса бронирования
    """.trimIndent()
    on { stubCase == OfficeStubs.BAD_STATUS && state == OfficeState.RUNNING }
    handle {
        fail(
            OfficeError(
                group = "validation",
                code = "validation-status",
                field = "status",
                message = "Wrong status field"
            )
        )
    }
}
