package br.com.devcave.redis

import br.com.devcave.redis.repository.ProgrammerRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    private lateinit var programmerRepository: ProgrammerRepository

	@Test
	fun `testing redis sample`() {
		val id = UUID.randomUUID().toString()
		val programmer = "12345"
		programmerRepository.setProgrammerAsString(id, programmer)
		val result = programmerRepository.getProgrammerAsString(id)
		Assertions.assertThat(result).isNotBlank().isEqualTo(programmer)
		val unexistingId = UUID.randomUUID().toString()
		val emptyResult = programmerRepository.getProgrammerAsString(unexistingId)
		Assertions.assertThat(emptyResult).isBlank()
	}

	@Test
	fun `expiring key`() {
		val id = UUID.randomUUID().toString()
		val programmer = "12345"
		programmerRepository.setProgrammerAsString(id, programmer)
		val result = programmerRepository.getProgrammerAsString(id)
		Assertions.assertThat(result).isNotBlank().isEqualTo(programmer)
		Thread.sleep(10_000)
		val emptyResult = programmerRepository.getProgrammerAsString(id)
		Assertions.assertThat(emptyResult).isBlank()
	}
}
