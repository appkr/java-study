package javastudy.kotlin

class Rectangle(
    private var height: Double,
    private var length: Double
) : Shape(listOf(height, length, height, length)), RectangleProperties {

    override val isSquare: Boolean get() = length == height

    override fun calculateArea(): Double {
        return height * length
    }
}