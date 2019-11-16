package br.com.devcave.redis.controller

import br.com.devcave.redis.service.CacheService
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("cache")
class CacheController(private val cacheService: CacheService) {

    @GetMapping("{name}")
    fun testingCache(@PathVariable name: String):HttpEntity<String>{
        val phrase = cacheService.cachingThis(name)
        return ResponseEntity.ok(phrase)
    }
}