package ru.otus.otuskotlin.smartoffice.biz.validation

import ru.otus.otuskotlin.smartoffice.common.models.OfficeCommand
import kotlin.test.Test

class BizValidationCreateTest: BaseBizValidationTest() {
    override val command: OfficeCommand = OfficeCommand.CREATE

    @Test fun correctUserId() = validationTestUserIdCorrect(command, processor)
    @Test fun trimUserId() = validationTestUserIdTrim(command, processor)
    @Test fun emptyUserId() = validationTestUserIdEmpty(command, processor)
    @Test fun formatUserId() = validationTestUserIdFormat(command, processor)



//    @Test fun correctTitle() = validationTitleCorrect(command, processor)
//    @Test fun trimTitle() = validationTitleTrim(command, processor)
//    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
//    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)
//
//    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
//    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
//    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
//    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)
}
