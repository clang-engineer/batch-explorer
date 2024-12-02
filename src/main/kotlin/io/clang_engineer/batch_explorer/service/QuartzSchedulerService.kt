package io.clang_engineer.batch_explorer.service

import io.clang_engineer.batch_explorer.job.QuartzJob
import org.quartz.JobDataMap
import org.quartz.Scheduler
import org.springframework.stereotype.Component
import java.util.*

@Component
class QuartzSchedulerService(private val scheduler: Scheduler) {
  fun scheduleBatchJobExecution(cronExpression: String, jobDataMap: Map<String, String>) {
    val jobDetail = org.quartz.JobBuilder.newJob(QuartzJob::class.java)
      .withIdentity("${UUID.randomUUID()}", "jobs")
      .usingJobData(JobDataMap(jobDataMap))
      .storeDurably()
      .build()

    val trigger = org.quartz.TriggerBuilder.newTrigger()
      .forJob(jobDetail)
      .withIdentity("${UUID.randomUUID()}", "triggers")
      .withSchedule(org.quartz.CronScheduleBuilder.cronSchedule(cronExpression))
      .build()

    scheduler.scheduleJob(jobDetail, trigger)
  }
}