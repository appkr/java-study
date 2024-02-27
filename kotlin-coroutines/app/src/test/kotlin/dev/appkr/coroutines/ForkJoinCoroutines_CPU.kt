package dev.appkr.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.system.measureTimeMillis

class ForkJoinCoroutines_CPU {
    val dispatcher = newFixedThreadPoolContext(Runtime.getRuntime().availableProcessors(), "test-pool")
    val range = (0..1_000_000)

    @Test
    fun withoutCoroutine() {
        val elapsedMillis = measureTimeMillis {
            val result = range
                .filter { isPrime(it) }
                .toList()

            println(result.size)
        }

        println("elapsedMillis=$elapsedMillis")
    }

    @Test
    fun usingLaunchAndConcurrentLinkedQueue() {
        val elapsedMilles = measureTimeMillis {
            val unorderedResults = ConcurrentLinkedQueue<Int>()
            runBlocking(dispatcher) {
                range
                    .chunked(1_000)
                    .forEach {
                        launch {
                            unorderedResults.addAll(
                                it.filter { n -> isPrime(n) }.toList()
                            )
                        }
                    }
            }

            println(unorderedResults.size)
        }

        println("elapsedMillis=$elapsedMilles")
    }

    @Test
    fun usingAsync() {
        val elapsedMillis = measureTimeMillis {
            val unorderedResults = runBlocking(dispatcher) {
                val deferred = range
                    .chunked(1_000)
                    .map {
                        async {
                            it.filter { n -> isPrime(n) }
                        }
                    }
                deferred.flatMap { it.await() }
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
                    range
                        .chunked(1_000)
                        .forEach {
                            launch {
                                send(it.filter { n -> isPrime(n) }.toList())
                            }
                        }
                }.toList()
            }.flatten()

            println(unorderedResults.size)
        }

        println("elapsedMillis=$elapsedMillis")
    }

    fun <T> println(msg: T) {
        kotlin.io.println("${msg} [${Thread.currentThread().name}]")
    }

    fun isPrime(number: Int): Boolean {
        if (number <= 1) return false
        for (i in 2 until number) {
            if (number % i == 0) return false
        }
        return true
    }
}
