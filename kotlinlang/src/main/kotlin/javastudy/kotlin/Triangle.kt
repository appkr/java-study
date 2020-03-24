package javastudy.kotlin

class Triangle(
        private var sideA: Double,
        private var sideB: Double,
        private var sideC: Double
) : Shape(listOf(sideA, sideB, sideC)) {

    override fun calculateArea(): Double {
        val s = perimeter / 2

        // Herons formular
        // @see https://mathworld.wolfram.com/HeronsFormula.html
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC))
    }
}