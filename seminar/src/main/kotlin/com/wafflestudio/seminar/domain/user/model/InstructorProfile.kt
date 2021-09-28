package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import javax.persistence.*

@Entity
class InstructorProfile (
        var company: String,
        var year: Int?,

        @ManyToOne
        @JoinColumn(name = "seminar_id", referencedColumnName = "id", nullable = true)
        var charge: Seminar? = null,

        @OneToOne(mappedBy="instructor_profile")
        var user: User? = null,

        ) : BaseTimeEntity()