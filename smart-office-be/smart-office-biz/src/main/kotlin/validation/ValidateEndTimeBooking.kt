package ru.otus.otuskotlin.smartoffice.biz.validation

import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.hours
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.validateEndTimeBooking(title: String) = worker {
    this.title = title

    val now = Clock.System.now()
    val minAllowedTime = now + 2.hours

    on { bookingValidating.endTime < minAllowedTime  }
    handle {
        fail(
            errorValidation(
                field = "endTime",
                violationCode = "badValue",
                description = "value must be in future"
            )
        )
    }
}