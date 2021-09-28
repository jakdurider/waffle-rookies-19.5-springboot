package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.user.model.InstructorProfile

class InstructorProfileDto {
    data class Response(
        val id: Long,
        val company: String,
        val year: Int?,
        val charge: ChargeResponse?,
    ){
        constructor(instructorProfile: InstructorProfile) : this(
            id = instructorProfile.id,
            company = instructorProfile.company,
            year = instructorProfile.year,
            charge = instructorProfile.charge?.let{ChargeResponse(it)}
        )
    }
    data class ChargeResponse(
        val id: Long,
        val name: String,
    ){
        constructor(seminar: Seminar): this(
            id = seminar.id,
            name = seminar.name,
        )
    }
}