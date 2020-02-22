package br.com.devcave.redis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MasterSlaveApplication

fun main(args: Array<String>) {
	runApplication<MasterSlaveApplication>(*args)
}
