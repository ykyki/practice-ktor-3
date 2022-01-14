package com.example.pk3.configuration

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldNotBe
import withTestRootModule

class ApplicationEnvironmentKtSpec : StringSpec({
    "envPublicFile" {
        withTestRootModule {
            application.envPublicFile() shouldNotBe null
        }
    }
})
