package ru.otus.otuskotlin.marketplace.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.stubs.OfficeStubs

data class OfficeContext(
    var command: OfficeCommand = OfficeCommand.NONE,
    var state: OfficeState = OfficeState.NONE,
    val errors: MutableList<OfficeError> = mutableListOf(),

    var workMode: OfficeWorkMode = OfficeWorkMode.PROD,
    var stubCase: OfficeStubs = OfficeStubs.NONE,

    var requestId: OfficeRequestId = OfficeRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    //var adRequest: MkplAd = MkplAd(),
    //var adFilterRequest: MkplAdFilter = MkplAdFilter(),

    //var adResponse: MkplAd = MkplAd(),
    //var adsResponse: MutableList<MkplAd> = mutableListOf(),

    )
