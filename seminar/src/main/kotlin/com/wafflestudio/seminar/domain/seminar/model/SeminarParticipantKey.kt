package com.wafflestudio.seminar.domain.seminar.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class SeminarParticipantKey (
        @Column(name = "participant_id")
        var participantId: Long = 0,
        @Column(name = "seminar_id")
        var seminarId: Long = 0,
        ) : Serializable