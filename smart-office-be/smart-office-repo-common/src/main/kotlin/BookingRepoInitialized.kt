package ru.otus.otuskotlin.smartoffice.repo.common

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking

/**
 * Делегат для всех репозиториев, позволяющий инициализировать базу данных предзагруженными данными
 */
class BookingRepoInitialized(
    private val repo: IRepoBookingInitializable,
    initObjects: Collection<OfficeBooking> = emptyList(),
) : IRepoBookingInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<OfficeBooking> = save(initObjects).toList()
}
