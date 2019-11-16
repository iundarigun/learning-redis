package br.com.devcave.redis.repository

import br.com.devcave.redis.domain.Programmer
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class ProgrammerRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, Any?>
) : ProgrammerRepository {

    private val REDIS_LIST_KEY = "ProgrammerList"

    override fun setProgrammerAsString(idKey: String, programmer: String) {
        redisTemplate.opsForValue().set(idKey, programmer)
        redisTemplate.expire(idKey, 10, TimeUnit.SECONDS)
    }

    override fun getProgrammerAsString(idKey: String): String {
        return redisTemplate.opsForValue().get(idKey)?.let { it as String } ?: ""
    }

    override fun addToProgrammersList(programmer: Programmer) {
        redisTemplate.opsForList().leftPush(REDIS_LIST_KEY, programmer)
    }

    override fun getProgrammersListMembers(): List<Programmer> {
        return redisTemplate.opsForList().range(REDIS_LIST_KEY, 0, -1) as List<Programmer>? ?: emptyList()
    }

    override fun getProgrammerListCount(): Long {
        return redisTemplate.opsForList().size(REDIS_LIST_KEY)?:0
    }
}