fun main() {
    println("Hello World!")

    var nullable = null
    val a = nullable?.toDouble()
    val b = a ?: 4.5
    val c = nullable ?: 5

    fun hello() = print("Hello")
    hello()
}
