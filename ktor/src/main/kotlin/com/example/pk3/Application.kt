package com.example.pk3

import com.example.pk3.configuration.*
import com.example.pk3.configuration.authentication.AuthenticationGroup
import com.example.pk3.router.registerCustomerRouter
import com.example.pk3.router.registerPublicFileRouter
import com.example.subsample.Sample
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.rootModule() {
    configureContentNegotiation()
    configureStatusPage()
    configureLogging()
    configureKoin(rootDiModule)
    configureAuthentication()

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
        get("/test-sub-sample") {
            call.respondText("From sub sample project: " + Sample().get("foo"))
        }
        get("/test-error") {
            throw RuntimeException("エラーテストページにアクセスしました")
        }
        authenticate(AuthenticationGroup.DigestSampleA.groupName) {
            route("/test-digest-a") {
                get {
                    call.respondText("Authorized!")
                }
                get("/user") {
                    val principal = call.principal<UserIdPrincipal>()!!
                    call.respondText("Hello ${principal.name} at Digest")
                }
            }
        }
        authenticate(AuthenticationGroup.DigestSampleA.groupName) {
            route("/test-digest-a/sub") {
                get {
                    call.respondText("Authorized!")
                }
                get("/user") {
                    val principal = call.principal<UserIdPrincipal>()!!
                    call.respondText("Sub: Hello ${principal.name} at Digest")
                }
            }
        }
    }
    registerCustomerRouter()
    registerPublicFileRouter()
}
