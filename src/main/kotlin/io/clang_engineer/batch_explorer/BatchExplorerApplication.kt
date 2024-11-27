package io.clang_engineer.batch_explorer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BatchExplorerApplication

fun main(args: Array<String>) {
	runApplication<BatchExplorerApplication>(*args)
}
