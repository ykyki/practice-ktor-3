package com.example.pk3.configuration

import com.example.pk3.configuration.authentication.AuthenticationGroup.DigestSampleA
import com.example.pk3.configuration.authentication.AuthenticationRepository
import io.ktor.application.*
import io.ktor.auth.*
import org.koin.ktor.ext.inject

fun Application.configureAuthentication() {
    val authenticationRepository: AuthenticationRepository by inject()

    authentication {
        digest(DigestSampleA.groupName) {
            digestProvider { userName, realm ->
                authenticationRepository.find(DigestSampleA, userName, realm)
            }
        }
    }
}
