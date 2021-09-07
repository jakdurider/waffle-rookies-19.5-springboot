package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.os.exception.OsNotFoundException
import com.wafflestudio.seminar.domain.survey.dto.SurveyResponseDto
import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.service.UserService
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.exception.EmailAlreadyExist
import org.modelmapper.ModelMapper
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
    private val modelMapper: ModelMapper
) {
    @GetMapping("/")
    fun getUsers(@RequestParam(required = false) user: Int?): ResponseEntity<List<UserDto.Response>> {
        return try {
            val userResponses = userService.getAllUsers()
            val responseBody = userResponses.map { modelMapper.map(it, UserDto.Response::class.java) }
            ResponseEntity.ok(responseBody)
        } catch (e: UserNotFoundException) {
            ResponseEntity.notFound().build()
        }
        // AOP를 적용해 exception handling을 따로 하도록 고쳐보셔도 됩니다.
    }

    @GetMapping("/me/")
    fun getUserById(@RequestHeader("User-Id") user_Id: Long): ResponseEntity<UserDto.Response> {
        return try {
            val userResponse = userService.getUserById(user_Id)
            val responseBody = modelMapper.map(userResponse, UserDto.Response::class.java)
            ResponseEntity.ok(responseBody)
        } catch (e: UserNotFoundException) {
            ResponseEntity.notFound().build()
        }
        // AOP를 적용해 exception handling을 따로 하도록 고쳐보셔도 됩니다.
    }

    @PostMapping("/", consumes = arrayOf(MediaType.MULTIPART_FORM_DATA_VALUE))
    fun addUser(
        @ModelAttribute @Valid body: UserDto.CreateRequest,
    ): Unit {
        try {
            val newUser = modelMapper.map(body, User::class.java)
            userService.addUser(newUser)
        }
        catch (e : EmailAlreadyExist){

        }
    }


}