package io.clang_engineer.batch_explorer.service

import io.clang_engineer.batch_explorer.service.dto.JobExecutionDTO
import io.clang_engineer.batch_explorer.service.dto.StepExecutionDTO
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.stereotype.Service

@Service
class BatchMetaService(
  private val jobExplorer: JobExplorer,
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
}

