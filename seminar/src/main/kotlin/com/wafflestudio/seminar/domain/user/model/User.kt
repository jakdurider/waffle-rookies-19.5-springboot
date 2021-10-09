package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "seminar_user")
class User(
    @Column(unique = true)
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val name: String,

    val date_joined: LocalDateTime = LocalDateTime.now(),

    @field:NotBlank
    val password: String,

    @Column
    @field:NotNull
    val roles: String = "",

    @OneToOne
    @JoinColumn(name = "participant_profile", referencedColumnName = "id", nullable = true)
    var participant_profile: ParticipantProfile? = null,

    @OneToOne
    @JoinColumn(name = "instructor_profile", referencedColumnName = "id", nullable = true)
    var instructor_profile: InstructorProfile? = null,

    ) : BaseEntity()
