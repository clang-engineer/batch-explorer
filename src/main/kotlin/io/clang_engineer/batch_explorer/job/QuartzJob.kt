package io.clang_engineer.batch_explorer.job

import org.quartz.JobExecutionContext
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.configuration.support.ReferenceJobFactory
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager

@Component
class QuartzJob(
        private val jobLauncher: JobLauncher,
        private val jobRepository: JobRepository,
        private val transactionManager: PlatformTransactionManager,
        private val jobRegistry: JobRegistry,
) : org.quartz.Job {
    private val JOB_NAME = "batch-explorer-job"
    private val STEP_NAME = "batch-explorer-step"

    override fun execute(context: JobExecutionContext?) {
        val jobDataMap = context?.jobDetail?.jobDataMap as org.quartz.JobDataMap
        val jobParameters = transformQuartzJobDataMapToBatchJobParameters(jobDataMap)

        var job: Job? = try {
            jobRegistry.getJob(JOB_NAME)
        } catch (e: org.springframework.batch.core.launch.NoSuchJobException) {
            null
        }

        if (job == null) {
            job = job()
            jobRegistry.register(ReferenceJobFactory(job))
        }

        jobLauncher.run(job(), jobParameters)
    }

    fun job(): Job {
        return JobBuilder(JOB_NAME, jobRepository).start(step()).build()
    }

    fun step(): Step {
        return StepBuilder(STEP_NAME, jobRepository).tasklet(tasklet(), transactionManager).build()
    }

    fun tasklet(): Tasklet {
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