package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Seminar(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @CreatedDate
    var created_at: LocalDateTime? = null,

    @LastModifiedDate
    var updated_at: LocalDateTime? = null,

    var name: String,
    var capacity: Int,
    var count: Int,
    var time: String,
    var online: String,

    @OneToMany(mappedBy = "charge")
    var instructors : MutableList<InstructorProfile> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL],mappedBy = "seminar",fetch = FetchType.EAGER)
    var seminarParticipants : MutableList<SeminarParticipant> = mutableListOf(),

    )
