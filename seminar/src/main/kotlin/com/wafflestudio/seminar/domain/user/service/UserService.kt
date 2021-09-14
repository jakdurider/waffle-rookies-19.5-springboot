package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import com.wafflestudio.seminar.domain.user.exception.EmailAlreadyExist
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }
    fun getUserById(id: Long) : User? {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
    }
    fun getUserByEmail(email: String) : User? {
        return userRepository.findByEmail(email)
    }
    fun addUser(newUser: User): User? {
        if(getUserByEmail(newUser.email)!=null) throw EmailAlreadyExist()
        return userRepository.save(newUser)
    }
}