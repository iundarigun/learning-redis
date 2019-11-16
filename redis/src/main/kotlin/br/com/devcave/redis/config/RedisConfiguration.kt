package br.com.devcave.redis.config

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfiguration(
    private val redisProperties: RedisProperties,
    private val jedisProperties: JedisProperties
) {

    @Bean
    fun jedisClientConfiguration(): JedisClientConfiguration {
        val builder =
            JedisClientConfiguration.builder() as JedisClientConfiguration.JedisPoolingClientConfigurationBuilder

        return builder.poolConfig(GenericObjectPoolConfig().apply {
            this.maxTotal = jedisProperties.maxActive
            this.maxIdle = jedisProperties.maxIdle
            this.minIdle = jedisProperties.minIdle
        }).build()
    }

    @Bean
    fun jedisConnectionFactory(jedisClientConfiguration: JedisClientConfiguration): JedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration().apply {
            this.hostName = redisProperties.host
            this.port = redisProperties.port
            redisProperties.password?.let { this.password = RedisPassword.of(it) }
        }
        return JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration)

    }

    @Bean
    fun redisTemplate(jedisConnectionFactory: JedisConnectionFactory): RedisTemplate<String, Any?> {
        return RedisTemplate<String, Any?>().apply {
            this.setConnectionFactory(jedisConnectionFactory)
        }
    }
}