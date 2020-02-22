package br.com.devcave.redis.config

import org.apache.logging.log4j.util.Strings
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
class RedisConfiguration(
    @Value("\${spring.redis.host:}")
    private val masterHost: String,
    @Value("\${spring.redis.port:}")
    private val masterPort: Int,
    @Value("\${spring.redis.password:}")
    private val masterPassword: String,
    @Value("\${spring.redis-slave.host:}")
    private val slaveHost: String,
    @Value("\${spring.redis-slave.port:}")
    private val slavePort: Int,
    @Value("\${spring.redis-slave.password:}")
    private val slavePassword: String
) {

    @Bean
    @Primary
    fun redisMasterConnectionFactory(): LettuceConnectionFactory {
        val redisConfiguration = RedisStandaloneConfiguration(masterHost, masterPort)
        if (Strings.isNotBlank(masterPassword)) {
            redisConfiguration.password = RedisPassword.of(masterPassword)
        }
        return LettuceConnectionFactory(redisConfiguration)
    }

    @Bean
    fun redisSlaveConnectionFactory(): LettuceConnectionFactory {
        val redisConfiguration = RedisStandaloneConfiguration(slaveHost, slavePort)
        if (Strings.isNotBlank(slavePassword)) {
            redisConfiguration.password = RedisPassword.of(slavePassword)
        }
        return LettuceConnectionFactory(redisConfiguration)
    }

    @Bean
    fun redisMasterTemplate(): StringRedisTemplate {
        return StringRedisTemplate(redisMasterConnectionFactory())
    }

    @Bean
    fun redisSlaveTemplate(): StringRedisTemplate {
        return StringRedisTemplate(redisSlaveConnectionFactory())
    }
}