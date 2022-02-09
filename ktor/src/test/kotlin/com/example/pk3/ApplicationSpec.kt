package com.example.pk3

import com.example.pk3.configuration.authentication.AuthenticationGroup
import dispatchDigestAuthHeader
import io.kotest.assertions.ktor.shouldHaveContent
import io.kotest.assertions.ktor.shouldHaveStatus
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.throwable.shouldHaveMessage
import io.ktor.http.*
import io.ktor.server.testing.*
import shouldEqualJsonStrictly
import withTestRootModule

class ApplicationSpec : StringSpec({
    "GET: /" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/").apply {
                response shouldHaveStatus HttpStatusCode.OK
                response shouldHaveContent "Hello pk3!"
            }
        }
    }
    "GET: /test-json" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/test-json").apply {
                response shouldHaveStatus HttpStatusCode.OK
                response.content!! shouldEqualJsonStrictly """
                    {
                        "hello": "world"
                    }
                """
            }
        }
    }
    "GET: /test-sub-sample" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/test-sub-sample").apply {
                response shouldHaveStatus HttpStatusCode.OK
                response shouldHaveContent "From sub sample project: sub-sample-012345:foo"
            }
        }
    }
    "GET: /test-error" {
        withTestRootModule {
            val exception = shouldThrow<RuntimeException> {
                handleRequest(method = HttpMethod.Get, uri = "/test-error").apply {
                    response shouldHaveStatus HttpStatusCode.InternalServerError
                    response shouldHaveContent "Internal Server Error"
                }
            }

            exception shouldHaveMessage "エラーテストページにアクセスしました"
        }
    }
    "GET: /test-digest-a" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/test-digest-a").apply {
                response shouldHaveStatus HttpStatusCode.Unauthorized
            }
        }
    }
    "GET: /test-digest-a with user-password" {
        withTestRootModule {
            handleRequest(HttpMethod.Get, "/test-digest-a") {
                addHeader(HttpHeaders.Authorization, dispatchDigestAuthHeader(AuthenticationGroup.DigestSampleA))
            }.apply {
                response shouldHaveStatus HttpStatusCode.OK
                response shouldHaveContent "Authorized!"
            }
        }
    }
    "GET: /test-digest-a/user" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/test-digest-a/user").apply {
                response shouldHaveStatus HttpStatusCode.Unauthorized
            }
        }
    }
    "GET: /test-digest-a/user with user-password" {
        withTestRootModule {
            handleRequest(HttpMethod.Get, "/test-digest-a/user") {
                addHeader(HttpHeaders.Authorization, dispatchDigestAuthHeader(AuthenticationGroup.DigestSampleA))
            }.apply {
                response shouldHaveStatus HttpStatusCode.OK
                response shouldHaveContent "Hello user-abc at Digest"
            }
        }
    }
    "GET: page not exist" {
        withTestRootModule {
            handleRequest(method = HttpMethod.Get, uri = "/page-0123456789abc").apply {
                response shouldHaveStatus HttpStatusCode.NotFound
                response shouldHaveContent "Page not found"
            }
        }
    }
})
