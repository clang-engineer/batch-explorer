package io.clang_engineer.batch_explorer.job

fun transformQuartzJobDataMapToBatchJobParameters(jobDataMap: org.quartz.JobDataMap): org.springframework.batch.core.JobParameters {
    val jobParametersBuilder = org.springframework.batch.core.JobParametersBuilder()

    for (key in jobDataMap.keys) {
        val value = jobDataMap[key]
        when (value) {
            is String -> jobParametersBuilder.addString(key, value)
            is Long -> jobParametersBuilder.addLong(key, value)
            is Int -> jobParametersBuilder.addLong(key, value.toLong())
            is Double -> jobParametersBuilder.addDouble(key, value)
            is Float -> jobParametersBuilder.addDouble(key, value.toDouble())
            else -> jobParametersBuilder.addString(key, value.toString())
        }
    }

    // Add timestamp to execute unique batch job
    jobParametersBuilder.addLong("timestamp", System.currentTimeMillis())

    return jobParametersBuilder.toJobParameters()
}

