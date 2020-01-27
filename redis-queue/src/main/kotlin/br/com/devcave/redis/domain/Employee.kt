package br.com.devcave.redis.domain

import java.time.LocalDate

data class Employee(
    val name: String,
    val document: String,
    val collageCompletedYear: Int?,
    val bornAt: LocalDate
)