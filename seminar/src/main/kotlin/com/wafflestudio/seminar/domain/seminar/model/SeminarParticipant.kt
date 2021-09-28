package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class SeminarParticipant (
        @EmbeddedId
        var id: SeminarParticipantKey,

        @ManyToOne
        @MapsId("participantId")
        @JoinColumn(name="participant_id",referencedColumnName = "id")
        val participant : ParticipantProfile,


        @ManyToOne
        @MapsId("seminarId")
        @JoinColumn(name="seminar_id",referencedColumnName = "id")
        val seminar : Seminar,

        var joined_at : LocalDateTime = LocalDateTime.now(),
        var is_active : Boolean = true,
        var dropped_at : LocalDateTime? = null,

        @CreatedDate
        var created_at: LocalDateTime? = null,

        @LastModifiedDate
        var updated_at: LocalDateTime? = null,
        )