package raspberry.ledservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["raspberry"])
class LedServiceApplication

fun main(args: Array<String>) {
	runApplication<LedServiceApplication>(*args)
}
