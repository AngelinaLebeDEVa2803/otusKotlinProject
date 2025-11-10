package ru.otus.otuskotlin.smartoffice.app.kafka

import ru.otus.otuskotlin.smartoffice.app.common.IOfficeAppSettings
import ru.otus.otuskotlin.smartoffice.biz.OfficeBookingProcessor
import ru.otus.otuskotlin.smartoffice.common.OfficeCorSettings
import ru.otus.otuskotlin.smartoffice.logging.common.OfficeLoggerProvider
import ru.otus.otuskotlin.smartoffice.logging.jvm.officeLoggerLogback

class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val kafkaTopicInV1: String = KAFKA_TOPIC_IN_V1,
    val kafkaTopicOutV1: String = KAFKA_TOPIC_OUT_V1,
    override val corSettings: OfficeCorSettings = OfficeCorSettings(
        loggerProvider = OfficeLoggerProvider { officeLoggerLogback(it) }
    ),
    override val processor: OfficeBookingProcessor = OfficeBookingProcessor(corSettings),
): IOfficeAppSettings {
    companion object {
        const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
        const val KAFKA_TOPIC_IN_V1_VAR = "KAFKA_TOPIC_IN_V1"
        const val KAFKA_TOPIC_OUT_V1_VAR = "KAFKA_TOPIC_OUT_V1"
        const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "localhost:9092").split("\\s*[,; ]\\s*") }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "smartoffice" }
        val KAFKA_TOPIC_IN_V1 by lazy { System.getenv(KAFKA_TOPIC_IN_V1_VAR) ?: "smartoffice-booking-v1-in" }
        val KAFKA_TOPIC_OUT_V1 by lazy { System.getenv(KAFKA_TOPIC_OUT_V1_VAR) ?: "smartoffice-booking-v1-out" }
    }
}
