package io.clang_engineer.batch_explorer

import io.clang_engineer.batch_explorer.service.QuartzSchedulerService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class BatchExplorerApplication

fun main(args: Array<String>) {
    runApplication<BatchExplorerApplication>(*args)
}

@Component
class StartupRunner(private val quartzSchedulerService: QuartzSchedulerService) : CommandLineRunner {
    override fun run(vararg args: String?) {
        quartzSchedulerService.scheduleBatchJobExecution()
    }
}