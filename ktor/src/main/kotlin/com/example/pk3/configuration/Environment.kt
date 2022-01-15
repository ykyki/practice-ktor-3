package com.example.pk3.configuration

import io.ktor.application.*
import java.nio.file.Path
import kotlin.io.path.Path

/*
    confファイルを読み取ってenvを設定する
 */

fun Application.envStoragePath(): Path =
    Path(environment.config.property("storagePath").getString())


fun Application.envPublicPath(): Path =
    Path(envStoragePath().toString(), "public")
