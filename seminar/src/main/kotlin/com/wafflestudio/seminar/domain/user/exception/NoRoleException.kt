package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.*

class NoRoleException(detail : String = "") :
    InvalidRequestException(ErrorType.INVALID_REQUEST, detail)