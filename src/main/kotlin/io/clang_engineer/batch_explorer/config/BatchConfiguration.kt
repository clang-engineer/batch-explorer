package io.clang_engineer.batch_explorer.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableBatchProcessing
class BatchConfiguration(
        private val transactionManager: PlatformTransactionManager, private val jobRepository: JobRepository,
) {
    private val JOB_NAME = "batch-explorer-job"
    private val STEP_NAME = "batch-explorer-step"

    @Bean
    fun sampleJob(): Job {
        return JobBuilder(JOB_NAME, jobRepository).start(sampleStep()).build()
    }

    @Bean
    fun sampleStep(): Step {
        return StepBuilder(STEP_NAME, jobRepository)
                .tasklet(sampleTasklet(), transactionManager).build()
    }

    @Bean
    fun sampleTasklet(): Tasklet {
        return Tasklet { _, _ ->
            synchronized(this) {
                val file = java.io.File("build/output.txt")

                if (!file.exists()) {
                    file.createNewFile()
                }

                file.appendText("Job executed at ${java.time.LocalDateTime.now()}\n")
            }
            null
        }
    }
}