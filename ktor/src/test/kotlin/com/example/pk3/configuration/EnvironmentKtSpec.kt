package com.example.pk3.configuration

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import withTestRootModule
import java.nio.file.Path

class EnvironmentKtSpec : StringSpec({
    "envStorageFile" {
        withTestRootModule {
            application.envStoragePath().shouldBePlayable()

            println("storage folder path: ${application.envStoragePath()}")
        }
    }
    "envPublicFile" {
        withTestRootModule {
            application.envPublicPath().shouldBePlayable()

            println("public folder path: ${application.envStoragePath()}")
        }
    }
})

private fun Path.shouldBePlayable() {
    withClue("Path should be absolute") { this.isAbsolute shouldBe true }
    this.toFile().let { file ->
        withClue("Path should exist") { file.exists() shouldBe true }
        withClue("Path should be readable") { file.canRead() shouldBe true }
        withClue("Path should be writable") { file.canWrite() shouldBe true }
        withClue("Path should be executable") { file.canExecute() shouldBe true }
    }
}
