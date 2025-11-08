package ru.otus.otuskotlin.smartoffice.app.spring.controllers

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.smartoffice.app.spring.base.OfficeAppSettings
import ru.otus.otuskotlin.smartoffice.api.v1.models.*
import ru.otus.otuskotlin.smartoffice.app.common.controllerHelper
import ru.otus.otuskotlin.smartoffice.mappers.v1.fromTransport
import ru.otus.otuskotlin.smartoffice.mappers.v1.toTransportBooking
import kotlin.reflect.KClass

@Suppress("unused")
@RestController
@RequestMapping("v1/booking")
class BookingControllerV1Fine(
    private val appSettings: OfficeAppSettings
) {

    @PostMapping("create")
    suspend fun create(@RequestBody request: BookingCreateRequest): BookingCreateResponse =
        process(appSettings, request = request, this::class, "create")

    @PostMapping("read")
    suspend fun  read(@RequestBody request: BookingReadRequest): BookingReadResponse =
        process(appSettings, request = request, this::class, "read")

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun  update(@RequestBody request: BookingUpdateRequest): BookingUpdateResponse =
        process(appSettings, request = request, this::class, "update")

    @PostMapping("delete")
    suspend fun  delete(@RequestBody request: BookingDeleteRequest): BookingDeleteResponse =
        process(appSettings, request = request, this::class, "delete")

    @PostMapping("all")
    suspend fun  all(@RequestBody request: BookingAllRequest): BookingAllResponse =
        process(appSettings, request = request, this::class, "all")


    companion object {
        suspend inline fun <reified Q : IRequest, reified R : IResponse> process(
            appSettings: OfficeAppSettings,
            request: Q,
            clazz: KClass<*>,
            logId: String,
        ): R = appSettings.controllerHelper(
            {
                fromTransport(request)
            },
            { toTransportBooking() as R },
            clazz,
            logId,
        )
    }
}
