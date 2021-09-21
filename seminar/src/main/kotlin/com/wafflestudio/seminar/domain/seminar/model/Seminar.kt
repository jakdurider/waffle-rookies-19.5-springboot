package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@Entity
class Seminar(
    @field:NotBlank(message="Register with name")
    val name: String,

    @field:NotNull(message="Register with capacity")
    @field:Min(1,message = "capacity is lower than 1")
    val capacity: Long,

    @field:NotNull(message="Register with count")
    @field:Min(1,message = "count is lower than 1")
    val count: Long,

    @field:NotBlank(message="Register with time")
    @field:Pattern(regexp="^([0-1][0-9]|2[0-3])([:])([0-5][0-9])$",message="time should have structure like HH:MM in 24 hour system")
    val time: String,

    @field:Pattern(regexp="^([T,t][R,r][U,u][E,e])|([F,f][A,a][L,l][S,s][E,e])$", message = "")
    val online: String = "True",


    @OneToMany(cascade = [CascadeType.ALL],mappedBy="participantProfile")
    val participants : List<SeminarParticipant> = listOf(),

    @OneToMany(cascade = [CascadeType.ALL],mappedBy="seminar")
    val instructors : List<Seminar> = listOf()

) : BaseTimeEntity()