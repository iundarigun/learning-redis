package br.com.devcave.redis.listener

import br.com.devcave.redis.domain.Employee
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisListener(
    private val objectMapper: ObjectMapper
) : MessageListener {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun onMessage(message: Message, pattern: ByteArray?) {
        log.info("received")
//        val employee = redisTemplate.valueSerializer.deserialize(message.body)
        val employee = objectMapper.readValue<Employee>(message.body)
        log.info("message content: $employee")
    }
}