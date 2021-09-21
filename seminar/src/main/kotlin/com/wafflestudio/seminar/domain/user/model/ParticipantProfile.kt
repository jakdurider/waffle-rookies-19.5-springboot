package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class ParticipantProfile (
        @field:NotBlank
        var university : String,

        @field:NotNull
        val accepted : Boolean,

        @OneToMany(cascade = [CascadeType.ALL],mappedBy="participantProfile")
        var seminars : List<SeminarParticipant> = listOf()
        ) : BaseTimeEntity()