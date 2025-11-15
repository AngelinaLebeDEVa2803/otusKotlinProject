package ru.otus.otuskotlin.smartoffice.biz.stubs

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeError
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs

fun ICorChainDsl<OfficeContext>.stubValidationBadTimeRange(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для периода бронирования
    """.trimIndent()
    on { stubCase == OfficeStubs.BAD_TIME_RANGE && state == OfficeState.RUNNING }
    handle {
        fail(
            OfficeError(
                group = "validation",
                code = "validation-timeRange",
                message = "Incorrect time range"
            )
        )
    }
}
