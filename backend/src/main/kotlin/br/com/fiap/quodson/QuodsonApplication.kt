package br.com.fiap.quodson

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuodsonApplication

fun main(args: Array<String>) {
    runApplication<QuodsonApplication>(*args)
}
