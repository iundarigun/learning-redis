package br.com.devcave.redis.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.redis.jedis.pool")
class JedisProperties {
    var maxActive: Int = 10
    var maxIdle: Int = 8
    var minIdle: Int = 8
}