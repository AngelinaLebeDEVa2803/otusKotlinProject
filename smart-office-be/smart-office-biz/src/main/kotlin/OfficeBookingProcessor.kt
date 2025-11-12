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

                stubValidationBadStatus("Имитация ошибки валидации status")  // отдельно для create?

                stubDbError("Имитация ошибки работы с БД")
                stubCannotCreate("Невозможно создать бронь (занято место, лимит броней превышен etc)")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }


    }.build()
}

//NONE,
//SUCCESS, по всем
//NOT_FOUND, update delete read
//BAD_ID, не create

//BAD_USER_ID,
//BAD_FLOOR_ID,
//BAD_ROOM_ID,
//BAD_WORKSPACE_ID,

//BAD_START_TIME,
//BAD_END_TIME,
//BAD_TIME_RANGE,

//BAD_STATUS,

//CANNOT_CREATE,

//CANNOT_UPDATE,
//CANNOT_DELETE,
//DB_ERROR,