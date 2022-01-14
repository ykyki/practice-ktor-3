package com.example.pk3.configuration

import io.ktor.application.*
import java.io.File

fun Application.envPublicFile(): File =
    File(environment.config.property("env.publicPath").getString())
