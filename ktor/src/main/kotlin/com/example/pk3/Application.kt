package com.example.pk3

import com.example.pk3.domain.tutorial.customer.CustomerRepository
import com.example.pk3.domain.tutorial.customer.CustomerRepositoryInMemory
import com.example.pk3.router.registerCustomerRouter
import com.example.pk3.router.registerPublicFileRouter
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger
import org.slf4j.event.Level

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.rootModule() {
    // configureSecurity()
    configureRootContentNegotiation()
    configureRootStatusPages()
    configureRootLogging()
    configureRootKoin(rootDiModule)

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

fun Application.configureRootContentNegotiation() {
    install(ContentNegotiation) {
        json(Json(DefaultJson) {
            prettyPrint = true
        })
    }
}

fun Application.configureRootStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> {
            call.respond(HttpStatusCode.BadRequest, it.message.toString())
        }
        exception<NotFoundException> {
            call.respond(HttpStatusCode.NotFound, it.message.toString())
        }

        status(HttpStatusCode.NotFound) {
            call.respond(HttpStatusCode.NotFound, "Page not found")
        }
        status(HttpStatusCode.InternalServerError) {
            call.respond(HttpStatusCode.InternalServerError, "Internal server error")
        }
    }
}

fun Application.configureRootLogging() {
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

fun Application.configureRootKoin(koinModule: Module) {
    install(Koin) {
        SLF4JLogger()
        modules(koinModule)
    }
}

val rootDiModule = module {
    single<CustomerRepository> { CustomerRepositoryInMemory() }
}
