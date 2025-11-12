package ru.otus.otuskotlin.smartoffice.biz

import ru.otus.otuskotlin.smartoffice.biz.general.*
import ru.otus.otuskotlin.smartoffice.biz.stubs.*
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.common.models.OfficeCommand
import ru.otus.otuskotlin.smartoffice.cor.rootChain


class OfficeBookingProcessor(
    private val corSettings: OfficeCorSettings = OfficeCorSettings.NONE
) {
    suspend fun exec(ctx: OfficeContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<OfficeContext> {
        initStatus("Инициализация статуса")

        operation("Создание бронирования", OfficeCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
//                stubValidationBadTitle("Имитация ошибки валидации заголовка")
//                stubValidationBadDescription("Имитация ошибки валидации описания")
//                stubDbError("Имитация ошибки работы с БД")
//                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }


    }.build()
}
