package com.example.pk3.configuration

import com.example.pk3.configuration.authentication.AuthenticationGroup.DigestSampleA
import com.example.pk3.configuration.authentication.AuthenticationRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.util.*
import org.koin.ktor.ext.inject

fun Application.configureAuthentication(nonceManager: NonceManager = GenerateOnlyNonceManager) {
    val authenticationRepository: AuthenticationRepository by inject()

    authentication {
        digest(DigestSampleA.groupName) {
            realm = DigestSampleA.realm
            algorithmName = "MD5"
            this.nonceManager = nonceManager

            digestProvider { userName, realm ->
                authenticationRepository.find(DigestSampleA, userName, realm)
            }
        }
    }
}
