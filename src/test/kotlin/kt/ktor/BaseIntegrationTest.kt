package kt.ktor

import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
abstract class BaseIntegrationTest {

    companion object {

        private val logger = LoggerFactory.getLogger(BaseIntegrationTest::class.java)

        private const val VERSION = "postgres:14.6"
        private const val DB_NAME = "data"
        private const val DB_USER = "postgres"
        private const val DB_PWD = "postgres"

        private val db = PostgreSQLContainer(DockerImageName.parse(VERSION))
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USER)
            .withPassword(DB_PWD)

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.r2dbc.url", Companion::r2dbcUrl)
            registry.add("spring.r2dbc.username", db::getUsername)
            registry.add("spring.r2dbc.password", db::getPassword)

            registry.add("postgres.host", db::getHost)
            registry.add("postgres.port") { db.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT) }
            registry.add("postgres.database", db::getDatabaseName)
            registry.add("postgres.username", db::getUsername)
            registry.add("postgres.password", db::getPassword)
        }

        private fun r2dbcUrl(): String =
            "r2dbc:postgresql://${db.host}:${db.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT)}/${db.databaseName}"
    }
}
