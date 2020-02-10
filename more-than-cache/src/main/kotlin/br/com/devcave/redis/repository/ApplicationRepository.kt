package br.com.devcave.redis.repository

import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.scripting.support.ResourceScriptSource
import org.springframework.stereotype.Repository

@Repository
class ApplicationRepository(private val redisTemplate: StringRedisTemplate) {

    private val applicationPrefixKey = "morethancache:application"
    private val instancePrefixKey = "morethancache:instance"

    fun addInstanceToApplication(
        applicationId: String,
        instanceId: String
    ) {
        val key = "$applicationPrefixKey:$applicationId"
        redisTemplate.boundSetOps(key).add(instanceId)
    }

    fun removeInstanceFromApplication(
        applicationId: String,
        instanceId: String
    ) {
        val key = "$applicationPrefixKey:$applicationId"
        redisTemplate.boundSetOps(key).remove(instanceId)
    }

    fun findInstancesByApplicationId(applicationId: String): MutableSet<String> {
        val key = "$applicationPrefixKey:$applicationId"
        return redisTemplate.opsForSet().members(key) ?: HashSet()
    }

    fun findInstancesByApplicationIdLua(applicationId: String): List<String> {
        val appKey = "$applicationPrefixKey:$applicationId"
        val instanceKey = "$instancePrefixKey:$applicationId"
        val redisScript = RedisScript.of(
            javaClass.getResource("/scripts/validation.lua").readText(),
            List::class.java)
        return redisTemplate.execute(redisScript, mutableListOf(appKey, instanceKey)).let {
            it as List<String>
        }
    }
}