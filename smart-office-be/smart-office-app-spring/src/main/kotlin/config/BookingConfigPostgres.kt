package ru.otus.otuskotlin.smartoffice.app.spring.config

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.otus.otuskotlin.smartoffice.repo.pgjvm.SqlProperties

@ConfigurationProperties(prefix = "psql")
data class BookingConfigPostgres(
    var host: String = "localhost",
    var port: Int = 5432,
    var user: String = "postgres",
    var password: String = "smart123!",
    var database: String = "smartoffice_bookings",
    var schema: String = "public",
    var table: String = "bookings",
) {
    val psql: SqlProperties = SqlProperties(
        host = host,
        port = port,
        user = user,
        password = password,
        database = database,
        schema = schema,
        table = table,
    )
}