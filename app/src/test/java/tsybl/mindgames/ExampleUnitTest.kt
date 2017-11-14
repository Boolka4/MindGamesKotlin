package tsybl.mindgames

import org.junit.Test

import org.junit.Assert.*
import tsybl.mindgames.data.ComputationRepository
import tsybl.mindgames.data.ComputingType
import tsybl.mindgames.data.NumbersGenerator
import tsybl.mindgames.entities.ComputingTask

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun addition() {
        System.out.println("Start Test");
        var computingTask = ComputingTask("q", true)
        val rep = ComputationRepository(NumbersGenerator(1))
        for (i in 1..5000) {
            rep.getTask(computingTask).subscribe { task ->
                computingTask = task
                when (task.type) {
                    ComputingType.MULTIPLICATION -> {
                        System.out.println("MULTIPLICATION");
                        if (task.isRight) {
                            System.out.println("IsRight " + task.first + "*" + task.second + "=" + task.answer);
                            assertEquals(task.answer, task.first * task.second)
                        } else {
                            System.out.println("!IsRight " + task.first + "*" + task.second + "=" + task.answer);
                            assertNotEquals(task.answer, task.first * task.second)
                        }
                    }
                    ComputingType.ADDITION -> {
                        System.out.println("ADDITION");
                        if (task.isRight) {
                            System.out.println("IsRight " + task.first + "+" + task.second + "=" + task.answer);
                            assertEquals(task.answer, task.first + task.second)
                        } else {
                            System.out.println("!IsRight " + task.first + "+" + task.second + "=" + task.answer);
                            assertNotEquals(task.answer, task.first + task.second)
                        }
                    }
                    ComputingType.SUBTRACTION -> TODO()
                    ComputingType.DIVISION -> TODO()
                }
            }
        }
    }
}
