package io.clang_engineer.batch_explorer.service.dto

import java.time.LocalDateTime

data class JobExecutionDTO(
  val id: Long,
  val jobName: String,
  val status: String,
  val startTime: LocalDateTime?,
  val endTime: LocalDateTime?,
  val exitCode: String,
  val exitDescription: String,
  val lastUpdated: LocalDateTime?,
  val stepExecutions: List<StepExecutionDTO>?,
)

