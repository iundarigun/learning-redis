package br.com.devcave.redis.repository

import br.com.devcave.redis.domain.Student
import org.springframework.data.repository.CrudRepository

interface StudentRepository: CrudRepository<Student, String> {
}