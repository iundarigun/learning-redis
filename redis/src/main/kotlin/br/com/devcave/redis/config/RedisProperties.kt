package br.com.devcave.redis.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
class RedisProperties {

    var host: String = ""
    var port: Int = 12
    var password: String? = null
}