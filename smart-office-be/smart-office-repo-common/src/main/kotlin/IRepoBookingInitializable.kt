package ru.otus.otuskotlin.smartoffice.repo.common

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.common.repo.IRepoAd

interface IRepoBookingInitializable: IRepoAd {
    fun save(bookings: Collection<OfficeBooking>) : Collection<OfficeBooking>
}
