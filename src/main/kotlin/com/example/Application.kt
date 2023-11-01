package com.example

import com.example.plugins.*
import com.example.user.User
import com.example.user.Users
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureDatabases()
    configureRouting()

    transaction {
        SchemaUtils.create(Users)
        User.new {
            name = "zuri"
            password = "string"
        }
    }
}
