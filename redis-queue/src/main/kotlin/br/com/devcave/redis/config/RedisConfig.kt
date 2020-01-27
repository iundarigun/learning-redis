package br.com.devcave.redis.config

import br.com.devcave.redis.domain.Employee
import br.com.devcave.redis.listener.RedisListener
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun redisContainer(
        redisConnectionFactory: RedisConnectionFactory,
        redisListener: RedisListener
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(redisConnectionFactory)
        container.addMessageListener(MessageListenerAdapter(redisListener), topic())
        return container
    }

    @Bean
    fun topic(): ChannelTopic {
        return ChannelTopic("messageQueue")
    }

    @Bean
    fun redisTemplate(
        redisConnectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper
    ): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            val serializer = GenericJackson2JsonRedisSerializer(objectMapper)
            this.setConnectionFactory(redisConnectionFactory)
            this.setEnableTransactionSupport(true)
            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = serializer
            this.hashKeySerializer = StringRedisSerializer()
            this.hashValueSerializer = serializer
        }
    }
}