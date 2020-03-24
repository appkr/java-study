package javastudy.kotlin

fun main() {
    val rectangle = Rectangle(5.0, 2.0)
    val triangle = Triangle(3.0, 4.0, 5.0)
    println("Area of rectangle is ${rectangle.calculateArea()}, it perimeter is ${rectangle.perimeter}")
    println("Area of triangle is ${triangle.calculateArea()}, it perimeter is ${triangle.perimeter}")
}