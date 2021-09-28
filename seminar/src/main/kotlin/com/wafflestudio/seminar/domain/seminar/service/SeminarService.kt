package com.wafflestudio.seminar.domain.seminar.service

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.exception.*
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipantKey
import com.wafflestudio.seminar.domain.seminar.repository.SeminarParticipantRepository
import com.wafflestudio.seminar.domain.seminar.repository.SeminarRepository
import com.wafflestudio.seminar.domain.user.exception.InvalidRoleException
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class SeminarService(
    private val seminarRepository: SeminarRepository,
    private val seminarParticipantRepository: SeminarParticipantRepository,
    private val userRepository: UserRepository,
) {
    fun createSeminar(createRequest: SeminarDto.CreateRequest, user_id: Long): Seminar {
        val user = userRepository.findByIdOrNull(user_id)?: throw NoMatchingException("There is no matching user")
        if(user.instructor_profile==null) throw ProcessingSeminarException("Participant cannot create seminar")
        val seminar = Seminar(
            name = createRequest.name, capacity = createRequest.capacity, count = createRequest.count,
            time = createRequest.time, online = createRequest.online,instructors = mutableListOf(user.instructor_profile!!))
        val newSeminar = seminarRepository.save(seminar)
        user.instructor_profile!!.charge = newSeminar
        userRepository.save(user)
        return newSeminar
    }

    fun modifySeminar(modifyRequest: SeminarDto.ModifyRequest, user_id: Long, seminar_id: Long): Seminar{
        val user = userRepository.findByIdOrNull(user_id)?: throw NoMatchingException("There is no matching user")
        if(user.instructor_profile == null) throw ProcessingSeminarException("Participant cannot modify seminar")
        val seminar = seminarRepository.findByIdOrNull(seminar_id)?: throw NoMatchingException("No matching seminar")
        if(user.instructor_profile?.charge?.id != seminar_id) throw ProcessingSeminarException("Do not have charge in this seminar")
        if(modifyRequest.capacity != null && activeParticipants(seminar.seminarParticipants) > modifyRequest.capacity)
            throw CapacityException("Capacity must be higher than number of active participant")
        seminar.name = modifyRequest.name?: seminar.name
        seminar.capacity = modifyRequest.capacity?: seminar.capacity
        seminar.count = modifyRequest.count?: seminar.count
        seminar.time = modifyRequest.time?: seminar.time
        seminar.online = modifyRequest.online?: seminar.online
        return seminarRepository.save(seminar)
    }

    // function to get number of active participants in seminar
    fun activeParticipants(participants: List<SeminarParticipant>) : Int{
        var total: Int = 0
        for(participant in participants) if(participant.is_active) ++total
        return total
    }

    fun getSeminarById(seminar_id: Long): Seminar{
        return seminarRepository.findByIdOrNull(seminar_id)?: throw NoMatchingException("No matching seminar")
    }

    //ordering by "created_at" is same with ordering by id
    fun getSeminarsByName(name: String?, order: String?) : List<Seminar> {
        return if(order != null && order == "earliest") seminarRepository.findAllByName(name)
        else (seminarRepository.findAllByName(name)).sortedByDescending { it.created_at }
    }

    fun getAllSeminars(order: String?): List<Seminar> {
        return if(order != null && order == "earliest") seminarRepository.findAll()
        else (seminarRepository.findAll()).reversed()
    }

    fun connectUserToSeminar(connectingRequest: SeminarDto.ConnectingRequest, user_id: Long, seminar_id: Long): Seminar{
        val user = userRepository.findByIdOrNull(user_id)?: throw NoMatchingException("There is no matching user")
        val seminar = seminarRepository.findByIdOrNull(seminar_id)?: throw NoMatchingException("No matching seminar")
        if(connectingRequest.role == "participant"){
            if(user.participant_profile == null) throw ProcessingSeminarException("This user does not have participant_profile")
            if(!user.participant_profile!!.accepted) throw ProcessingSeminarException("This user is not accepted")
            if(seminar.capacity <= activeParticipants(seminar.seminarParticipants)) throw CapacityException("This seminar is full")
            if(seminarParticipantRepository.findByParticipantIdAndSeminarId(user.participant_profile!!.id,seminar_id)!=null)
                throw UserAlreadyConnectedException("This participant is already joined in this seminar")
            val seminarParticipant = seminarParticipantRepository.save(SeminarParticipant(
                id = SeminarParticipantKey(participantId = user.participant_profile!!.id, seminarId = seminar.id),
                participant = user.participant_profile!!, seminar = seminar,
            ))
            user.participant_profile!!.seminars.add(seminarParticipant)
            seminar.seminarParticipants.add(seminarParticipant)
            userRepository.save(user)
            return seminarRepository.save(seminar)
        }
        else if(connectingRequest.role == "instructor"){
            if(user.instructor_profile == null) throw ProcessingSeminarException("This user does not have instructor_profile")
            if(user.instructor_profile!!.charge != null) throw UserAlreadyConnectedException("This instructor is already charged")
            user.instructor_profile!!.charge = seminar
            seminar.instructors.add(user.instructor_profile!!)
            userRepository.save(user)
            return seminarRepository.save(seminar)
        }
        else throw InvalidRoleException("Role should be participant or instructor")
    }

    fun dropFromSeminar(user_id: Long, seminar_id: Long): Seminar {
        val user = userRepository.findByIdOrNull(user_id)?: throw NoMatchingException("There is no matching user")
        val seminar = seminarRepository.findByIdOrNull(seminar_id)?: throw NoMatchingException("No matching seminar")
        if(user.instructor_profile!=null && user.instructor_profile?.charge == seminar) throw ProcessingSeminarException("Instructor cannot drop from seminar")
        if(user.participant_profile!=null) {
            val seminarParticipant = seminarParticipantRepository.findByParticipantIdAndSeminarId(
                user.participant_profile!!.id, seminar_id)
            if(seminarParticipant != null){
                seminarParticipant.is_active = false
                seminarParticipant.dropped_at = LocalDateTime.now()
                seminarParticipantRepository.save(seminarParticipant)
            }
        }
        return seminar
    }
}