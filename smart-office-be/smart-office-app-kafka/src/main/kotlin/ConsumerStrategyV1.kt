package ru.otus.otuskotlin.smartoffice.app.kafka

import ru.otus.otuskotlin.smartoffice.api.v1.apiV1RequestDeserialize
import ru.otus.otuskotlin.smartoffice.api.v1.apiV1ResponseSerialize
import ru.otus.otuskotlin.smartoffice.api.v1.models.IRequest
import ru.otus.otuskotlin.smartoffice.api.v1.models.IResponse
import ru.otus.otuskotlin.smartoffice.common.OfficeContext
import ru.otus.otuskotlin.smartoffice.mappers.v1.fromTransport
import ru.otus.otuskotlin.smartoffice.mappers.v1.toTransportBooking

class ConsumerStrategyV1 : IConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: OfficeContext): String {
        val response: IResponse = source.toTransportBooking()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: OfficeContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}
