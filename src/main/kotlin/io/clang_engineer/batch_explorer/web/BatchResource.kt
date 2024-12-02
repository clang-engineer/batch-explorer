package io.clang_engineer.batch_explorer.web

import io.clang_engineer.batch_explorer.service.BatchMetaService
import io.clang_engineer.batch_explorer.service.dto.JobExecutionDTO
import org.springframework.batch.core.JobInstance
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class BatchResource(
  private val batchMetaService: BatchMetaService
) {
  private val log = org.slf4j.LoggerFactory.getLogger(javaClass)

  @GetMapping("/job/instances")
  fun fetchAllJobs(): ResponseEntity<List<JobInstance>> {
    log.info("Listing all jobs")

//    val jobInstances = jobExplorer.findJobInstancesByJobName("batch-explorer-job", 0, 100)
    return ResponseEntity.ok().build()
  }

  @GetMapping("/job/executions")
  fun fetchAllJobExecutions(): ResponseEntity<List<JobExecutionDTO>> {
    log.info("Listing all job executions")

    val jobExecutions = batchMetaService.fetchAllExecutions()
    return ResponseEntity.ok(jobExecutions)
  }

  @GetMapping("/job/steps")
  fun fetchAllJobSteps(): ResponseEntity<List<org.springframework.batch.core.StepExecution>> {
    log.info("Listing all job steps")

    return ResponseEntity.ok().build()
  }

}