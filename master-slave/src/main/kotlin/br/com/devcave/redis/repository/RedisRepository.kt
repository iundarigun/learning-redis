package br.com.devcave.redis.repository

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisRepository(
    private val redisMasterTemplate: StringRedisTemplate,
    private val redisSlaveTemplate: StringRedisTemplate
) {
    private val suffixKey = "devcave:masterslave:"

    fun setValue(key: String, value: String) {
        redisMasterTemplate.opsForValue().set(suffixKey + key, value)
    }

    fun getValue(key: String): String? {
        return redisSlaveTemplate.opsForValue().get(suffixKey + key)
    }

    fun deleteValue(key: String) {
        redisMasterTemplate.delete(suffixKey + key)
    }
}