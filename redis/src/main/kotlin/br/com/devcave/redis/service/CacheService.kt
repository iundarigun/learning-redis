package br.com.devcave.redis.service

import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class CacheService {

    private val log = LoggerFactory.getLogger(javaClass)

    @Cacheable(cacheNames = ["myCache"])
    fun cachingThis(name: String): String {
        log.info("Not using cache for $name")
        return "This is $name's application"
    }
}