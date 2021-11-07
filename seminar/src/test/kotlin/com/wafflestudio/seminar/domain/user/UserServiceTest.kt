package com.wafflestudio.seminar.domain.user

import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.service.UserService
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest {
    @Autowired
    lateinit var userService: UserService

    @Test
    fun 유저이름변경(){
        //given
        val user = User(
            email = "chul@naver.com",
            name = "chul",
            password = "chul",
            roles = "participant",
            participant_profile = ParticipantProfile(
                university = "SNU",
                accepted = true
            )
        )
        val modifyRequest = UserDto.ModifyRequest(
            university = "SNU Graduate"
        )
        //when
        val modifiedUser = userService.modify(modifyRequest,user.id)
        //then
        assertEquals("SNU Graduate",modifiedUser.participant_profile?.university)
    }
}