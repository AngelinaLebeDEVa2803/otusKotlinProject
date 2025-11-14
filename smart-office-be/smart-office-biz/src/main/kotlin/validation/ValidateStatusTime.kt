package ru.otus.otuskotlin.smartoffice.biz.validation

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.helpers.errorValidation
import ru.otus.otuskotlin.smartoffice.common.helpers.fail
import ru.otus.otuskotlin.smartoffice.common.models.OfficeState
import ru.otus.otuskotlin.smartoffice.common.models.OfficeBookingStatus
import ru.otus.otuskotlin.smartoffice.cor.ICorChainDsl
import ru.otus.otuskotlin.smartoffice.cor.chain
import ru.otus.otuskotlin.smartoffice.cor.worker
import kotlin.time.Duration.Companion.hours


fun ICorChainDsl<OfficeContext>.validateStatusTime(title: String) = chain {
    this.title = title
    this.description = """
        Проверка корректности статуса и полей startTime и endTime.
        При статусе ACTIVE или CANCELLED startTime должно быть через час минимум.
        При статусе COMPLETED endTime должен быть позже момента выполнения запроса.        
    """.trimIndent()
    val statusFuture = arrayOf(OfficeBookingStatus.ACTIVE, OfficeBookingStatus.CANCELLED)
    val now = Clock.System.now()
    val minAllowedStartTime = now + 1.hours
    val minAllowedEndTime = now + 2.hours

    on { state == OfficeState.RUNNING }
    worker {
        this.title = "Проверка startTime при статусах ACTIVE, CANCELLED"
        this.description = this.title
        on { state == OfficeState.RUNNING && statusFuture.contains(bookingValidating.status) && bookingValidating.startTime < minAllowedStartTime }
        handle {
            fail(
                errorValidation(
                    field = "startTime",
                    violationCode = "badValue",
                    description = "startTime must be in at least 1 hour"
                )
            )
        }
    }
//    worker { // избыточно
//        this.title = "Проверка endTime при статусах ACTIVE, CANCELLED"
//        this.description = this.title
//        on { state == OfficeState.RUNNING && statusFuture.contains(bookingValidating.status) && bookingValidating.endTime < minAllowedEndTime }
//        handle {
//            fail(
//                errorValidation(
//                    field = "endTime",
//                    violationCode = "badValue",
//                    description = "endTime must be in at least 2 hours"
//                )
//            )
//        }
//    }
//    worker {
//        this.title = "Проверка startTime при статусе COMPLETED"
//        this.description = this.title
//        on { state == OfficeState.RUNNING && bookingValidating.status == OfficeBookingStatus.COMPLETED && bookingValidating.startTime > now }
//        handle {
//            fail(
//                errorValidation(
//                    field = "startTime",
//                    violationCode = "badValue",
//                    description = "startTime must be in past"
//                )
//            )
//        }
//    }
    worker {
        this.title = "Проверка endTime при статусе COMPLETED"
        this.description = this.title
        on { state == OfficeState.RUNNING && bookingValidating.status == OfficeBookingStatus.COMPLETED && bookingValidating.endTime > now }
        handle {
            fail(
                errorValidation(
                    field = "endTime",
                    violationCode = "badValue",
                    description = "endTime must be in past"
                )
            )
        }
    }
}