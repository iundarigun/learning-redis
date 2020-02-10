package br.com.devcave.redis.controller

import br.com.devcave.redis.domain.InstanceDefinition
import br.com.devcave.redis.service.ApplicationService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("applications")
class ApplicationController(
    private val applicationService: ApplicationService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("{applicationId}")
    fun findAll(@PathVariable applicationId: String): ResponseEntity<Set<InstanceDefinition>> {
        log.info("findAll applicationId=$applicationId")
        val result = applicationService.findByApplicationId(applicationId)
        return ResponseEntity.ok(result)
    }

    @GetMapping("lua/{applicationId}")
    fun findAllWithLua(@PathVariable applicationId: String): ResponseEntity<Set<InstanceDefinition>> {
        log.info("findAllWithLua applicationId=$applicationId")
        val result = applicationService.findByApplicationIdByLua(applicationId)
        return ResponseEntity.ok(result)
    }
}