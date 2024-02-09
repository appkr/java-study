package dev.appkr.flowapi

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class FlowBuilderTest {
    @Test
    fun flowOf() {
        val flow = flowOf(1, 2, 3)
            .onEach { delay(500) }

        runBlocking {
            flow.collect {
                println(it)
            }
        }
    }

    @Test
    fun collectionFlow() {
        val flow = listOf(1, 2, 3)
            .asFlow()
            .onEach { delay(500) }

        runBlocking {
            flow.collect {
                println(it)
            }
        }
    }

    @Test
    fun lambdaFlow() {
        val flow = flow {
            delay(500)
            emit(1)
            delay(500)
            emit(2)
            delay(500)
            emit(3)
        }

        runBlocking {
            flow.collect {
                println(it)
            }
        }
    }

    @Test
    fun channelFlow() {
        val flow = channelFlow {
            delay(500)
            send(1)
            delay(500)
            send(2)
            delay(500)
            send(3)
        }

        runBlocking {
            flow.collect {
                println(it)
            }
        }
    }
}
