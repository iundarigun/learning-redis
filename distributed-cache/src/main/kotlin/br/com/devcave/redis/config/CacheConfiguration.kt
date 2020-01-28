package br.com.devcave.redis.config

import br.com.devcave.redis.domain.Employee
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import java.time.Duration

@Configuration
class CacheConfiguration(
    private val connectionFactory: RedisConnectionFactory,
    private val objectMapper: ObjectMapper,
    @Value("\${spring.application.name}")
    private val applicationName: String
) {

    @Bean
    fun cacheManager(): CacheManager {
        val redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory)
//        val redisCacheWriter = CustomRedisCacheWriter(connectionFactory, Duration.ofMinutes(1))
        val cache1MinuteConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(1))
            .computePrefixWith { name -> "$applicationName:$name:" }
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(Employee::class.java).apply {
            this.setObjectMapper(objectMapper)
        }
        val cache60MinuteConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .computePrefixWith { name -> "$applicationName:$name:" }
            .serializeValuesWith(SerializationPair.fromSerializer(jackson2JsonRedisSerializer))

        return RedisCacheManager.RedisCacheManagerBuilder.fromCacheWriter(redisCacheWriter)
            .cacheDefaults(cache1MinuteConfiguration)
//            .disableCreateOnMissingCache()
            .withCacheConfiguration("findAll", cache1MinuteConfiguration)
            .withCacheConfiguration("count", cache1MinuteConfiguration)
            .withCacheConfiguration("findById", cache60MinuteConfiguration)
            .build()
    }
}