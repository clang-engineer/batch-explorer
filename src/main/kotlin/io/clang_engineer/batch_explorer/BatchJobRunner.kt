package io.clang_engineer.batch_explorer

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class BatchJobRunner(private val jobLauncher: JobLauncher) {
    @Bean
    fun runJob(sampleTaskletJob: Job): CommandLineRunner {
        return CommandLineRunner {
            jobLauncher.run(sampleTaskletJob, JobParametersBuilder().toJobParameters())
        }
    }
}