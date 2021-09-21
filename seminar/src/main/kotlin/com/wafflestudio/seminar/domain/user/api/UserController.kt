package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.service.UserService
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.global.auth.CurrentUser
import com.wafflestudio.seminar.global.auth.JwtTokenProvider
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/")
    fun signup(@Valid @RequestBody signupRequest: UserDto.SignupRequest): ResponseEntity<UserDto.Response> {
        val user = userService.signup(signupRequest)
        return ResponseEntity.noContent().header("Authentication", jwtTokenProvider.generateToken(user.email)).build()
    }

    @GetMapping("/me/")
    fun getCurrentUser(@CurrentUser user: User): UserDto.Response {
        return UserDto.Response(user)
    }

    @GetMapping("/{user_id}/")
    fun getUserById(@CurrentUser user: User): UserDto.Response {
        return UserDto.Response(user)
    }

    @PutMapping("/me/")
    fun modifyCurrentUser(@Valid @RequestBody modifyRequest: UserDto.ModifyRequest, @CurrentUser user : User) : UserDto.Response {
        val modifiedUser = userService.modify(modifyRequest,user)
        return UserDto.Response(modifiedUser)
    }

    @PostMapping("/participant/")
    @ResponseStatus(HttpStatus.CREATED)
    fun reSignupToParticipant(@Valid @RequestBody signupRequest: UserDto.SignupRequest, @CurrentUser user: User): UserDto.Response{
        return UserDto.Response(userService.reSignup(signupRequest,user))
    }
}
