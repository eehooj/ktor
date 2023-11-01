package com.example.auth

import com.example.auth.dto.LoginRequest
import com.example.auth.dto.LoginResponse
import com.example.config.JwtConfig
import com.example.user.User
import com.example.user.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.authRouting() {

    post("/login") {
        val loginRequest = call.receive<LoginRequest>()

        // entity 조회
        val findUser = transaction {
            User.find(Users.name.eq(loginRequest.name)).firstOrNull() ?: throw IllegalArgumentException("회원 없음")
        }

        // create jwt
        val jwtConfig = JwtConfig()
        val jwt = jwtConfig.create(findUser.id.value)

        val message = LoginResponse(findUser.id.value, findUser.name, jwt)
        call.respond(HttpStatusCode.OK, message)
    }

    authenticate {
        route("/info") {
            get {

                call.respond("authentication...!!")
            }
        }
    }
}