package com.wafflestudio.seminar.domain.seminar.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.NotAllowedException

class ProcessingSeminarException(detail: String=""):
        NotAllowedException(ErrorType.NOT_ALLOWED,detail)