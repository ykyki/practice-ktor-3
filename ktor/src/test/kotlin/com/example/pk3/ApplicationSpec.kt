package com.example.pk3

import io.kotest.assertions.ktor.shouldHaveStatus
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.ktor.http.*
import io.ktor.server.testing.*
import withRootModule

class ApplicationSpec : StringSpec() {
    init {
        "GET: /" {
            withRootModule {
                handleRequest(method = HttpMethod.Get, uri = "/").apply {
                    response shouldHaveStatus HttpStatusCode.OK
                    response.content shouldBe "Hello pk3!"
                }
            }
        }
        "GET: /test-json" {
            withRootModule {
                handleRequest(method = HttpMethod.Get, uri = "/test-json").apply {
                    response shouldHaveStatus HttpStatusCode.OK
                    response.content shouldNotBe null
                }
            }
        }
        "GET: /test-error" {
            withRootModule {
                val exception = shouldThrow<RuntimeException> {
                    handleRequest(method = HttpMethod.Get, uri = "/test-error").apply {
                        response shouldHaveStatus HttpStatusCode.InternalServerError
                        response.content shouldBe "Internal Server Error"
                    }
                }

                exception.message shouldBe "エラーテストページにアクセスしました"
            }
        }
        "GET: page not exist" {
            withRootModule {
                handleRequest(method = HttpMethod.Get, uri = "/page-0123456789abc").apply {
                    response shouldHaveStatus HttpStatusCode.NotFound
                    response.content shouldBe "Page not found"
                }
            }
        }
    }
}
