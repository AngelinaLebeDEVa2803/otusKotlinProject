package ru.otus.otuskotlin.smartoffice.biz.repo

import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == OfficeState.RUNNING }
    handle {
        bookingRepoPrepare = bookingRepoRead.deepCopy().apply {
            userId = bookingValidated.userId
            floorId = bookingValidated.floorId
            roomId = bookingValidated.roomId
            workspaceId = bookingValidated.workspaceId
            startTime = bookingValidated.startTime
            endTime = bookingValidated.endTime
            status = bookingValidated.status
            lock = bookingValidated.lock
        }
    }
}
