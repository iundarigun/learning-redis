package br.com.devcave.redis.repository

import br.com.devcave.redis.domain.InstanceDefinition
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class InstanceRepository(private val redisTemplate: RedisTemplate<String, InstanceDefinition>) {

    private val instancePrefixKey = "morethancache:instance"

    fun saveInstance(
        instanceDefinition: InstanceDefinition,
        instanceTtlInMinutes: Long
    ) {
        val key = "$instancePrefixKey:${instanceDefinition.application}:${instanceDefinition.uuid}"
        redisTemplate.boundValueOps(key).set(instanceDefinition, instanceTtlInMinutes, TimeUnit.MINUTES)
    }

    fun removeInstance(
        instanceDefinition: InstanceDefinition
    ) {
        val key = "$instancePrefixKey:${instanceDefinition.application}:${instanceDefinition.uuid}"
        redisTemplate.delete(key)
    }

    fun getInstance(
        applicationId: String,
        instance: String
    ): InstanceDefinition? {
        val key = "$instancePrefixKey:$applicationId:$instance"
        return redisTemplate.boundValueOps(key).get()
    }
}