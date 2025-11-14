package ru.otus.otuskotlin.smartoffice.biz.validation

import kotlin.time.Duration.Companion.hours
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingId
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.chain
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.validateTimeRangeBooking(title: String) = chain {
    this.title = title
    this.description = """
        Валидация времени продолжительности бронирования. 
        Бронирование должно быть не больше 12 часов и не меньше 1 часа.
    """.trimIndent()
    on { state == OfficeState.RUNNING }
    worker {
        this.title = "Проверка на максимальную продолжительность"
        this.description = this.title
        on { state == OfficeState.RUNNING && bookingValidating.endTime - bookingValidating.startTime > 12.hours }
        handle {
            fail(
                errorValidation(
                    field = "startTime_endTime",
                    violationCode = "badValue",
                    description = "Incorrect time range. Shift cannot be more than 12 hours"
                )
            )
        }
    }
    worker {
        this.title = "Проверка на минимальную продолжительность"
        this.description = this.title
        on { state == OfficeState.RUNNING && bookingValidating.endTime - bookingValidating.startTime < 1.hours }
        handle {
            fail(
                errorValidation(
                    field = "startTime_endTime",
                    violationCode = "badValue",
                    description = "Incorrect time range. Shift cannot be less than 1 hour"
                )
            )
        }
    }

}

fun ICorChainDsl<OfficeContext>.validateTimeRange(title: String) = worker {
    this.title = title

    on { bookingFilterValidating.startTime > bookingFilterValidating.endTime }
    handle {
        fail(
            errorValidation(
                field = "startTime_endTime",
                violationCode = "badValue",
                description = "Incorrect time range: startTime cannot be greater than endTime"
            )
        )
    }
}

fun ICorChainDsl<OfficeContext>.validateTimeRangeFilter(title: String) = worker {
    this.title = title

    on { bookingFilterValidating.startTime > bookingFilterValidating.endTime }
    handle {
        fail(
            errorValidation(
                field = "startTime_endTime",
                violationCode = "badValue",
                description = "Incorrect time range: startTime cannot be greater than endTime"
            )
        )
    }
}