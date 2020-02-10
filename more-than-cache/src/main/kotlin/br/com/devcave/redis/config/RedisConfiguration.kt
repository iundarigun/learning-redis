package br.com.devcave.redis.config

import br.com.devcave.redis.domain.InstanceDefinition
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfiguration {

    @Bean
    fun redisTemplate(
        connectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper
    ): RedisTemplate<String, InstanceDefinition> {
        val template = RedisTemplate<String, InstanceDefinition>()

        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(InstanceDefinition::class.java)
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper)

        template.setConnectionFactory(connectionFactory)
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = jackson2JsonRedisSerializer
        template.hashKeySerializer = StringRedisSerializer()
        template.hashValueSerializer = jackson2JsonRedisSerializer

        return template
    }
}