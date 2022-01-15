package com.example.pk3.configuration

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json

fun Application.configureContentNegotiation() {
    install(ContentNegotiation) {
        json(Json(DefaultJson) {
            prettyPrint = true
        })
    }
}
