package br.com.devcave.redis.publisher

import br.com.devcave.redis.domain.Employee
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisPublisher(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val channelTopic: ChannelTopic
) {

    fun publish(message: Employee) {
        redisTemplate.convertAndSend(channelTopic.topic, message)
    }
}