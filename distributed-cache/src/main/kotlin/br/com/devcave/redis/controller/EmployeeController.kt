package br.com.devcave.redis.controller

import br.com.devcave.redis.domain.Employee
import br.com.devcave.redis.service.EmployeeService
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("employees")
class EmployeeController(
    private val employeeService: EmployeeService
) {

    @GetMapping
    fun getAll(): HttpEntity<List<Employee>> {
        return ResponseEntity.ok(employeeService.findAll())
    }
    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): HttpEntity<Employee> {
        return employeeService.findById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("count")
    fun count(): HttpEntity<Long> {
        return ResponseEntity.ok(employeeService.count())
    }
}