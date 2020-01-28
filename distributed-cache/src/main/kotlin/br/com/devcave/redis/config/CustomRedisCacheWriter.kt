package br.com.devcave.redis.config

import org.slf4j.LoggerFactory
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStringCommands
import org.springframework.data.redis.core.types.Expiration
import java.time.Duration
import java.util.Optional
import java.util.concurrent.TimeUnit

class CustomRedisCacheWriter(
    private val connectionFactory: RedisConnectionFactory,
    private val duration: Duration
) : RedisCacheWriter {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun put(name: String, key: ByteArray, value: ByteArray, ttl: Duration?) {
        log.info("put name $name")
        ttl?.let {
            connectionFactory.connection.set(
                key,
                value,
                Expiration.from(ttl.toMillis(), TimeUnit.MILLISECONDS),
                RedisStringCommands.SetOption.upsert()
            )
        } ?: connectionFactory.connection.set(key, value)
    }

    override fun clean(name: String, pattern: ByteArray) {
        log.info("clean name $name")
        val connection = connectionFactory.connection
        val keys =
            Optional.ofNullable<Set<ByteArray>>(connection.keys(pattern))
                .orElse(emptySet())
                .toTypedArray()
        if (keys.isNotEmpty()) {
            connection.del(*keys)
        }
    }

    override fun remove(name: String, key: ByteArray) {
        log.info("remove name $name")
        connectionFactory.connection.del(key)
    }

    override fun get(name: String, key: ByteArray): ByteArray? {
        log.info("get name $name")
        val connection = connectionFactory.connection
        val result = connection.get(key)
        result?.let {
            connection.pExpire(key, duration.toMillis())
        }
        return result
    }

    override fun putIfAbsent(name: String, key: ByteArray, value: ByteArray, ttl: Duration?): ByteArray? {
        log.info("putIfAbsent name $name")
        val connection = connectionFactory.connection
        connection.setNX(key, value)?.let { result ->
            if (result) {
                ttl?.let { duration ->
                    connection.pExpire(key, duration.toMillis())
                }
                return null
            }
        }
        return connection.get(key)
    }
}