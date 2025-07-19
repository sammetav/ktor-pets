package kt.ktor

import org.flywaydb.core.Flyway
import org.flywaydb.core.api.Location
import org.flywaydb.core.api.configuration.FluentConfiguration
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
        @JvmStatic
        protected var flywayBuilder: FluentConfiguration

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

        fun runMigrations(schema: String? = null) {
            schema?.apply { flywayBuilder.schemas(schema) }
            flywayBuilder.load().migrate()
        }

        init {
            logger.info("Starting postgres test container")
            db.start()
            flywayBuilder = Flyway
                .configure()
                .locations(
                    Location("classpath:db/migration"),
                    // data migrations can be excluded during development of integration tests, which don't need these migrations
                    Location("classpath:db/tsetdata")
                )
                .dataSource(db.jdbcUrl, DB_USER, DB_PWD)
            logger.info("Executing flyway migrations")
            runMigrations()
        }
    }
}
