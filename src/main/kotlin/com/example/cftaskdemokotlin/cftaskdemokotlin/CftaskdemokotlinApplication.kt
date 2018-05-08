package com.example.cftaskdemokotlin.cftaskdemokotlin

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.DefaultApplicationArguments
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.task.configuration.EnableTask
import org.springframework.context.annotation.Bean
import java.time.LocalDateTime

@EnableTask
@SpringBootApplication
class CftaskdemokotlinApplication {

    private val log = LoggerFactory.getLogger(CftaskdemokotlinApplication::class.java)

    @Bean
    fun run(args2: DefaultApplicationArguments) = ApplicationRunner {
        it.sourceArgs.forEach {
            log.info(it)
            when (it) {
                "endpoint1" -> log.info("Calling endpoint 1")
                "endpoint2" -> log.info("Calling endpoint 2")
                else -> log.warn("Call with endpoint1 or endpoint2")
            }
        }

        log.info("Running at ${LocalDateTime.now()}")
    }
}

fun main(args: Array<String>) {
    runApplication<CftaskdemokotlinApplication>(*args)
}
