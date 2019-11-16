package br.com.devcave.redis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class RedisApplication

fun main(args: Array<String>) {
	runApplication<RedisApplication>(*args)
}
