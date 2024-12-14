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
    // quartz를 사용해서 주기적으로 같은 batch job instance를 실행하기 위해 별도의 고유값을 추가한다.
    // spring batch에서 job instance는 job name과 job parameters로 구분되기 때문에 job parameters에 timestamp를 추가한다.
    jobParametersBuilder.addLong("timestamp", System.currentTimeMillis())

    return jobParametersBuilder.toJobParameters()
}

