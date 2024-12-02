package io.clang_engineer.batch_explorer.web

import io.clang_engineer.batch_explorer.service.QuartzSchedulerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quartz")
class QuartzResource(
  private val quartzSchedulerService: QuartzSchedulerService
) {
  private val log = org.slf4j.LoggerFactory.getLogger(javaClass)

  @PostMapping("/schedule")
  fun scheduleBatchJobExecution(@RequestBody data: Map<String, Any>): ResponseEntity<Void> {
    log.info("Scheduling batch job execution")

    quartzSchedulerService.scheduleBatchJobExecution(
      data["cronExpression"] as String,
      data["jobDataMap"] as Map<String, String>
    )
    return ResponseEntity.ok().build()
  }
}