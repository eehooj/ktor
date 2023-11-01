package com.example.auth.dto

data class LoginResponse(
    val id: Long,
    val name: String,
    val token: String
) {

}