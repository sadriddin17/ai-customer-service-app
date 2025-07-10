package com.greg.respiroc.aicustomerserviceapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class AiCustomerServiceAppApplication

fun main(args: Array<String>) {
    runApplication<AiCustomerServiceAppApplication>(*args)
}
