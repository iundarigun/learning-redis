package br.com.devcave.redis.controller

import br.com.devcave.redis.repository.RedisRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("redis")
class RedisController(
    private val redisRepository: RedisRepository
) {
    @GetMapping("{key}")
    fun getValue(@PathVariable key:String): String? {
        return redisRepository.getValue(key)
    }

    @PostMapping
    fun setValue(@RequestParam key: String, @RequestParam value: String) {
        redisRepository.setValue(key, value)
    }

    @DeleteMapping("{key}")
    fun delete(@PathVariable key: String) {
        redisRepository.deleteValue(key)
    }
}