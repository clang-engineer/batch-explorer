package io.clang_engineer.batch_explorer.service

import io.clang_engineer.batch_explorer.service.dto.JobExecutionDTO
import io.clang_engineer.batch_explorer.service.dto.StepExecutionDTO
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobOperator
import org.springframework.stereotype.Service

@Service
class BatchExecutionService(
  private val jobExplorer: JobExplorer,
  private val jobOperator: JobOperator
) {
  private val log = org.slf4j.LoggerFactory.getLogger(javaClass)

  fun fetchAllExecutions(): List<JobExecutionDTO> {
    log.info("Listing all job executions")

    val result = mutableListOf<JobExecutionDTO>()

    val jobNames = jobExplorer.jobNames
    for (jobName in jobNames) {
      val jobInstances = jobExplorer.findJobInstancesByJobName(jobName, 0, Int.MAX_VALUE)
      for (jobInstance in jobInstances) {
        val executions = jobExplorer.getJobExecutions(jobInstance)
        for (execution in executions) {
          val stepExecutions = execution.stepExecutions.map { stepExecution ->
            StepExecutionDTO(
              id = stepExecution.id,
              stepName = stepExecution.stepName,
              status = stepExecution.status.name,
              startTime = stepExecution.startTime,
              endTime = stepExecution.endTime,
              exitCode = stepExecution.exitStatus.exitCode,
              exitDescription = stepExecution.exitStatus.exitDescription,
              lastUpdated = stepExecution.lastUpdated,
              readCount = stepExecution.readCount,
              writeCount = stepExecution.writeCount,
              commitCount = stepExecution.commitCount,
              rollbackCount = stepExecution.rollbackCount,
              readSkipCount = stepExecution.readSkipCount,
              processSkipCount = stepExecution.processSkipCount,
              writeSkipCount = stepExecution.writeSkipCount,
              filterCount = stepExecution.filterCount,
              skipCount = stepExecution.skipCount,
              summary = stepExecution.summary,
            )
          }
          result.add(
            JobExecutionDTO(
              id = execution.id,
              jobName = execution.jobInstance.jobName,
              status = execution.status.name,
              startTime = execution.startTime,
              endTime = execution.endTime,
              exitCode = execution.exitStatus.exitCode,
              exitDescription = execution.exitStatus.exitDescription,
              lastUpdated = execution.lastUpdated,
              stepExecutions = stepExecutions,
            )
          )
        }
      }
    }
    return result
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
    if (jobExecution?.status == BatchStatus.FAILED) {
      try {
        jobOperator.restart(jobExecutionId)
        log.info("JobExecution $jobExecutionId has been restarted.")
      } catch (e: Exception) {
        log.error("Failed to restart job execution: $jobExecutionId", e)
      }
    } else {
      log.info("JobExecution $jobExecutionId is not in a failed state.")
    }
  }
}

