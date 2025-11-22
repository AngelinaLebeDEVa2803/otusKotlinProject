package ru.otus.otuskotlin.smartoffice.biz.repo

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.models.OfficeWorkMode
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != OfficeWorkMode.STUB }
    handle {
        bookingResponse = bookingRepoDone
        bookingsResponse = bookingsRepoDone
        state = when (val st = state) {
            OfficeState.RUNNING -> OfficeState.FINISHING
            else -> st
        }
    }
}
