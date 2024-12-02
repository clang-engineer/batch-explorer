package io.clang_engineer.batch_explorer.service.dto

import java.time.LocalDateTime

data class StepExecutionDTO(
  val id: Long,
  val stepName: String,
  val status: String,
  val startTime: LocalDateTime?,
  val endTime: LocalDateTime?,
  val exitCode: String,
  val exitDescription: String,
  val lastUpdated: LocalDateTime?,
  val readCount: Long,
  val writeCount: Long,
  val commitCount: Long,
  val rollbackCount: Long,
  val readSkipCount: Long,
  val processSkipCount: Long,
  val writeSkipCount: Long,
  val filterCount: Long,
  val skipCount: Long,
  val summary: String,
)
