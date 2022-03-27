enum class Gender {
    MALE,
    FEMALE
}

interface LivingObject {
    fun liveOneDay()
}

abstract class Human(val name: String, var age: Int, val gender: Gender) : LivingObject {
    open fun describe(): String {
        return when (gender) {
            Gender.MALE -> "MALE"
            Gender.FEMALE -> "..."
        }
    }

    override fun toString(): String {
        return "[Name = $name, Age=$age]"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Human) {
            return this.name == other.name && this.age == other.age
        }
        return false
    }

    override fun hashCode(): Int {
        return this.name.hashCode() + this.age
    }
}

@JvmInline
value class StudentId(val id: Int)

class Student(val id: StudentId, var score: Int, name: String, age: Int = 0, gender: Gender) :
    Human(name, age, gender) {
    fun passExam(delta: Int) {
        score += delta
    }

    operator fun component1(): String {
        return name
    }

    override fun describe(): String {
        return "Student"
    }

    override fun toString(): String {
        return "Student[Name = $name, Age=$age]"
    }

    override fun liveOneDay() {
        TODO("Not yet implemented")
    }
}

data class Grade(var grade: Int, val student: Student) {
    fun changeGrade(delta: Int) {
        grade += delta
    }
}

fun main() {
    val human = Student(id = StudentId(10), score = 10, name = "Ivan", gender = Gender.MALE)
    val human2 = Student(age = 10, name = "Ivan", score = 23, gender = Gender.FEMALE, id = StudentId(20))
    val (name) = human
    println("NAME=$name")

    val grade1 = Grade(10, human)
    val grade2 = Grade(10, human2)

    val gradeCopy = grade1.copy(grade = 20)
    println("Grade=${grade1.component1()}")
    val (grade, student) = grade1
    println(grade)
    println(student)

    println(grade1)
    println(grade1 == grade2)

    val map = mapOf(human to "phone1", human2 to "phone2")
    println(map)
    when (val data = loadData()) {
        is APIResult.Error -> print("Error ${data.error}")
        is APIResult.Loading -> print("Loading")
        is APIResult.Success -> print("Data ${data.data}")
    }
}

fun loadData(): APIResult {
    return APIResult.Error("Something went wrong")
}

sealed class APIResult {
    object Loading : APIResult()

    data class Success(val data: String) : APIResult()

    data class Error(val error: String) : APIResult()
}

