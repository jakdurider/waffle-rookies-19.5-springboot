package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import javax.persistence.*

@Entity
class ParticipantProfile (
        var university: String,
        val accepted: Boolean,

        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "participant", fetch = FetchType.EAGER)
        var seminars : MutableList<SeminarParticipant> = mutableListOf(),

        @OneToOne(mappedBy="participant_profile")
        var user: User? = null,

        ) : BaseTimeEntity()