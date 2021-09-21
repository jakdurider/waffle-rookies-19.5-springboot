package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.exception.NoRoleException
import com.wafflestudio.seminar.domain.user.exception.UserAlreadyExistsException
import com.wafflestudio.seminar.domain.user.exception.UserAlreadyParticipantException
import com.wafflestudio.seminar.domain.user.exception.YearException
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.repository.InstructorRepository
import com.wafflestudio.seminar.domain.user.repository.ParticipantRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val participantRepository: ParticipantRepository,
    private val instructorRepository: InstructorRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun signup(signupRequest: UserDto.SignupRequest): User {
        if (userRepository.existsByEmail(signupRequest.email)) throw UserAlreadyExistsException()
        val encodedPassword = passwordEncoder.encode(signupRequest.password)
        if(signupRequest.role == "participant") {
            val participantProfile = participantRepository.save(ParticipantProfile(
                university = signupRequest.university,
                accepted = signupRequest.accepted,
            ))
            return userRepository.save(User(
                email = signupRequest.email,
                name = signupRequest.name,
                password = encodedPassword,
                role = signupRequest.role,
                participant_profile = participantProfile
                ))
        }
        else if(signupRequest.role == "instructor"){
            //if(signupRequest.year!=null && signupRequest.year <= 0) throw YearException("Year <= 0")
            val instructorProfile = instructorRepository.save(InstructorProfile(
                company = signupRequest.company,
                year = signupRequest.year
                ))
            return userRepository.save(User(
                email = signupRequest.email,
                name = signupRequest.name,
                password = encodedPassword,
                role = signupRequest.role,
                instructor_profile = instructorProfile
                ))
        }
        else {
            throw NoRoleException("No Role in Request Body")
        }
    }

    fun modify(modifyRequest: UserDto.ModifyRequest, user: User): User{
        //if(modifyRequest.year!=null && modifyRequest.year <= 0) throw YearException("Year <= 0")
        user.email = modifyRequest.email?: user.email
        user.name = modifyRequest.name?: user.name
        if(user.role == "participant"){
            user.participant_profile?.university = modifyRequest.university
        }
        else if(user.role == "instructor"){
            user.instructor_profile?.company = modifyRequest.company
            user.instructor_profile?.year = modifyRequest.year
        }
        return user
    }

    fun reSignup(signupRequest: UserDto.SignupRequest, user: User): User{
        if(user.participant_profile!=null) throw UserAlreadyParticipantException("Already Participant")
        val participantProfile = participantRepository.save(ParticipantProfile(
            university = signupRequest.university,
            accepted = signupRequest.accepted
        ))
        user.participant_profile = participantProfile
        return user
    }
}