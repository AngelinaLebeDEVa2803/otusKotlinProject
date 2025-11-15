package ru.otus.otuskotlin.smartoffice.biz.stubs

import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeError
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs

fun ICorChainDsl<OfficeContext>.stubValidationBadFloorId(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для идентификатора floorId
    """.trimIndent()
    on { stubCase == OfficeStubs.BAD_FLOOR_ID && state == OfficeState.RUNNING }
    handle {
        fail(
            OfficeError(
                group = "validation",
                code = "validation-floorId",
                field = "floorId",
                message = "Wrong floorId field"
            )
        )
    }
}
