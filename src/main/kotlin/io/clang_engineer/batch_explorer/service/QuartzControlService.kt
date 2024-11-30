package io.clang_engineer.batch_explorer.service

import org.quartz.Scheduler
import org.springframework.stereotype.Service

@Service
class QuartzControlService(private val scheduler: Scheduler) {
    private final val log = org.slf4j.LoggerFactory.getLogger(QuartzSchedulerService::class.java)

    fun listJobs() {
        log.info("Listing all jobs")

        val jobKeys = scheduler.getJobKeys(org.quartz.impl.matchers.GroupMatcher.anyJobGroup())
        jobKeys.forEach { jobKey ->
            val jobDetail = scheduler.getJobDetail(jobKey)
            println("Job: ${jobDetail.key}")
        }
    }

    fun pauseJob(jobKey: org.quartz.JobKey) {
        log.info("Pausing job: $jobKey")

        scheduler.pauseJob(jobKey)
    }

    fun resumeJob(jobKey: org.quartz.JobKey) {
        log.info("Resuming job: $jobKey")

        scheduler.resumeJob(jobKey)
    }

    fun pauseTrigger(triggerKey: org.quartz.TriggerKey) {
        log.info("Pausing trigger: $triggerKey")

        scheduler.pauseTrigger(triggerKey)
    }

    fun resumeTrigger(triggerKey: org.quartz.TriggerKey) {
        log.info("Resuming trigger: $triggerKey")

        scheduler.resumeTrigger(triggerKey)
    }
}