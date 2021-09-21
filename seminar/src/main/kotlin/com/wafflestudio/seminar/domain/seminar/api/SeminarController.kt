package com.wafflestudio.seminar.domain.seminar.api

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.exception.NotInstructorException
import com.wafflestudio.seminar.domain.seminar.service.SeminarService
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.global.auth.CurrentUser
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/seminars")
class SeminarController(
    private val seminarService: SeminarService,
) {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun addSeminar(
        @RequestBody @Valid seminarCreateRequest: SeminarDto.CreateRequest,
        @CurrentUser user: User,
    ): SeminarDto.Response {
        if(user.role != "instructor") throw NotInstructorException("Only Instructor can create seminar")
        return SeminarDto.Response(seminarService.createSeminar(seminarCreateRequest,user))
    }
}