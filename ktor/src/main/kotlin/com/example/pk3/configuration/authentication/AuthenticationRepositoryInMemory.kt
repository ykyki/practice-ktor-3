package com.example.pk3.configuration.authentication

import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

class AuthenticationRepositoryInMemory : AuthenticationRepository {
    private fun getMd5Digest(str: String): ByteArray =
        MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))

    private val digestMap: Map<String, String> = mapOf(
        "user-abc" to "ktor3-pass",
        "user-def" to "ktor3-1234"
    )

    override fun find(
        group: AuthenticationGroup,
        username: String,
        realm: String
    ): ByteArray? {
        if (group !== AuthenticationGroup.DigestSampleA) {
            return null
        }

        return digestMap[username]?.let { password ->
            getMd5Digest("$username:$realm:$password")
        }
    }
}
