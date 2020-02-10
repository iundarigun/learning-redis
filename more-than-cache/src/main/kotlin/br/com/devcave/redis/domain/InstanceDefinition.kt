package br.com.devcave.redis.domain

import java.time.LocalDateTime

data class InstanceDefinition(
    val uuid: String,
    val host: String,
    val upDate: LocalDateTime,
    val application: String
)