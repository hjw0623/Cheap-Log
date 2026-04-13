package kr.cheaplog.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CheaplogApplication

fun main(args: Array<String>) {
    runApplication<CheaplogApplication>(*args)
}
