package com.wafflestudio.seminar.domain.survey.service

import com.wafflestudio.seminar.domain.os.repository.OperatingSystemRepository
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.os.exception.OsNotFoundException
import com.wafflestudio.seminar.domain.survey.exception.SurveyNotFoundException
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.survey.repository.SurveyResponseRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SurveyResponseService(
    private val surveyResponseRepository: SurveyResponseRepository,
    private val operatingSystemRepository: OperatingSystemRepository,
    private val userRepository: UserRepository,
) {
    fun getAllSurveyResponses(): List<SurveyResponse> {
        return surveyResponseRepository.findAll()
    }

    fun getSurveyResponsesByOsName(name: String): List<SurveyResponse> {
        val os = operatingSystemRepository.findByNameEquals(name) ?: throw OsNotFoundException()
        return surveyResponseRepository.findAllByOs(os)
    }

    fun getSurveyResponseById(id: Long): SurveyResponse? {
        return surveyResponseRepository.findByIdOrNull(id) ?: throw SurveyNotFoundException()
    }

    fun addSurveyResponse(newSurveyResponse : SurveyResponse, userId : Long, osName: String?): SurveyResponse?{
        var surveyResponse = newSurveyResponse
        surveyResponse.os = operatingSystemRepository.findByNameEquals(osName) ?: throw OsNotFoundException()
        surveyResponse.user = userRepository.findByIdOrNull(userId)

        return surveyResponseRepository.save(surveyResponse)
    }
}
