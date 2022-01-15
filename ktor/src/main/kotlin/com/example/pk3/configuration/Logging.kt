package com.example.pk3.configuration

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import org.slf4j.event.Level

fun Application.configurLogging() {
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
}
