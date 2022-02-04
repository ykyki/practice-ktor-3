package com.example.pk3.configuration

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.paths.*
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
    assertSoftly(this) {
        shouldBeAbsolute()
        shouldExist()
        shouldBeReadable()
        shouldBeWriteable()
        shouldBeExecutable()
    }
    // withClue("Path should be absolute") { this.isAbsolute.shouldBeTrue() }
    // this.toFile().let { file ->
    //     withClue("Path should exist") { file.exists().shouldBeTrue() }
    //     withClue("Path should be readable") { file.canRead().shouldBeTrue() }
    //     withClue("Path should be writable") { file.canWrite().shouldBeTrue() }
    //     withClue("Path should be executable") { file.canExecute().shouldBeTrue() }
    // }
}
