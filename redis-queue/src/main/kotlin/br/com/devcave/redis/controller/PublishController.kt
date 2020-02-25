package br.com.devcave.redis.controller

import br.com.devcave.redis.domain.Employee
import br.com.devcave.redis.listener.RedisService
import br.com.devcave.redis.publisher.RedisPublisher
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("publish")
class PublishController(
    private val redisPublisher: RedisPublisher,
    private val redisService: RedisService
) {
    private val log = LoggerFactory.getLogger(javaClass)
    @PostMapping
    fun publishMessage(@RequestBody employee: Employee): ResponseEntity<Any?> {
        log.info("publish message $employee")
        redisPublisher.publish(employee)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun transactionTest(): ResponseEntity<Any?> {
        redisService.transaction()
        return ResponseEntity.ok().build()
    }
}