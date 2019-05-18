package kr.appkr.dockerdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DockerdemoApplication

fun main(args: Array<String>) {
    runApplication<DockerdemoApplication>(*args)
}
