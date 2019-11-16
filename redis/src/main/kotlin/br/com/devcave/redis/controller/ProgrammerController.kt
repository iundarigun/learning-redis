package br.com.devcave.redis.controller

import br.com.devcave.redis.domain.Programmer
import br.com.devcave.redis.service.ProgrammerService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("programmer-string")
class ProgrammerController(
    private val programmerService: ProgrammerService,
    private val mapper: ObjectMapper
) {

    @PostMapping
    fun addProgrammer(@RequestBody programmer: Programmer): HttpEntity<Any?> {
        programmerService.setProgrammerAsString(programmer.id.toString(),
            mapper.writeValueAsString(programmer))
        return ResponseEntity.ok().build()
    }

    @GetMapping("{id}")
    fun getProgrammer(@PathVariable id:String): HttpEntity<String> {
        val programmer = programmerService.getProgrammerAsString(id)
        return ResponseEntity.ok(programmer)
    }

    @PostMapping("list")
    fun addToProgrammersList(@RequestBody programmer: Programmer): HttpEntity<Any?> {
        programmerService.addToProgrammersList(programmer)
        return ResponseEntity.ok().build()
    }

    @GetMapping("list")
    fun getProgrammersListMembers(): HttpEntity<List<Programmer>> {
        val programmersListMembers = programmerService.getProgrammersListMembers()
        return ResponseEntity.ok(programmersListMembers)
    }

    @GetMapping("list/count")
    fun getProgrammerListCount(): HttpEntity<Long> {
        return ResponseEntity.ok(programmerService.getProgrammerListCount())
    }

}