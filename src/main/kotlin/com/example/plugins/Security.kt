package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.config.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    authentication {
        jwt {
            realm = JwtConfig.jwtRealm
            challenge {_, _ ->
                call.request.headers["Authorization"]?.let {

                }
            }
            verifier(
                JWT
                    .require(Algorithm.HMAC256(JwtConfig.jwtSecret))
                    .withAudience(JwtConfig.jwtAudience)
                    .withIssuer(JwtConfig.jwtDomain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(JwtConfig.jwtAudience)) JWTPrincipal(credential.payload)
                else null
            }
        }
    }
}
