package ru.otus.otuskotlin.smartoffice.stubs

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStubExample.MY_BOOKING1

object OfficeBookingStub {
    fun get(): OfficeBooking = MY_BOOKING1.copy()

    fun prepareResult(block: OfficeBooking.() -> Unit): OfficeBooking = get().apply(block)


}
