package io.clang_engineer.batch_explorer.web

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobInstance
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping("/api")
class BatchResource(
        private val jobExplorer: JobExplorer,
        private val jobRegistry: JobRegistry
) {
    private val log = org.slf4j.LoggerFactory.getLogger(javaClass)

    @GetMapping("/job/instances")
    fun fetchAllJobs(): ResponseEntity<List<JobInstance>> {
        log.info("Listing all jobs")

        val jobInstances = jobExplorer.findJobInstancesByJobName("batch-explorer-job", 0, 100)
        return ResponseEntity.ok(jobInstances)
    }

    @GetMapping("/job/executions")
    fun fetchAllJobExecutions(): ResponseEntity<MutableSet<JobExecution>> {
        log.info("Listing all job executions")

        val jobInstances = jobExplorer.getJobInstances("batch-explorer-job", 0, 100)

        val allJobExecutions = jobInstances.stream()
                .flatMap({ jobInstance -> jobExplorer.getJobExecutions(jobInstance).stream() })
                .collect(Collectors.toSet())

        return ResponseEntity.ok(allJobExecutions)
    }

    @GetMapping("/job/steps")
    fun fetchAllJobSteps(): ResponseEntity<List<org.springframework.batch.core.StepExecution>> {
        log.info("Listing all job steps")

        return ResponseEntity.ok().build()
    }

}