package br.com.devcave.redis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class DistributedCacheApplication

fun main(args: Array<String>) {
	runApplication<DistributedCacheApplication>(*args)
}
