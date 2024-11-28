package io.clang_engineer.batch_explorer.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableBatchProcessing
class BatchConfiguration(
        private val jobRepository: JobRepository,
        private val transactionManager: PlatformTransactionManager,
        private val jobLauncher: JobLauncher
) {
    @Bean
    fun runJob(): CommandLineRunner {
        val jobParameter = JobParametersBuilder()
                .addString("jobId", System.currentTimeMillis().toString())
                .toJobParameters()

        return CommandLineRunner {
            jobLauncher.run(sampleJob(), jobParameter)
        }
    }

    @Bean
    fun sampleJob(): Job {
        return JobBuilder("sample-job", jobRepository)
                .start(sampleStep())
                .build()
    }

    @Bean
    fun sampleStep(): Step {
        return StepBuilder("sample-step", jobRepository)
                .tasklet(sampleTasklet(), transactionManager)
                .build()
    }

    @Bean
    fun sampleTasklet(): Tasklet {
        return Tasklet { _, _ ->
            println("Hello, World!")
            null
        }
    }
}