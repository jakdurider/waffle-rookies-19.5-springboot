package com.wafflestudio.seminar.domain.survey.api

import com.wafflestudio.seminar.domain.survey.dto.SurveyResponseDto
import com.wafflestudio.seminar.domain.os.exception.OsNotFoundException
import com.wafflestudio.seminar.domain.os.service.OperatingSystemService
import com.wafflestudio.seminar.domain.survey.exception.SurveyNotFoundException
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.survey.service.SurveyResponseService
import org.modelmapper.ModelMapper

import org.modelmapper.convention.MatchingStrategies
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/results")
class SurveyResponseController(
    private val surveyResponseService: SurveyResponseService,
    private val modelMapper: ModelMapper
) {
    @GetMapping("/")
    fun getSurveyResponses(@RequestParam(required = false) os: String?): ResponseEntity<List<SurveyResponseDto.Response>> {
        return try {
            val surveyResponses =
                if (os != null) surveyResponseService.getSurveyResponsesByOsName(os)
                else surveyResponseService.getAllSurveyResponses()
            val responseBody = surveyResponses.map { modelMapper.map(it, SurveyResponseDto.Response::class.java) }
            ResponseEntity.ok(responseBody)
        } catch (e: OsNotFoundException) {
            ResponseEntity.notFound().build()
        }
        // AOP를 적용해 exception handling을 따로 하도록 고쳐보셔도 됩니다.
    }

    @GetMapping("/{id}/")
    fun getSurveyResponse(@PathVariable("id") id: Long): ResponseEntity<SurveyResponseDto.Response> {
        return try {
            val surveyResponse = surveyResponseService.getSurveyResponseById(id)
            val responseBody = modelMapper.map(surveyResponse, SurveyResponseDto.Response::class.java)
            ResponseEntity.ok(responseBody)
        } catch (e: SurveyNotFoundException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/", consumes = arrayOf(MediaType.MULTIPART_FORM_DATA_VALUE))
    fun addSurveyResponse(
        @ModelAttribute @Valid body: SurveyResponseDto.CreateRequest,
        @RequestHeader("User-Id") userId: Long
    ): ResponseEntity<SurveyResponseDto.Response> {
        //TODO: API 생성
        return try {
            //modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            var newSurveyResponse = modelMapper.map(body,SurveyResponse::class.java)
            //modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
            val newSurveyResponseResult = surveyResponseService.addSurveyResponse(newSurveyResponse,userId,body.osName)
            val responseBody = modelMapper.map(newSurveyResponseResult, SurveyResponseDto.Response::class.java)
            ResponseEntity.ok(responseBody)
        } catch (e: OsNotFoundException){
            ResponseEntity.notFound().build()
        }
    }

    @PatchMapping("/{id}/")
    fun modifySurveyResponseWithId(@ModelAttribute @Valid body: SurveyResponseDto.ModifyRequest): SurveyResponseDto.Response {
        //TODO: API 생성
        return SurveyResponseDto.Response()
    }
}
