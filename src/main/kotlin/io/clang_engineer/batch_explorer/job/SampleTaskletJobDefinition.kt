package io.clang_engineer.batch_explorer.job

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager

@Component
class SampleTaskletJobDefinition {
    @Bean
    fun sampleTaskletJob(jobRepository: JobRepository, sampleStep: Step): Job {
        return JobBuilder("sample-job", jobRepository).start(sampleStep).build()
    }

    @Bean
    fun sampleStep(jobRepository: JobRepository, transactionManager: PlatformTransactionManager, sampleTasklet: Tasklet): Step {
        return StepBuilder("sample-step", jobRepository).tasklet(sampleTasklet, transactionManager).build()
    }

    @Bean
    fun sampleTasklet(): Tasklet {
        return Tasklet { _, _ ->
            println("Hello, World!")
            null
        }
    }

}