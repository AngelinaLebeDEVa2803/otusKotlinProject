package ru.otus.otuskotlin.smartoffice.app.spring.repo

import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.mappers.v1.*
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub
import kotlin.test.Test
//
//internal abstract class AdRepoBaseV1Test {
//    protected abstract var webClient: WebTestClient
//    private val debug = AdDebug(mode = AdRequestDebugMode.TEST)
//    protected val uuidNew = "10000000-0000-0000-0000-000000000001"
//
//    @Test
//    open fun createAd() = testRepoAd(
//        "create",
//        AdCreateRequest(
//            ad = OfficeBookingStub.get().toTransportCreate(),
//            debug = debug,
//        ),
//        prepareCtx(OfficeBookingStub.prepareResult {
//            id = MkplAdId(uuidNew)
//            ownerId = MkplUserId.NONE
//            lock = MkplAdLock.NONE
//        })
//            .toTransportCreate()
//            .copy(responseType = "create")
//    )
//
//    @Test
//    open fun readAd() = testRepoAd(
//        "read",
//        AdReadRequest(
//            ad = OfficeBookingStub.get().toTransportRead(),
//            debug = debug,
//        ),
//        prepareCtx(OfficeBookingStub.get())
//            .toTransportRead()
//            .copy(responseType = "read")
//    )
//
//    @Test
//    open fun updateAd() = testRepoAd(
//        "update",
//        AdUpdateRequest(
//            ad = OfficeBookingStub.prepareResult { title = "add" }.toTransportUpdate(),
//            debug = debug,
//        ),
//        prepareCtx(OfficeBookingStub.prepareResult { title = "add" })
//            .toTransportUpdate().copy(responseType = "update")
//    )
//
//    @Test
//    open fun deleteAd() = testRepoAd(
//        "delete",
//        AdDeleteRequest(
//            ad = OfficeBookingStub.get().toTransportDelete(),
//            debug = debug,
//        ),
//        prepareCtx(OfficeBookingStub.get())
//            .toTransportDelete()
//            .copy(responseType = "delete")
//    )
//
//    @Test
//    open fun searchAd() = testRepoAd(
//        "search",
//        AdSearchRequest(
//            adFilter = AdSearchFilter(adType = DealSide.SUPPLY),
//            debug = debug,
//        ),
//        OfficeContext(
//            state = MkplState.RUNNING,
//            adsResponse = OfficeBookingStub.prepareSearchList("xx", MkplDealSide.SUPPLY)
//                .onEach { it.permissionsClient.clear() }
//                .sortedBy { it.id.asString() }
//                .toMutableList()
//        )
//            .toTransportSearch().copy(responseType = "search")
//    )
//
//    @Test
//    open fun offersAd() = testRepoAd(
//        "offers",
//        AdOffersRequest(
//            ad = OfficeBookingStub.get().toTransportRead(),
//            debug = debug,
//        ),
//        OfficeContext(
//            state = MkplState.RUNNING,
//            adResponse = OfficeBookingStub.get(),
//            adsResponse = OfficeBookingStub.prepareSearchList("xx", MkplDealSide.SUPPLY)
//                .onEach { it.permissionsClient.clear() }
//                .sortedBy { it.id.asString() }
//                .toMutableList()
//        )
//            .toTransportOffers().copy(responseType = "offers")
//    )
//
//    private fun prepareCtx(ad: MkplAd) = OfficeContext(
//        state = MkplState.RUNNING,
//        adResponse = ad.apply {
//            // Пока не реализована эта функциональность
//            permissionsClient.clear()
//        },
//    )
//
//    private inline fun <reified Req : Any, reified Res : IResponse> testRepoAd(
//        url: String,
//        requestObj: Req,
//        expectObj: Res,
//    ) {
//        webClient
//            .post()
//            .uri("/v1/ad/$url")
//            .contentType(MediaType.APPLICATION_JSON)
//            .body(BodyInserters.fromValue(requestObj))
//            .exchange()
//            .expectStatus().isOk
//            .expectBody(Res::class.java)
//            .value {
//                println("RESPONSE: $it")
//                val sortedResp: IResponse = when (it) {
//                    is AdSearchResponse -> it.copy(ads = it.ads?.sortedBy { it.id })
//                    is AdOffersResponse -> it.copy(ads = it.ads?.sortedBy { it.id })
//                    else -> it
//                }
//                assertThat(sortedResp).isEqualTo(expectObj)
//            }
//    }
//}
