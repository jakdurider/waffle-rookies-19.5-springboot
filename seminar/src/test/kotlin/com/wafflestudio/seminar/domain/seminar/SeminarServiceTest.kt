package com.wafflestudio.seminar.domain.seminar

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.repository.SeminarRepository

import com.wafflestudio.seminar.domain.seminar.service.SeminarService
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.User
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SeminarServiceTest {
        @Autowired
        private lateinit var seminarService: SeminarService

        @Mock
        private lateinit var seminarRepository: SeminarRepository
        @Test
        fun 세미나생성(){
                //given
                val instructor = User(
                        email = "instructor@gmail.com",
                        name = "instructor",
                        password = "inst",
                        roles = "instructor",
                        instructor_profile = InstructorProfile(
                                company = "NAVER",
                                year = 3
                        )
                )
                val createRequest = SeminarDto.CreateRequest(
                        name = "waffle-spring",
                        capacity = 40,
                        count = 5,
                        time = "15:00",
                        online = "true"
                )
                //when
                val newSeminar = seminarService.createSeminar(createRequest,instructor.id)
                //then
                assertEquals("waffle-spring",newSeminar?.name)
                assertEquals(mutableListOf("instructor"),newSeminar?.instructors)
                assertEquals("waffle-spring",instructor.instructor_profile?.charge?.name)
        }
}