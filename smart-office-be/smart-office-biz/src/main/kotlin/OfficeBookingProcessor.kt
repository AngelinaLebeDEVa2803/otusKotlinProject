package ru.otus.otuskotlin.smartoffice.biz

import ru.otus.otuskotlin.smartoffice.biz.general.*
import ru.otus.otuskotlin.smartoffice.biz.stubs.*
import ru.otus.otuskotlin.smartoffice.biz.validation.*
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.cor.rootChain
import ru.otus.otuskotlin.smartoffice.cor.worker


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
            validation {
                worker("Копируем поля в bookingValidating") { bookingValidating = bookingRequest.deepCopy() }
                worker("Очистка id") { bookingValidating.id = OfficeBookingId.NONE }
                validateUserId("Проверка userId")
                validateFloorId("Проверка floorId")
                validateRoomId("Проверка roomId")
                validateWorkspaceId("Проверка workspaceId")

                validateStartTimeNotNone("Проверка, что startTime задан")
                validateEndTimeNotNone("Проверка, что endTime задан")
                validateStatusCreate("Проверка, что статус ACTIVE")
                validateTimeRange("Проверка, что дата начала меньше даты окончания")
                validateTimeRangeBooking("Проверка продолжительности бронирования")
                validateStatusTime("Проверка корректности startTime/endTime для будущего бронирования")

                finishBookingValidation("Завершение проверок")
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
            validation {
                worker("Копируем поля в bookingValidating") { bookingValidating = bookingRequest.deepCopy() }
                validateId("Проверка id бронирования")
                finishBookingValidation("Завершение проверок")
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
                stubCannotUpdate("Невозможно изменить бронь")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в bookingValidating") { bookingValidating = bookingRequest.deepCopy() }
                validateId("Проверка id бронирования")
                validateLock("Проверка lock")
                validateUserId("Проверка userId")
                validateFloorId("Проверка floorId")
                validateRoomId("Проверка roomId")
                validateWorkspaceId("Проверка workspaceId")

                validateStartTimeNotNone("Проверка, что startTime задан")
                validateEndTimeNotNone("Проверка, что endTime задан")
                validateStatusNotEmpty("Проверка статуса")
                validateTimeRange("Проверка, что дата начала меньше даты окончания")
                validateTimeRangeBooking("Проверка продолжительности бронирования")
                validateStatusTime("Проверка статуса и временных полей при обновлении брони")

                finishBookingValidation("Завершение проверок")
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
            validation {
                worker("Копируем поля в bookingValidating") { bookingValidating = bookingRequest.deepCopy() }
                validateId("Проверка id бронирования")
                validateLock("Проверка lock")
                finishBookingValidation("Завершение проверок")
            }
        }
        operation("Список бронирований пользователя", OfficeCommand.ALL) {
            stubs("Обработка стабов") {
                stubAllSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadUserId("Имитация ошибки валидации userId")
                stubValidationBadTimeRange("Имитация ошибки валидации периода бронирования")
//                stubValidationBadStatus("Имитация ошибки валидации status")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в bookingFilterValidating") { bookingFilterValidating = bookingFilterRequest.deepCopy() }
                validateUserIdFilter("Проверка userId")
                validateTimeRangeFilter("Проверка периода бронирования")
                finishBookingFilterValidation("Завершение проверок")
            }
        }
    }.build()
}
