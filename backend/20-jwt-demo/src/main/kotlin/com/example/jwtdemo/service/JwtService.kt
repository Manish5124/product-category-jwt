package com.example.jwtdemo.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtService(
    @Value("\${jwt.secret}") secret: String,
    @Value("\${jwt.expiration}") val expirationMs: Long
) {

    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(username: String, role: String) =
        Jwts.builder()
            .subject(username)
            .claim("role", role)
            .expiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(key)
            .compact()

    fun extractUsername(token: String) =
        Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .payload.subject
}