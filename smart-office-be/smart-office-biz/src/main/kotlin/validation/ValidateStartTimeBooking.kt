package ru.otus.otuskotlin.smartoffice.biz.validation

import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.hours
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.worker

fun ICorChainDsl<OfficeContext>.validateStartTimeBooking(title: String) = worker {
    this.title = title

    val now = Clock.System.now()
    val minAllowedTime = now + 1.hours

    on { bookingValidating.startTime < minAllowedTime  }
    handle {
        fail(
            errorValidation(
                field = "startTime",
                violationCode = "badValue",
                description = "value must be in future"
            )
        )
    }
}