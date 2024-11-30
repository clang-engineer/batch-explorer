package io.clang_engineer.batch_explorer.service

import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobOperator
import org.springframework.stereotype.Service

@Service
class BatchControlService(
        private val jobOperator: JobOperator,
        private val jobLauncher: org.springframework.batch.core.launch.JobLauncher,
        private val jobExplorer: JobExplorer,
        private val jobRegistry: JobRegistry
) {
    private final val log = org.slf4j.LoggerFactory.getLogger(BatchControlService::class.java)

    fun listJobs() {
        log.info("Listing all jobs")

        val jobNames = jobExplorer.jobNames
        jobNames.forEach { jobName ->
            println("Job: $jobName")
        }
    }

    fun stopJobExecution(jobExecutionId: Long) {
        log.info("Stopping job execution: $jobExecutionId")

        val jobExecution = jobExplorer.getJobExecution(jobExecutionId) as org.springframework.batch.core.JobExecution
        if (jobExecution !== null && jobExecution.isRunning) {
            jobOperator.stop(jobExecutionId)
        }
    }

    fun restartJobExecution(jobExecutionId: Long) {
        log.info("Restarting job execution: $jobExecutionId")

        val jobExecution = jobExplorer.getJobExecution(jobExecutionId)

        if (jobExecution !== null && jobExecution.status == BatchStatus.FAILED) {
            val jobName = jobExecution.jobInstance.jobName

            val job = jobRegistry.getJob(jobName)

            val jobParameters = jobExecution.jobParameters

            jobLauncher.run(job, jobParameters)
        }
    }
}