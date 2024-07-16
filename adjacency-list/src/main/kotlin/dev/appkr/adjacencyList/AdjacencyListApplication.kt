package dev.appkr.adjacencyList

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

@SpringBootApplication
class AdjacencyListApplication {
	@Autowired
	private lateinit var repository: CategoryRepository

	@EventListener
	fun on(e: ApplicationReadyEvent) {
		val dev = repository.save(Category(name = "dev"))
		val etc = repository.save(Category(name = "etc"))
		val usr = repository.save(Category(name = "usr"))

		dev.add(Category(name = "core"))
		dev.add(Category(name = "fd"))
		dev.add(Category(name = "full"))
		repository.save(dev)

		etc.add(Category(name = "apt"))
		etc.add(Category(name = "cron.d"))
		etc.add(Category(name = "default"))
		repository.save(etc)

		usr.add(Category(name = "local"))
		usr.add(Category(name = "share"))
		usr.add(Category(name = "lib"))
		repository.save(usr)
	}
}

fun main(args: Array<String>) {
	runApplication<AdjacencyListApplication>(*args)
}
