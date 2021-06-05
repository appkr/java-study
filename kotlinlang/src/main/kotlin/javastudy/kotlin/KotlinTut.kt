package javastudy.kotlin

fun main(args: Array<String>) {
    println("Hello world");

    println("Variable" + "-".repeat(40))

    // Variables
    val name = "appkr"
    var myAge = 47
    var bigInt: Int = Int.MAX_VALUE
    var smallInt: Int = Int.MIN_VALUE
    println("Biggest int: ${bigInt}")
    println("Smallest int: ${smallInt}")

    var dblNum1: Double = 1.11111111111111111
    var dblNum2: Double = 1.11111111111111111
    println("Sum: ${dblNum1 + dblNum2} ")

    // Instance of
    if (true is Boolean) {
        print("true is boolean\n")
    }

    var letterGrade: Char = 'A'
    println("A is a char: ${letterGrade is Char}")

    // Casting
    println("3.14 to Int: ${3.14.toInt()}")
    println("A to Int: ${'A'.toInt()}")
    println("65 to Char: ${65.toChar()}")

    println("String" + "-".repeat(40))

    // String
    val longStr = """Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
Morbi semper diam vitae eros scelerisque, eget tincidunt odio bibendum
    """.trimMargin()
    println("Length of longStr: " + longStr.length)

    val str1 = "A random string"
    val str2 = "a random string"
    println("String equality: " + str1.equals(str2));
    println("String comparison: "  + str2.compareTo(str1));
    println("2nd index: " + str1.get(2));
    println("2nd index: " + str1[2]);
    println("Index 2~7:"  + str1.subSequence(2, 8))
    println("Contains random?: " + str1.contains("random"))

    println("Array" + "-".repeat(40))

    // Array
    var myArray = arrayOf(1, 1.23, "Doug")
    println(myArray[2])
    myArray[1] = 3.14
    println("myArray length: " + myArray.size)
    println("Doug in myArray? " + myArray.contains("Doug"))
    var partOfArray = myArray.copyOfRange(0, 2)
    partOfArray.forEach { e -> println(e) }
    println("First element: ${partOfArray.first()}")
    println("Doug's index: ${myArray.indexOf("Doug")}")

    println("Range" + "-".repeat(40))

    var sqArray = Array<Int>(5, {x -> x * x})
    sqArray.forEach { e -> println(e) }

    // Ranges
    val oneToTen = 1..10
    val alpha = "A".."Z"
    println("R in alpha?: " + alpha.contains("R"))
    println("R in alpha?: " + "R" in alpha)

    val tenToOne = 10.downTo(1)
    val twoToTwenty = 2.rangeTo(20)
    val rangeThree = oneToTen.step(3)
    println(rangeThree)
    for (x in rangeThree) {
        println(x)
    }
    for (x in tenToOne.reversed()) {
        println(x)
    }
}
