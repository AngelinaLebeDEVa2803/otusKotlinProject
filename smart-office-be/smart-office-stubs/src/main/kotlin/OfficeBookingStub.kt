package ru.otus.otuskotlin.smartoffice.stubs

import ru.otus.otuskotlin.smartoffice.common.models.OfficeBooking
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStubExample.MY_BOOKING1

object OfficeBookingStub {
    fun get(): OfficeBooking = MY_BOOKING1.copy()

    fun prepareResult(block: OfficeBooking.() -> Unit): OfficeBooking = get().apply(block)

//    fun prepareSearchList(filter: String, type: MkplDealSide) = listOf(
//        mkplAdDemand("d-666-01", filter, type),
//        mkplAdDemand("d-666-02", filter, type),
//        mkplAdDemand("d-666-03", filter, type),
//        mkplAdDemand("d-666-04", filter, type),
//        mkplAdDemand("d-666-05", filter, type),
//        mkplAdDemand("d-666-06", filter, type),
//    )
//
//    fun prepareOffersList(filter: String, type: MkplDealSide) = listOf(
//        mkplAdSupply("s-666-01", filter, type),
//        mkplAdSupply("s-666-02", filter, type),
//        mkplAdSupply("s-666-03", filter, type),
//        mkplAdSupply("s-666-04", filter, type),
//        mkplAdSupply("s-666-05", filter, type),
//        mkplAdSupply("s-666-06", filter, type),
//    )

//    private fun mkplAdDemand(id: String, filter: String, type: MkplDealSide) =
//        mkplAd(AD_DEMAND_BOLT1, id = id, filter = filter, type = type)
//
//    private fun mkplAdSupply(id: String, filter: String, type: MkplDealSide) =
//        mkplAd(AD_SUPPLY_BOLT1, id = id, filter = filter, type = type)
//
//    private fun mkplAd(base: MkplAd, id: String, filter: String, type: MkplDealSide) = base.copy(
//        id = MkplAdId(id),
//        title = "$filter $id",
//        description = "desc $filter $id",
//        adType = type,
//    )

}
