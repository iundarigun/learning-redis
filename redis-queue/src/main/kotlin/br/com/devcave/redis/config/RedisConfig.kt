package br.com.devcave.redis.config

import br.com.devcave.redis.listener.RedisListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter

@Configuration
class RedisListenerConfig {

    @Bean
    fun messageListener(): MessageListenerAdapter = MessageListenerAdapter(RedisListener())

    @Bean
    fun redisContainer(redisConnectionFactory: RedisConnectionFactory): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(redisConnectionFactory)
        container.addMessageListener(messageListener(), topic())
        return container
    }

    @Bean
    fun topic(): ChannelTopic {
        return ChannelTopic("messageQueue")
    }
}