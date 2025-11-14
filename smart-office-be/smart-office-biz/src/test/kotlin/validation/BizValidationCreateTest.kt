package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.models.OfficeCommand
import kotlin.test.Test

class BizValidationCreateTest: BaseBizValidationTest() {
    override val command: OfficeCommand = OfficeCommand.CREATE

    @Test fun correctUserId() = validationTestUserIdCorrect(command, processor)
    @Test fun trimUserId() = validationTestUserIdTrim(command, processor)
    @Test fun emptyUserId() = validationTestUserIdEmpty(command, processor)
    @Test fun formatUserId() = validationTestUserIdFormat(command, processor)

    @Test fun correctFloorId() = validationTestFloorIdCorrect(command, processor)
    @Test fun trimFloorId() = validationTestFloorIdTrim(command, processor)
    @Test fun emptyFloorId() = validationTestFloorIdEmpty(command, processor)
    @Test fun formatFloorId() = validationTestFloorIdFormat(command, processor)

    @Test fun correctRoomId() = validationTestRoomIdCorrect(command, processor)
    @Test fun trimRoomId() = validationTestRoomIdTrim(command, processor)
    @Test fun emptyRoomId() = validationTestRoomIdEmpty(command, processor)
    @Test fun formatRoomId() = validationTestRoomIdFormat(command, processor)

    @Test fun correctWorkspaceId() = validationTestWorkspaceIdCorrect(command, processor)
    @Test fun trimWorkspaceId() = validationTestWorkspaceIdTrim(command, processor)
    @Test fun emptyWorkspaceId() = validationTestWorkspaceIdEmpty(command, processor)
    @Test fun formatWorkspaceId() = validationTestWorkspaceIdFormat(command, processor)

    // далее проверки на статус и даты

}
