package br.com.devcave.redis.job

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.TimeUnit

@Component
class SampleJob(private val redisTemplate: StringRedisTemplate) {

    private val log = LoggerFactory.getLogger(javaClass)

    private val processingSomethingJobKey = "application:job:processingSomething"

    @Scheduled(cron = "0 0 0/1 1/1 * ?")
    fun processingSomething() {
       val canRun = redisTemplate.boundValueOps(processingSomethingJobKey)
           .setIfAbsent(UUID.randomUUID().toString(), 5L, TimeUnit.MINUTES)
        if (canRun != null && canRun) {
            log.info("Lock acquired. Running something in this instance")
        } else {
            log.info("Lock unavailable! Job is running in other instance")
        }
    }
}