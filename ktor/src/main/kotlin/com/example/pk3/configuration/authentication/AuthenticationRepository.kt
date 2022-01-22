package com.example.pk3.configuration.authentication

interface AuthenticationRepository {
    fun find(group: AuthenticationGroup, username: String, realm: String): ByteArray?
}
