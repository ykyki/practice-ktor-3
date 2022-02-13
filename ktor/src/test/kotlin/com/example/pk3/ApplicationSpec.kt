package com.example.pk3

import com.example.pk3.configuration.authentication.AuthenticationGroup
import dispatchDigestAuthHeader
import io.kotest.assertions.ktor.shouldHaveContent
import io.kotest.assertions.ktor.shouldHaveStatus
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.throwable.shouldHaveMessage
import io.ktor.http.*
import io.ktor.server.testing.*
import shouldEqualJsonStrictly
import withTestRootModule

class ApplicationSpec : FunSpec({
    context("/") {
        test("GET") {
            withTestRootModule {
                handleRequest(method = HttpMethod.Get, uri = "/").apply {
                    response shouldHaveStatus HttpStatusCode.OK
                    response shouldHaveContent "Hello pk3!"
                }
            }
        }
    }
    context("/test-json") {
        test("GET") {
            withTestRootModule {
                handleRequest(method = HttpMethod.Get, uri = "/test-json").apply {
                    response shouldHaveStatus HttpStatusCode.OK
                    response.content!! shouldEqualJsonStrictly
                            """
                                {
                                    "hello": "world"
                                }
                            """
                }
            }
        }
    }
    context("/test-sub-sample") {
        test("GET") {
            withTestRootModule {
                handleRequest(method = HttpMethod.Get, uri = "/test-sub-sample").apply {
                    response shouldHaveStatus HttpStatusCode.OK
                    response shouldHaveContent "From sub sample project: sub-sample-012345:foo"
                }
            }
        }
    }
    context("/test-error") {
        test("GETすると例外") {
            withTestRootModule {
                shouldThrow<RuntimeException> {
                    handleRequest(method = HttpMethod.Get, uri = "/test-error").apply {
                        response shouldHaveStatus HttpStatusCode.InternalServerError
                        response shouldHaveContent "Internal Server Error"
                    }
                }.let {
                    it shouldHaveMessage "エラーテストページにアクセスしました"
                }
            }
        }
        test("POSTするとNotFound") {
            withTestRootModule {
                handleRequest(method = HttpMethod.Post, uri = "/test-error").apply {
                    response shouldHaveStatus HttpStatusCode.NotFound
                    response shouldHaveContent "Page not found"
                }
            }
        }
    }
    context("/test-digest-a") {
        test("GETするとUnauthorized") {
            withTestRootModule {
                handleRequest(method = HttpMethod.Get, uri = "/test-digest-a").apply {
                    response shouldHaveStatus HttpStatusCode.Unauthorized
                }
            }
        }
        test("認証ヘッダ付きでGET") {
            withTestRootModule {
                handleRequest(HttpMethod.Get, "/test-digest-a") {
                    addHeader(HttpHeaders.Authorization, dispatchDigestAuthHeader(AuthenticationGroup.DigestSampleA))
                }.apply {
                    response shouldHaveStatus HttpStatusCode.OK
                    response shouldHaveContent "Authorized!"
                }
            }
        }
    }
    context("/test-digest-a/user") {
        test("GETするとUnauthorized") {
            withTestRootModule {
                handleRequest(method = HttpMethod.Get, uri = "/test-digest-a/user").apply {
                    response shouldHaveStatus HttpStatusCode.Unauthorized
                }
            }
        }
        test("認証ヘッダ付きでGET") {
            withTestRootModule {
                handleRequest(HttpMethod.Get, "/test-digest-a/user") {
                    addHeader(HttpHeaders.Authorization, dispatchDigestAuthHeader(AuthenticationGroup.DigestSampleA))
                }.apply {
                    response shouldHaveStatus HttpStatusCode.OK
                    response shouldHaveContent "Hello user-abc at Digest"
                }
            }
        }
    }
    context("割り当てられていないURL") {
        withData(
            nameFn = { "${it.value}するとNotFound" },
            HttpMethod.Get,
            HttpMethod.Post,
            HttpMethod.Put,
            HttpMethod.Delete
        ) { method ->
            withTestRootModule {
                handleRequest(method = method, uri = "/page-0123456789abc").apply {
                    response shouldHaveStatus HttpStatusCode.NotFound
                    response shouldHaveContent "Page not found"
                }
            }
        }
    }
})
