package io.clang_engineer.batch_explorer.web

import io.clang_engineer.batch_explorer.service.BatchExecutionService
import io.clang_engineer.batch_explorer.service.dto.JobExecutionDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class BatchResource(
  private val batchExecutionService: BatchExecutionService
) {
  private val log = org.slf4j.LoggerFactory.getLogger(javaClass)

  @GetMapping("/job/executions")
  fun fetchAllJobExecutions(): ResponseEntity<List<JobExecutionDTO>> {
    log.info("Listing all job executions")

    val jobExecutions = batchExecutionService.fetchAllExecutions()
    return ResponseEntity.ok(jobExecutions)
  }

  @PostMapping("/job/executions/{executionId}/stop")
  fun stopExecution(@PathVariable executionId: Long): ResponseEntity<Void> {
    log.info("Stopping job execution: $executionId")

    batchExecutionService.stopJobExecution(executionId)
    return ResponseEntity.ok().build()
  }

  @PostMapping("/job/executions/{executionId}/restart")
  fun restartExecution(@PathVariable executionId: Long): ResponseEntity<Void> {
    log.info("Restarting job execution: $executionId")

    batchExecutionService.restartJobExecution(executionId)
    return ResponseEntity.ok().build()
  }

}