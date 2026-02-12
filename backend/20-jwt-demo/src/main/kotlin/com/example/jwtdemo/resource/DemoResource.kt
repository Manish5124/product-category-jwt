package com.example.jwtdemo.resource

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoResource {
    @GetMapping("/user/hello")
    fun user(): String = "Hello User"

    @GetMapping("/admin/hello")
    fun admin(): String = "Hello Admin"
}