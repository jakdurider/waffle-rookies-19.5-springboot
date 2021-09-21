package com.wafflestudio.seminar.domain.seminar.service

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.seminar.repository.SeminarParticipantRepository
import com.wafflestudio.seminar.domain.seminar.repository.SeminarRepository
import com.wafflestudio.seminar.domain.user.model.User
import org.springframework.stereotype.Service

@Service
class SeminarService(
    private val seminarRepository: SeminarRepository,
    private val seminarParticipantRepository: SeminarParticipantRepository,
){
    fun createSeminar(createRequest: SeminarDto.CreateRequest, user: User): Seminar {
        val newSeminar =  seminarRepository.save(Seminar(
            name = createRequest.name,
            capacity = createRequest.capacity,
            count = createRequest.count,
            time = createRequest.time,
            online = createRequest.online,
        ))
        user.instructor_profile?.seminar = newSeminar
        return newSeminar
    }
}