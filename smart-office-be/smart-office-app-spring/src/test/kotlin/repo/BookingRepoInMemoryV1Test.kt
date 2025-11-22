package ru.otus.otuskotlin.smartoffice.app.spring.repo

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.slot
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import ru.otus.otuskotlin.smartoffice.app.spring.config.BookingConfig
import ru.otus.otuskotlin.smartoffice.app.spring.controllers.BookingControllerV1Fine
import ru.otus.otuskotlin.smartoffice.common.models.*
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingFilterRequest
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingIdRequest
import ru.otus.otuskotlin.smartoffice.common.repo.DbBookingRequest
import ru.otus.otuskotlin.smartoffice.common.repo.IRepoBooking
import ru.otus.otuskotlin.smartoffice.repo.common.BookingRepoInitialized
import ru.otus.otuskotlin.smartoffice.repo.inmemory.BookingRepoInMemory
import ru.otus.otuskotlin.smartoffice.stubs.OfficeBookingStub
import kotlin.test.Test

//// Temporary simple test with stubs
//@WebFluxTest(BookingControllerV1Fine::class, BookingConfig::class)
//internal class BookingRepoInMemoryV1Test : BookingRepoBaseV1Test() {
//    @Autowired
//    override lateinit var webClient: WebTestClient
//
//    @MockkBean
//    @Qualifier("testRepo")
//    lateinit var testTestRepo: IRepoBooking
//
//    @BeforeEach
//    fun tearUp() {
//        val slotAd = slot<DbBookingRequest>()
//        val slotId = slot<DbBookingIdRequest>()
//        val slotFl = slot<DbBookingFilterRequest>()
//        val repo = BookingRepoInitialized(
//            repo = BookingRepoInMemory(randomUuid = { uuidNew }),
//            initObjects = OfficeBookingStub.prepareSearchList("xx", MkplDealSide.SUPPLY) + OfficeBookingStub.get()
//        )
//        coEvery { testTestRepo.createAd(capture(slotAd)) } coAnswers { repo.createAd(slotAd.captured) }
//        coEvery { testTestRepo.readAd(capture(slotId)) } coAnswers { repo.readAd(slotId.captured) }
//        coEvery { testTestRepo.updateAd(capture(slotAd)) } coAnswers { repo.updateAd(slotAd.captured) }
//        coEvery { testTestRepo.deleteAd(capture(slotId)) } coAnswers { repo.deleteAd(slotId.captured) }
//        coEvery { testTestRepo.searchAd(capture(slotFl)) } coAnswers { repo.searchAd(slotFl.captured) }
//    }
//
//    @Test
//    override fun createAd() = super.createAd()
//
//    @Test
//    override fun readAd() = super.readAd()
//
//    @Test
//    override fun updateAd() = super.updateAd()
//
//    @Test
//    override fun deleteAd() = super.deleteAd()
//
//    @Test
//    override fun searchAd() = super.searchAd()
//
//    @Test
//    override fun offersAd() = super.offersAd()
//}
