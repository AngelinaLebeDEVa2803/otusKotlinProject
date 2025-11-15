package ru.otus.otuskotlin.smartoffice.biz.stubs

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeError
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs

fun ICorChainDsl<OfficeContext>.stubValidationBadEndTime(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для времени окончания бронирования endTime
    """.trimIndent()
    on { stubCase == OfficeStubs.BAD_END_TIME && state == OfficeState.RUNNING }
    handle {
        fail(
            OfficeError(
                group = "validation",
                code = "validation-endTime",
                field = "endTime",
                message = "Wrong endTime field"
            )
        )
    }
}
