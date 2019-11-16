package br.com.devcave.redis.service

import br.com.devcave.redis.domain.Student
import br.com.devcave.redis.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(private val studentRepository: StudentRepository) {

    fun save(student:Student) {
        studentRepository.save(student)
    }

    fun findById(id:String):Student? {
        return studentRepository.findById(id).orElse(null)
    }

    fun findAll(): List<Student> {
        return ArrayList<Student>().apply {
            this.addAll(studentRepository.findAll())
        }
    }

    fun deleteById(id: String){
        studentRepository.deleteById(id)
    }
}