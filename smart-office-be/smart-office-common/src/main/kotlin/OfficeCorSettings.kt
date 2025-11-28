package ru.otus.otuskotlin.smartoffice.common

import ru.otus.otuskotlin.smartoffice.logging.common.OfficeLoggerProvider
import ru.otus.otuskotlin.smartoffice.common.ws.IOfficeWsSessionRepo
import ru.otus.otuskotlin.smartoffice.common.repo.IRepoBooking

data class OfficeCorSettings(
    val loggerProvider: OfficeLoggerProvider = OfficeLoggerProvider(),
    val wsSessions: IOfficeWsSessionRepo = IOfficeWsSessionRepo.NONE,
    val repoStub: IRepoBooking = IRepoBooking.NONE,
    val repoTest: IRepoBooking = IRepoBooking.NONE,
    val repoProd: IRepoBooking = IRepoBooking.NONE,
) {
    companion object {
        val NONE = OfficeCorSettings()
    }
}
