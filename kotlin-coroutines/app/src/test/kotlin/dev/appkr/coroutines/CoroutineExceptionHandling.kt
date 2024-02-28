package dev.appkr.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class CoroutineExceptionHandling {
    @Test
    fun exceptionInAsync() {
        runBlocking {
            val results = (0..10).mapNotNull {
                async {
                    try {
                        if (it != 0) it else throw RuntimeException("STOP")
                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                        null
                    }
                }
            }.awaitAll()

            println(results)
        }
    }
}
