package ru.otus.otuskotlin.smartoffice.app.spring.stub

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import ru.otus.otuskotlin.smartoffice.api.v1.apiV1RequestSerialize
import ru.otus.otuskotlin.smartoffice.api.v1.apiV1ResponseDeserialize
import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import kotlin.test.Test


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Suppress("unused")
class BookingControllerV1WsTest: BookingControllerBaseWsTest<IRequest, IResponse>("v1") {

    @LocalServerPort
    override var port: Int = 0

    override fun deserializeRs(response: String): IResponse = apiV1ResponseDeserialize(response)
    override fun serializeRq(request: IRequest): String = apiV1RequestSerialize(request)

    @Test
    fun wsCreate(): Unit = testWsApp(BookingCreateRequest(
        debug = BookingDebug(BookingRequestDebugMode.STUB, BookingRequestDebugStubs.SUCCESS),
        booking = BookingCreateObject(
            userId = "123",
            floorId = "floor_2",
            roomId = "444",
            workspaceId = "009",
            startTime = "2025-09-01T10:00:00Z",
            endTime = "2025-09-01T19:00:00Z",
            status = BookingStatus.ACTIVE,
        )
    )) { pl ->
        val mesInit = pl[0]
        val mesCreate = pl[1]
        assert(mesInit is BookingInitResponse)
        assert(mesInit.result == ResponseResult.SUCCESS)
        assert(mesCreate is BookingCreateResponse)
        assert(mesCreate.result == ResponseResult.SUCCESS)
    }
}
