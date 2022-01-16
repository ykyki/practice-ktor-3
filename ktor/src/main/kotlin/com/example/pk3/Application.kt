package com.example.pk3

import com.example.pk3.configuration.*
import com.example.pk3.router.registerCustomerRouter
import com.example.pk3.router.registerPublicFileRouter
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.rootModule() {
    // configureSecurity()
    configureContentNegotiation()
    configureStatusPages()
    configureLogging()
    configureKoin(rootDiModule)

    rootRouter()
}

fun Application.rootRouter() {
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
    registerCustomerRouter()
    registerPublicFileRouter()
}
