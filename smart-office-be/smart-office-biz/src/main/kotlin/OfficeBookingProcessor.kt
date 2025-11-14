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

//                worker("Очистка userId") { bookingValidating.userId = OfficeUserId(bookingValidating.userId.asString().trim()) }
//                validateUserIdNotEmpty("Проверка на непустой userId")
//                validateUserIdFormat("Проверка формата userId")
                validateUserId("Проверка userId")

                worker("Очистка floorId") { bookingValidating.floorId = OfficeFloorId(bookingValidating.floorId.asString().trim()) }
                validateFloorIdNotEmpty("Проверка на непустой floorId")
                validateFloorIdFormat("Проверка формата floorId")

                worker("Очистка roomId") { bookingValidating.roomId = OfficeRoomId(bookingValidating.roomId.asString().trim()) }
                validateRoomIdNotEmpty("Проверка на непустой roomId")
                validateRoomIdFormat("Проверка формата roomId")

                worker("Очистка workspaceId") { bookingValidating.workspaceId = OfficeWorkspaceId(bookingValidating.workspaceId.asString().trim()) }
                validateWorkspaceIdNotEmpty("Проверка на непустой workspaceId")
                validateWorkspaceIdFormat("Проверка формата workspaceId")

                validateStartTimeBooking("Проверка корректности startTime для будущего бронирования")
                validateEndTimeBooking("Проверка корректности endTime для будущего бронирования")
                validateTimeRangeBooking("Проверка периода будущего бронирования")

                validateStatus("Проверка статуса при создании брони")

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

                worker("Очистка id") { bookingValidating.id = OfficeBookingId(bookingValidating.id.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdFormat("Проверка формата id бронирования")

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

                worker("Очистка id") { bookingValidating.id = OfficeBookingId(bookingValidating.id.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdFormat("Проверка формата id бронирования")

                worker("Очистка lock") { bookingValidating.lock = OfficeBookingLock(bookingValidating.lock.asString().trim()) }
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")

                worker("Очистка userId") { bookingValidating.userId = OfficeUserId(bookingValidating.userId.asString().trim()) }
                validateUserIdNotEmpty("Проверка на непустой userId")
                validateUserIdFormat("Проверка формата userId")

                worker("Очистка floorId") { bookingValidating.floorId = OfficeFloorId(bookingValidating.floorId.asString().trim()) }
                validateFloorIdNotEmpty("Проверка на непустой floorId")
                validateFloorIdFormat("Проверка формата floorId")

                worker("Очистка roomId") { bookingValidating.roomId = OfficeRoomId(bookingValidating.roomId.asString().trim()) }
                validateRoomIdNotEmpty("Проверка на непустой roomId")
                validateRoomIdFormat("Проверка формата roomId")

                worker("Очистка workspaceId") { bookingValidating.workspaceId = OfficeWorkspaceId(bookingValidating.workspaceId.asString().trim()) }
                validateWorkspaceIdNotEmpty("Проверка на непустой workspaceId")
                validateWorkspaceIdFormat("Проверка формата workspaceId")

                validateStartTimeBooking("Проверка корректности startTime для будущего бронирования")
                validateEndTimeBooking("Проверка корректности endTime для будущего бронирования")
                validateTimeRangeBooking("Проверка периода будущего бронирования")

                validateStatus("Проверка статуса при обновлении брони")

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

                worker("Очистка id") { bookingValidating.id = OfficeBookingId(bookingValidating.id.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdFormat("Проверка формата id бронирования")

                worker("Очистка lock") { bookingValidating.lock = OfficeBookingLock(bookingValidating.lock.asString().trim()) }
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")

                finishBookingValidation("Завершение проверок")
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
            validation {
                worker("Копируем поля в bookingFilterValidating") { bookingFilterValidating = bookingFilterRequest.deepCopy() }

                worker("Очистка userId") { bookingFilterValidating.userId = OfficeUserId(bookingFilterValidating.userId.asString().trim()) }
                validateUserIdNotEmptyFilter("Проверка на непустой userId")
                validateUserIdFormatFilter("Проверка формата userId")

                validateTimeRangeFilter("Проверка периода бронирования")

                finishBookingFilterValidation("Завершение проверок")
            }
        }
    }.build()
}
