package com.example.user

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable

object Users : LongIdTable() {
    val name = varchar("name", 50)
    val password = varchar("password", 100)
}

class User(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<User>(Users)

    var name by Users.name
    var password by Users.password
}