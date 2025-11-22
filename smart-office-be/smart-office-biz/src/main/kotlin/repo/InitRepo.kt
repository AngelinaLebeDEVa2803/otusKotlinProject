package ru.otus.otuskotlin.smartoffice.biz.repo

import ru.otus.otuskotlin.smartoffice.biz.exceptions.OfficeBookingDbNotConfiguredException
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorSystem
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeWorkMode
import ru.otus.otuskotlin.smartoffice.common.repo.IRepoBooking
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        bookingRepo = when {
            workMode == OfficeWorkMode.TEST -> corSettings.repoTest
            workMode == OfficeWorkMode.STUB -> corSettings.repoStub
            else -> corSettings.repoProd
        }
        if (workMode != OfficeWorkMode.STUB && bookingRepo == IRepoBooking.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = OfficeBookingDbNotConfiguredException(workMode)
            )
        )
    }
}
