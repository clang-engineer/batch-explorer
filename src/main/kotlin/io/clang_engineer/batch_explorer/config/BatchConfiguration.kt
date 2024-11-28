package io.clang_engineer.batch_explorer.config

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DataSourceInitializer
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class BatchConfiguration {
  @Bean
  fun batchSchemaInitializer(dataSource: DataSource): DataSourceInitializer {
    val initializer = DataSourceInitializer()
    initializer.setDataSource(dataSource)

    val populator = ResourceDatabasePopulator()
    populator.addScript(ClassPathResource("org/springframework/batch/core/schema-h2.sql"))

    initializer.setDatabasePopulator(populator)
    return initializer
  }
}