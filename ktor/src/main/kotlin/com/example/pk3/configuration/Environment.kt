package com.example.pk3.configuration

import io.ktor.application.*
import java.io.File

/*
    confファイルを読み取ってenvを設定する
 */

fun Application.envPublicFile(): File =
    File(environment.config.property("publicPath").getString())
