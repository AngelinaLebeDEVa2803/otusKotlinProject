package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.models.OfficeCommand
import kotlin.test.Test

class BizValidationDeleteTest: BaseBizValidationTest() {
    override val command: OfficeCommand = OfficeCommand.DELETE

    @Test fun correctId() = validationTestIdCorrect(command, processor)
    @Test fun trimId() = validationTestIdTrim(command, processor)
    @Test fun emptyId() = validationTestIdEmpty(command, processor)
    @Test fun formatId() = validationTestIdFormat(command, processor)

    @Test fun correctLock() = validationTestLockCorrect(command, processor)
    @Test fun trimLock() = validationTestLockTrim(command, processor)
    @Test fun emptyLock() = validationTestLockEmpty(command, processor)
    @Test fun formatLock() = validationTestLockFormat(command, processor)


}
