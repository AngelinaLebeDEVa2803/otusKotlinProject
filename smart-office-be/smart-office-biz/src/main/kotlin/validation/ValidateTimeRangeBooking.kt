package ru.otus.otuskotlin.smartoffice.biz.validation

import kotlin.time.Duration.Companion.hours
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.validateTimeRangeBooking(title: String) = worker {
    this.title = title

    on { bookingValidating.endTime - bookingValidating.startTime > 12.hours } // ещё минималку задать
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