package br.com.devcave.redis.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import redis.embedded.RedisServer
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@TestConfiguration
class RedisTestConfiguration(
    @Value("\${spring.redis.port}")
    private val redisPort: Int
) {
    private val redisServer: RedisServer = RedisServer(redisPort)

    @PostConstruct
    fun postConstruct() {
        redisServer.start()
    }

    @PreDestroy
    fun preDestroy() {
        redisServer.stop()
    }
}