package com.wafflestudio.seminar.domain.ping.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class PingController {
    @GetMapping("/")
    fun ping(): ResponseEntity<String>{
        return ResponseEntity.ok("pong");
    }
}