import kotlin.properties.Delegates
import kotlin.reflect.KProperty

enum class AgeRange {
    JUNIOR,
    MIDDLE,
    SENIOR
}

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

data class TestClass(var age: Int) {

    operator fun unaryPlus(): String {
        return "TESTCLASS: ${this.age}"
    }

    operator fun plus(other: TestClass): TestClass {
        return TestClass(this.age + other.age)
    }

    val lazyValue by lazy {
        println("LAZY INIT")
        "LAZY"
    }

    var simpleDelegate by Delegate()

    var delegateProperty: String by Delegates.vetoable(
        initialValue = "Hello",
        onChange = { property, oldValue, newValue ->
            !newValue.isNotEmpty()
        })

    var value: Int = 10
        get() {
            return field * 2
        }
        set(a: Int) {
            if (a > 0) {
                println("Value is set to ${a - 3}")
                field = a - 3
            }
        }

    var classification: AgeRange
        get() {
            return when {
                age < 10 -> AgeRange.JUNIOR
                age > 60 -> AgeRange.SENIOR
                else -> AgeRange.MIDDLE
            }
        }
        set(range: AgeRange) {
            when (range) {
                AgeRange.JUNIOR -> age = 5
            }
        }
}

fun main() {
    val a = TestClass(5)
    val b = TestClass(8)
    println(a + b)
    println(+a)
    a.value = 8
    println(a.value)
    println(a.delegateProperty)
    a.delegateProperty = "1"
    println(a.delegateProperty)
    println(a.lazyValue)
    println(a.lazyValue)
    a.classification = AgeRange.JUNIOR
    println(a.classification)
    println(a.age)
}