package com.example.jwtdemo.resource

import com.example.jwtdemo.dto.AuthRequest
import com.example.jwtdemo.dto.AuthResponse
import com.example.jwtdemo.dto.RegisterResponse
import com.example.jwtdemo.model.Role
import com.example.jwtdemo.model.User
import com.example.jwtdemo.persistence.UserPersistence
import com.example.jwtdemo.service.JwtService
import jakarta.annotation.PostConstruct
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.resource.HttpResource

@RestController
@RequestMapping("/auth")
class AuthResource(
    private val userPersistence: UserPersistence,
    private val jwtService: JwtService,
    private val encoder: PasswordEncoder
) {

    @PostConstruct
    fun init() {
        if (userPersistence.count() == 0L) {
            userPersistence.save(
                User(
                    username = "user",
                    password = encoder.encode("user123"),
                    role = Role.USER
                )
            )
            userPersistence.save(
                User(
                    username = "admin",
                    password = encoder.encode("admin123"),
                    role = Role.ADMIN
                )
            )
        }
    }

        @PostMapping("/login")
        fun login(@RequestBody request: AuthRequest): AuthResponse{
            val user = userPersistence.findByUsername(request.username)
                ?: throw RuntimeException("Invalid credentials")

            if(!encoder.matches(request.password, user.password)){
                throw RuntimeException("Invalid credentials")
            }

            return AuthResponse(
                jwtService.generateToken(user.username, user.role.name)
            )
        }

        @PostMapping("/register")
        fun register(@RequestBody request: AuthRequest): ResponseEntity<RegisterResponse>{
            if (userPersistence.existsByUsername(request.username)) {
                throw RuntimeException("Username already exists")
            }
            userPersistence.save(User(
                username = request.username,
                password = encoder.encode(request.password),
                role = if (request.role == Role.ADMIN.name) Role.ADMIN else Role.USER
            ))
            return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(RegisterResponse("User registered"))
        }
    }