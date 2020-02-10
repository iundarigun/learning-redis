package br.com.devcave.redis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class MoreThanCacheApplication

fun main(args: Array<String>) {
	runApplication<MoreThanCacheApplication>(*args)
}
