package com.wafflestudio.seminar.domain.seminar.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.DataNotFoundException

class NoMatchingException(detail: String=""):
    DataNotFoundException(ErrorType.DATA_NOT_FOUND,detail)