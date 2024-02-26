package dev.appkr.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.system.measureTimeMillis

class ForkJoinCoroutines {
    @Test
    fun withoutCoroutine() {
        val elapsedMillis = measureTimeMillis {
            val unorderedResults = buildList<Int> {
                for (i in 0..1000) {
                    println(i)
                    if (i % 2 == 0) add(i)
                }
            }

            println(unorderedResults)
        }

        println("elapsedMillis=$elapsedMillis") // 32ms, 10ms, 11ms, 11ms, 11ms
    }

    @Test
    fun usingLaunchAndConcurrentLinkedQueue() {
        val elapsedMilles = measureTimeMillis {
            val unorderedResults = ConcurrentLinkedQueue<Int>()
            runBlocking {
                for (i in 0..1000) {
                    launch {
                        println(i)
                        if (i % 2 == 0) unorderedResults.add(i * i)
                    }
                }
            }

            println(unorderedResults)
        }

        println("elapsedMillis=$elapsedMilles") // 66ms, 64ms, 61ms, 63ms, 65ms
    }

    @Test
    fun usingAsync() {
        val elapsedMillis = measureTimeMillis {
            val unorderedResults = runBlocking {
                (0..1000).map { i ->
                    async {
                        println(i)
                        if (i % 2 == 0) i * i else null
                    }
                }.awaitAll().filterNotNull()
            }

            println(unorderedResults)
        }

        println("elapsedMillis=$elapsedMillis") // 68ms, 16ms, 16ms, 14ms, 14ms
    }

    @Test
    fun usingChannelFlow() {
        val elapsedMillis = measureTimeMillis {
            val unorderedResults = runBlocking(Dispatchers.Default) {
                channelFlow {
                    for (i in 0..1000) {
                        launch {
                            println(i)
                            if (i % 2 == 0) send(i)
                        }
                    }
                }.toList()
            }

            println(unorderedResults)
        }

        println("elapsedMillis=$elapsedMillis") // 76ms, 36ms, 37ms, 36ms, 31ms
    }

    fun <T> println(msg: T) {
        kotlin.io.println("${msg} [${Thread.currentThread().name}]")
    }
}
