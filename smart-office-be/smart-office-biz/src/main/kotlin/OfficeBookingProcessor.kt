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
                stubValidationBadUserId("Имитация ошибки валидации userId")
                stubValidationBadFloorId("Имитация ошибки валидации floorId")
                stubValidationBadRoomId("Имитация ошибки валидации roomId")
                stubValidationBadWorkspaceId("Имитация ошибки валидации workspaceId")
                stubValidationBadStartTime("Имитация ошибки валидации startTime")
                stubValidationBadEndTime("Имитация ошибки валидации endTime")
                stubValidationBadTimeRange("Имитация ошибки валидации периода бронирования")
                stubValidationBadStatus("Имитация ошибки валидации status")
                stubDbError("Имитация ошибки работы с БД")
                stubCannotCreate("Невозможно создать бронь (занято место, лимит броней превышен etc)")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Чтение бронирования по id", OfficeCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id брони")
                stubDbError("Имитация ошибки работы с БД")
                stubNotFound("Бронирование не найдено")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Обновление бронирования", OfficeCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id брони")
                stubValidationBadUserId("Имитация ошибки валидации userId")
                stubValidationBadFloorId("Имитация ошибки валидации floorId")
                stubValidationBadRoomId("Имитация ошибки валидации roomId")
                stubValidationBadWorkspaceId("Имитация ошибки валидации workspaceId")
                stubValidationBadStartTime("Имитация ошибки валидации startTime")
                stubValidationBadEndTime("Имитация ошибки валидации endTime")
                stubValidationBadTimeRange("Имитация ошибки валидации периода бронирования")
                stubValidationBadStatus("Имитация ошибки валидации status")
                stubDbError("Имитация ошибки работы с БД")
                stubNotFound("Бронирование не найдено")
                stubCannotUpdate("Невозможно изменить бронь (отменить нельзя, если завершено и т.п.)")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Удаление бронирования", OfficeCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id брони")
                stubDbError("Имитация ошибки работы с БД")
                stubNotFound("Бронирование не найдено")
                stubCannotDelete("Невозможно удалить бронь (например, если начата)")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Список бронирований пользователя", OfficeCommand.ALL) {
            stubs("Обработка стабов") {
                stubAllSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadUserId("Имитация ошибки валидации userId")
                stubValidationBadStartTime("Имитация ошибки валидации startTime")
                stubValidationBadEndTime("Имитация ошибки валидации endTime")
                stubValidationBadTimeRange("Имитация ошибки валидации периода бронирования")
                stubValidationBadStatus("Имитация ошибки валидации status")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }

    }.build()
}
