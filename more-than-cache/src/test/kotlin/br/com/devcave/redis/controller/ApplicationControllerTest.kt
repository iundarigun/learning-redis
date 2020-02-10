package br.com.devcave.redis.controller

import br.com.devcave.redis.config.RedisTestConfiguration
import br.com.devcave.redis.domain.InstanceDefinition
import br.com.devcave.redis.service.InstanceService
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import java.time.LocalDateTime
import java.util.UUID
import java.util.concurrent.TimeUnit

@Tag("integration")
@Import(RedisTestConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationControllerTest {
    @LocalServerPort
    private val port: Int? = null

    @Autowired
    lateinit var stringRedisTemplate: StringRedisTemplate

    @Autowired
    lateinit var instanceRedisTemplate: RedisTemplate<String, InstanceDefinition>

    @Autowired
    lateinit var instanceService: InstanceService

    @BeforeEach
    fun before() {
        RestAssured.port = port ?: 8080
    }

    @AfterEach
    fun after() {
        stringRedisTemplate.keys("*").forEach {
            stringRedisTemplate.delete(it)
        }
    }

    @Test
    fun `findAll with some instance expired`() {
        val app = "APP1"
        val instance1 = InstanceDefinition(
            UUID.randomUUID().toString(),
            "host1",
            LocalDateTime.now(),
            app
        )
        val instance2 = instance1.copy(
            uuid = UUID.randomUUID().toString(),
            host = "host2"
        )

        // Saving two instances
        instanceService.registerInstance(instance1)
        instanceService.registerInstance(instance2)

        // Changing TTL of an second instance
        instanceRedisTemplate
            .boundValueOps("morethancache:instance:$app:${instance2.uuid}")
            .expire(1, TimeUnit.MILLISECONDS)

        // Getting total members of application set on set
        val total = stringRedisTemplate
            .boundSetOps("morethancache:application:$app")
            .size()

        val list = RestAssured
            .given()
            .pathParam("applicationId", app)
            .contentType(ContentType.JSON)
            .`when`()
            .get("/applications/{applicationId}")
            .then()
            .body("$", Matchers.hasSize<Int>(1))
            .extract()
            .jsonPath().getList("", InstanceDefinition::class.java)

        Assertions.assertEquals(2, total)
        Assertions.assertEquals(1, list.size)
        Assertions.assertEquals(instance1, list[0])
    }
}