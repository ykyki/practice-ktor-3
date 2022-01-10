package com.example.pk3

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.rootModule() {
    // configureRouting()
    // configureSecurity()
    // configureHTTP()
    // configureMonitoring()
    // configureSerialization()

    routing {
        get("/") {
            call.respondText("Hello pk3444!", ContentType.Text.Html)
        }
    }
}
