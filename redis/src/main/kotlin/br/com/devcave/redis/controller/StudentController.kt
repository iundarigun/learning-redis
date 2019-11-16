package br.com.devcave.redis.controller

import br.com.devcave.redis.domain.Student
import br.com.devcave.redis.service.StudentService
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("students")
class StudentController(
    private val studentService: StudentService
) {

    @PostMapping
    fun addStudent(@RequestBody student: Student): HttpEntity<Any?> {
        studentService.save(student)
        return ResponseEntity.ok().build()
    }

    @GetMapping("{id}")
    fun getStudent(@PathVariable id: String): HttpEntity<Student?> {
        val student = studentService.findById(id)
        return student?.let { ResponseEntity.ok(it) } ?: ResponseEntity.ok().build()
    }

    @GetMapping("")
    fun addToProgrammersList(): HttpEntity<List<Student>> {
        val students = studentService.findAll()
        return  ResponseEntity.ok(students)
    }
}