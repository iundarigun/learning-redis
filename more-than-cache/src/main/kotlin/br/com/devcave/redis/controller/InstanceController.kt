package br.com.devcave.redis.controller

import br.com.devcave.redis.domain.InstanceDefinition
import br.com.devcave.redis.service.InstanceService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("instances")
class InstanceController(private val instanceService: InstanceService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun registerInstance(
        @RequestBody instanceDefinition: InstanceDefinition
    ): HttpEntity<Any?> {
        log.info("registerInstance, instance=$instanceDefinition")
        instanceService.registerInstance(instanceDefinition)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping
    fun removeInstance(
        @RequestBody instanceDefinition: InstanceDefinition
    ): HttpEntity<Any?> {
        log.info("removeInstance, instanceDefinition=$instanceDefinition")
        instanceService.deleteInstance(instanceDefinition)
        return ResponseEntity.noContent().build()
    }
}