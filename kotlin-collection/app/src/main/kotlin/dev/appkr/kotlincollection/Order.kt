package dev.appkr.kotlincollection

data class Order(
    val items: Collection<Item>
)

data class Item(
    val name: String,
    val price: Int
) : Comparable<Item> {
    override fun compareTo(other: Item): Int {
        val priceComparison = this.price.compareTo(other.price)
        if (priceComparison != 0) {
            return priceComparison
        }
        return this.name.compareTo(other.name)
    }
}
