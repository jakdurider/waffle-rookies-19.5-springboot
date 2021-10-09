package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.service.UserService
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.global.auth.CurrentUser
import com.wafflestudio.seminar.global.auth.JwtTokenProvider
import com.wafflestudio.seminar.global.auth.dto.LoginRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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
        return ResponseEntity.ok().header("Authentication", jwtTokenProvider.generateToken(user.email)).body(UserDto.Response(user))
    }

    @PostMapping("/login/")
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<UserDto.Response> {
        val user = userService.login(loginRequest)
        return ResponseEntity.ok().header("Authentication", jwtTokenProvider.generateToken(user.email)).body(UserDto.Response(user))
    }

    @GetMapping("/me/")
    fun getCurrentUser(@CurrentUser user: User): UserDto.Response {
        return UserDto.Response(user)
    }

    // when giving user object to user service and repository,
    // repository.save() does not work for update so I give user_id
    // to get entity from repository
    @GetMapping("/{user_id}/")
    fun getUserById(@PathVariable("user_id") user_id: Long): UserDto.Response {
        return UserDto.Response(userService.getUserById(user_id))
    }

    @PutMapping("/me/")
    fun modifyCurrentUser(@Valid @RequestBody modifyRequest: UserDto.ModifyRequest, @CurrentUser user: User): UserDto.Response{
        return UserDto.Response(userService.modify(modifyRequest,user.id))
    }

    // api for instructor to resignup to participant
    @PostMapping("/participant/")
    @ResponseStatus(HttpStatus.CREATED)
    fun signupToParticipant(@Valid @RequestBody participantRequest: UserDto.ParticipantRequest, @CurrentUser user: User): UserDto.Response{
        return UserDto.Response(userService.signupToParticipant(participantRequest, user.id))
    }
}
