package com.example.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.jetbrains.exposed.dao.id.EntityID
import java.util.Date

class JwtConfig {

    companion object {
        private val config = HoconApplicationConfig(ConfigFactory.load())

        val jwtAudience = config.property("ktor.security.jwt.audience").getString()
        val jwtDomain = config.property("ktor.security.jwt.issuer").getString()
        val jwtRealm = config.property("ktor.security.jwt.realm").getString()
        val jwtSecret = config.property("ktor.security.jwt.secret").getString()
        val jwtAlgorithm: Algorithm = Algorithm.HMAC512(jwtSecret)
        val jwtExpiration = config.property("ktor.security.jwt.expiration").getString().toLong()
    }

    fun create(userId: Long): String =
        JWT
            .create()
            .withAudience(jwtAudience)
            .withIssuer(jwtDomain)
            .withClaim("userId", userId)
            .withExpiresAt(getExpirationTime())
            .sign(jwtAlgorithm)

    private fun getExpirationTime() =
        Date(System.currentTimeMillis() + jwtExpiration)
}