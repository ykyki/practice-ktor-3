package com.example.pk3

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json
import org.slf4j.event.Level

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.rootModule() {
    // configureSecurity()

    install(ContentNegotiation) {
        json(Json(DefaultJson) {
            prettyPrint = true
        })
    }
    install(StatusPages) {
        exception<Exception> {
            call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
            throw it
        }

        status(HttpStatusCode.NotFound) {
            call.respond(HttpStatusCode.NotFound, "Page not found")
        }
    }
    install(CallLogging) {
        level = Level.INFO

        filter { call -> call.request.path().startsWith("/") }
        format { call ->
            val uri = call.request.uri
            val userAgent = call.request.headers["User-Agent"]
            val httpMethod = call.request.httpMethod.value
            val status = call.response.status()

            "Request($uri, $httpMethod, $userAgent); Response($status)"
        }
    }

    routing {
        get("/") {
            call.respondText("Hello pk3!", ContentType.Text.Html)
        }
        get("/test-json") {
            call.respond(mapOf("hello" to "world"))
        }
        get("/test-error") {
            throw RuntimeException("エラーテストページにアクセスしました")
        }
    }
}
