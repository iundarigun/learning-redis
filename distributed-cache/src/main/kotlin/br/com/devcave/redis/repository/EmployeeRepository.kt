package br.com.devcave.redis.repository

import br.com.devcave.redis.domain.Employee
import com.github.javafaker.Faker
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import java.time.ZoneId

@Repository
class EmployeeRepository {

    private val faker = Faker()
    private val log = LoggerFactory.getLogger(javaClass)

    @Cacheable("findById")
    fun findById(id: Long): Employee? {
        log.info("findById id $id, not cache available")
        return createEmployee()
    }

    @Cacheable("findAll")
    fun findAll(): List<Employee> {
        log.info("findAll, not cache available")
        return (1..faker.number().numberBetween(10L, 40L))
            .map {
        Employee(
            name = faker.name().fullName(),
            document = faker.idNumber().valid(),
            collageCompletedYear = faker.number().numberBetween(1990, 2020),
            bornAt = faker.date().birthday(20, 60)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        ) }
    }

    @Cacheable("count")
    fun count(): Long = faker.number().numberBetween(10L, 40L).also {
        log.info("count, not cache available")
    }

    private fun createEmployee(): Employee {
        return Employee(
            name = faker.name().fullName(),
            document = faker.idNumber().valid(),
            collageCompletedYear = faker.number().numberBetween(1990, 2020),
            bornAt = faker.date().birthday(20, 60)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        )
    }
}