package io.clang_engineer.batch_explorer.service

import io.clang_engineer.batch_explorer.job.QuartzJob
import org.quartz.JobDataMap
import org.quartz.Scheduler
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
class QuartzSchedulerService(private val scheduler: Scheduler) {

    private val jobCounter = AtomicInteger(0)

    private val triggerCounter = AtomicInteger(0)

    fun scheduleBatchJobExecution() {
        val jobDetail = org.quartz.JobBuilder.newJob(QuartzJob::class.java)
                .withIdentity("${jobCounter.incrementAndGet()}", "jobs")
                .usingJobData(JobDataMap().apply {
                    put("paramaA", "valueA")
                    put("paramaB", "valueB")
                })
                .storeDurably()
                .build()

        val trigger = org.quartz.TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("${triggerCounter.incrementAndGet()}", "triggers")
                .withSchedule(org.quartz.CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build()

        scheduler.scheduleJob(jobDetail, trigger)
    }
}