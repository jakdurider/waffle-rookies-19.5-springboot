package com.wafflestudio.seminar.global.auth.dto

import javax.validation.constraints.NotBlank

class LoginRequest (
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String,
)