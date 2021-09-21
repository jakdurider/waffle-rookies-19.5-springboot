package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class InstructorProfile (
        @field:NotBlank
        var company : String,

        var year : Long?,

        @ManyToOne
        @JoinColumn(name = "seminar_id", referencedColumnName = "id", nullable = true)
        var seminar : Seminar? = null,

        ) : BaseTimeEntity()