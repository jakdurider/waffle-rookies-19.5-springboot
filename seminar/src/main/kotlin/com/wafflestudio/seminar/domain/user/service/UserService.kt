package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.exception.InvalidRoleException
import com.wafflestudio.seminar.domain.user.exception.NoUserMatchException
import com.wafflestudio.seminar.domain.user.exception.UserAlreadyExistsException
import com.wafflestudio.seminar.domain.user.exception.UserAlreadyParticipantException
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.repository.InstructorProfileRepository
import com.wafflestudio.seminar.domain.user.repository.ParticipantProfileRepository
import com.wafflestudio.seminar.global.auth.dto.LoginRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val participantRepository: ParticipantProfileRepository,
    private val instructorRepository: InstructorProfileRepository,
) {
    fun signup(signupRequest: UserDto.SignupRequest): User {
        if (userRepository.existsByEmail(signupRequest.email)) throw UserAlreadyExistsException()
        val encodedPassword = passwordEncoder.encode(signupRequest.password)
        if(signupRequest.role == "participant"){
            val participantProfile = participantRepository.save(ParticipantProfile(
                university = signupRequest.university, accepted = signupRequest.accepted, ))
            val user =  userRepository.save(User(name = signupRequest.name, email = signupRequest.email,
                password = encodedPassword, participant_profile = participantProfile))
            participantProfile.user = user
            return user
        }
        else if(signupRequest.role == "instructor"){
            val instructorProfile = instructorRepository.save(InstructorProfile(
                company = signupRequest.company, year = signupRequest.year,))
            val user =  userRepository.save(User(name = signupRequest.name, email = signupRequest.email,
                password = encodedPassword, instructor_profile = instructorProfile))
            instructorProfile.user = user
            return user
        }
        else throw InvalidRoleException("Role should be participant or instructor")
    }


    fun getUserById(user_id: Long) : User{
        return userRepository.findByIdOrNull(user_id)?: throw NoUserMatchException("There is no matching user")
    }

    fun modify(modifyRequest: UserDto.ModifyRequest, user_id: Long): User{
        val user = userRepository.findByIdOrNull(user_id)?: throw NoUserMatchException("There is no matching user")
        if(user.participant_profile!=null){
            user.participant_profile!!.university = modifyRequest.university
        }
        if(user.instructor_profile!=null){
            user.instructor_profile!!.company = modifyRequest.company
            user.instructor_profile!!.year = modifyRequest.year
        }
        return userRepository.save(user)
    }

    fun signupToParticipant(participantRequest: UserDto.ParticipantRequest, user_id: Long): User{
        val user = userRepository.findByIdOrNull(user_id)?: throw NoUserMatchException("There is no matching user")
        if(user.participant_profile!=null) throw UserAlreadyParticipantException("You are Already Participant")
        val participantProfile = participantRepository.save(ParticipantProfile(
            university = participantRequest.university, accepted = participantRequest.accepted, user = user))
        user.participant_profile = participantProfile
        return userRepository.save(user)
    }
}