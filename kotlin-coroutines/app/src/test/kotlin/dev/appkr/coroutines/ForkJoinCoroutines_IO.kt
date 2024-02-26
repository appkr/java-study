package dev.appkr.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.system.measureTimeMillis

class ForkJoinCoroutines_IO {
    val dispatcher = Dispatchers.IO

    @Test
    fun withoutCoroutine() {
        val elapsedMillis = measureTimeMillis {
            val unorderedResults = buildList {
                (0..1000).forEach {
                    Thread.sleep(10)
                    if (it % 2 == 0) add(it)
                }
            }

            println(unorderedResults.size)
        }

        println("elapsedMillis=$elapsedMillis")
    }

    @Test
    fun usingLaunchAndConcurrentLinkedQueue() {
        val elapsedMillis = measureTimeMillis {
            val unorderedResults = ConcurrentLinkedQueue<Int>()
            runBlocking(dispatcher) {
                (0..1000).forEach {
                    launch {
                        delay(10)
                        if (it % 2 == 0) unorderedResults.add(it * it)
                    }
                }
            }

            println(unorderedResults.size)
        }

        println("elapsedMillis=$elapsedMillis")
    }

    @Test
    fun usingAsync() {
        val elapsedMillis = measureTimeMillis {
            val unorderedResults = runBlocking(dispatcher) {
                (0..1000).map { i ->
                    async {
                        delay(10)
                        if (i % 2 == 0) i * i else null
                    }
                }.awaitAll().filterNotNull()
            }

            println(unorderedResults.size)
        }

        println("elapsedMillis=$elapsedMillis")
    }

    @Test
    fun usingChannelFlow() {
        val elapsedMillis = measureTimeMillis {
            val unorderedResults = runBlocking(dispatcher) {
                channelFlow {
                    (0..1000).forEach {
                        launch {
                            delay(10)
                            if (it % 2 == 0) send(it)
                        }
                    }
                }.toList()
            }

            println(unorderedResults.size)
        }

        println("elapsedMillis=$elapsedMillis")
    }

    fun <T> println(msg: T) {
        kotlin.io.println("${msg} [${Thread.currentThread().name}]")
    }
}
