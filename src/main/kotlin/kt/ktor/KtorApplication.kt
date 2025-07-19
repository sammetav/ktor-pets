package kt.ktor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KtorApplication

fun main(args: Array<String>) {
    runApplication<KtorApplication>(*args)
}
