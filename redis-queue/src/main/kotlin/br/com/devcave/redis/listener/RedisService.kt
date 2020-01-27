package br.com.devcave.redis.listener

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun transaction(){

        val before = redisTemplate.boundValueOps("transaction-test").get()
        log.info("get value $before transaction...")
        log.info("starting transaction...")
        redisTemplate.multi()
        val boundValueOps = redisTemplate.boundValueOps("transaction-test")
        val test = boundValueOps.get()
        // This value is allways null in transaction?
        // yes, because the transaction is execute on exec
        log.info("get value $test and wait some time...")
        Thread.sleep(10_000L)
        log.info("changing value ...")
        boundValueOps.set("some value")
        redisTemplate.exec()
        log.info("commit transaction ...")

    }
}