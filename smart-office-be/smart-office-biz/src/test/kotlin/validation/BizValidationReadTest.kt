package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.models.OfficeCommand
import kotlin.test.Test

class BizValidationReadTest: BaseBizValidationTest() {
    override val command: OfficeCommand = OfficeCommand.READ

    @Test fun correctId() = validationTestIdCorrect(command, processor)
    @Test fun trimId() = validationTestIdTrim(command, processor)
    @Test fun emptyId() = validationTestIdEmpty(command, processor)
    @Test fun formatId() = validationTestIdFormat(command, processor)


}
