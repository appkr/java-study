package dev.appkr.kotlincollection

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class CollectionTest {
    private val apple = Item(name = "apple", price = 5_000)
    private val avocado = Item(name = "avocado", price = 30_000)
    private val banana = Item(name = "banana", price = 29_000)
    private val cherry = Item(name = "cherry", price = 5_000)
    private val order = Order(listOf(apple, avocado, banana, cherry))
    private val emptyOrder = Order(emptyList())

    @Test
    fun filter() {
//         val actual = order.items.filter { item -> item.price > 10_000 }
        val actual = order.items.filter { it.price > 10_000 }
        assertEquals(listOf(avocado, banana), actual)
    }

    @Test
    fun any() {
        val actual = order.items.any { it.price > 10_000 }
        assertTrue(actual)
    }

    @Test
    fun none() {
        val actual = order.items.none { it.price > 10_000 }
        assertFalse(actual)
    }

    @Test
    fun all() {
        val actual = order.items.all { it.price > 100 }
        assertTrue(actual)
    }

    @Test
    fun find() {
        var actual = order.items.find { it.name.startsWith("a") } ?: emptyList<Item>()
        assertEquals(apple, actual)

        val actual2 = order.items.find { it.name.startsWith("d") }
        assertNull(actual2)
    }

    @Test
    fun findLast() {
        val actual = order.items.findLast { it.name.startsWith("a") } ?: emptyList<Item>()
        assertEquals(avocado, actual)
    }

    @Test
    fun first() {
        val actual = order.items.first { it.name.startsWith("a") }
        assertEquals(apple, actual)

        assertThrows<NoSuchElementException> {
            order.items.first { it.name.startsWith("d") }
        }
    }

    @Test
    fun firstOrNull() {
        val actual = order.items.firstOrNull { it.name.startsWith("d") }
        assertNull(actual)
    }

    @Test
    fun last() {
        val actual = order.items.last { it.name.startsWith("a") }
        assertEquals(avocado, actual)

        assertThrows<NoSuchElementException> {
            order.items.last { it.name.startsWith("d") }
        }
    }

    @Test
    fun lastOrNull() {
        val actual = order.items.lastOrNull() { it.name.startsWith("d") }
        assertNull(actual)
    }

    @Test
    fun count() {
        assertEquals(4, order.items.count())

        val actual = order.items.count { it.name.startsWith("a") }
        assertEquals(2, actual)
    }

    @Test
    fun map() {
        val actual = order.items.map { it.name }.toSet()
        assertEquals(setOf("apple", "avocado", "banana", "cherry"), actual)
    }

    @Test
    fun associateBy() {
        val map = order.items.associateBy { it.name }
        assertTrue { map is Map<String, Item> }
        assertEquals(setOf("apple", "avocado", "banana", "cherry"), map.keys)
    }

    @Test
    fun associateByOverwriteWithLastItem() {
        // 주의: 키가 같으면 마지막 값만 유지된다
        val actual = order.items.associateBy(Item::price, Item::name)
        val expected = mapOf(
            5_000 to "cherry",
            30_000 to "avocado",
            29_000 to "banana"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun associateByWithKeySelector() {
        val actual: Map<String, Int> = order.items.associateBy(Item::name, Item::price)
        val expected = mapOf(
            "apple" to 5_000,
            "avocado" to 30_000,
            "banana" to 29_000,
            "cherry" to 5_000,
        )
        assertEquals(expected, actual)
    }

    @Test
    fun groupBy() {
        val group: Map<Char, Collection<Item>> = order.items.groupBy { it.name[0] }
        assertEquals(setOf('a', 'b', 'c'), group.keys)
    }

    @Test
    fun partition() {
        // true first, false second
        val (expensive, cheap) = order.items.partition { it.price > 10_000 }
        assertEquals(setOf(apple, cherry), cheap.toSet())
        assertEquals(setOf(banana, avocado), expensive.toSet())
    }

    @Test
    fun flatMap() {
        val (expensive, cheap) = order.items.partition { it.price > 10_000 }
        val cart = listOf(expensive, cheap)
        assertEquals(2, cart.size)

        val flatten = cart.flatMap { it }.toSet()
        assertEquals(order.items.toSet(), flatten)
    }

    @Test
    fun maxBy() {
        val actual = order.items.maxBy { it.price }
        assertEquals(avocado, actual)
    }

    @Test
    fun maxByOrNull() {
        val actual = emptyOrder.items.maxByOrNull { it.price }
        assertNull(actual)
    }

    @Test
    fun sort() {
        val shuffled = order.items.shuffled()
        val sorted = shuffled.sorted()
        assertEquals(apple, sorted.first())
    }

    @Test
    fun sortedDescending() {
        val shuffled = order.items.shuffled()
        val sorted = shuffled.sortedDescending()
        assertEquals(avocado, sorted.first())
    }

    @Test
    fun sortedBy() {
        val shuffled = order.items.shuffled()
        val sorted = shuffled.sortedBy { it.name }
        assertEquals(cherry, sorted.last())
    }

    @Test
    fun sortedByDescending() {
        val shuffled = order.items.shuffled()
        val sorted = shuffled.sortedByDescending { it.name }
        assertEquals(cherry, sorted.first())
    }
}
