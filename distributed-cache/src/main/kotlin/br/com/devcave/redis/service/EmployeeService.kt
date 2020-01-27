package br.com.devcave.redis.service

import br.com.devcave.redis.domain.Employee
import br.com.devcave.redis.repository.EmployeeRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun findById(id: Long): Employee? {
        log.info("findById id $id")
        return employeeRepository.findById(id)
    }

    fun findAll(): List<Employee> {
        log.info("findAll")
        return employeeRepository.findAll()
    }

    fun count(): Long {
        log.info("count")
        return employeeRepository.count()
    }
}