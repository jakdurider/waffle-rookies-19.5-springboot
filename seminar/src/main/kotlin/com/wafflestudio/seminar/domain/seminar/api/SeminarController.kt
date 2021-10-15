package com.wafflestudio.seminar.domain.seminar.api

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.service.SeminarService
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.global.auth.CurrentUser
import com.wafflestudio.seminar.global.common.dto.ListResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/seminars")
class SeminarController(
    private val seminarService: SeminarService
) {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun addSeminar(@Valid @RequestBody createRequest: SeminarDto.CreateRequest, @CurrentUser user: User): SeminarDto.Response{
        return SeminarDto.Response(seminarService.createSeminar(createRequest, user.id))
    }

    @PutMapping("/{seminar_id}/")
    fun modifySeminar(@Valid @RequestBody modifyRequest: SeminarDto.ModifyRequest,
                      @CurrentUser user: User, @PathVariable("seminar_id") seminar_id: Long): SeminarDto.Response{
        return SeminarDto.Response(seminarService.modifySeminar(modifyRequest, user.id, seminar_id))
    }

    @GetMapping("/{seminar_id}/")
    fun getSeminar(@PathVariable("seminar_id") seminar_id: Long): SeminarDto.Response{
        return SeminarDto.Response(seminarService.getSeminarById(seminar_id))
    }

    @GetMapping("/")
    fun getSeminars(@RequestParam(required = false, name = "name") name: String?,
                    @RequestParam(required = false, name = "order") order: String?) : ListResponse<SeminarDto.ManyResponse> {
        var seminars =
            if(name != null) seminarService.getSeminarsByName(name,order)
            else seminarService.getAllSeminars(order)
        return ListResponse(seminars.map{SeminarDto.ManyResponse(it,seminarService.activeParticipants(it.seminarParticipants))})
    }

    @PostMapping("/{seminar_id}/user/")
    @ResponseStatus(HttpStatus.CREATED)
    fun connectUserToSeminar(@Valid @RequestBody connectingRequest: SeminarDto.ConnectingRequest,
                             @CurrentUser user: User, @PathVariable("seminar_id") seminar_id: Long) : SeminarDto.Response {
        return SeminarDto.Response(seminarService.connectUserToSeminar(connectingRequest, user.id, seminar_id))
    }

    @DeleteMapping("/{seminar_id}/user/me/")
    fun dropFromSeminar(@CurrentUser user: User, @PathVariable("seminar_id") seminar_id: Long) : SeminarDto.Response{
        return SeminarDto.Response(seminarService.dropFromSeminar(user.id, seminar_id))
    }

}