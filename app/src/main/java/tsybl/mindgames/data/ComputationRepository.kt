package tsybl.mindgames.data

import io.reactivex.Flowable
import tsybl.mindgames.entities.ComputingTask
import java.util.*

class ComputationRepository(private val numbersGenerator: NumbersGenerator) : ComputingDataSource {

    override fun getTask(previousTask: ComputingTask): Flowable<ComputingTask> {
        return Flowable.just(generate(previousTask))
    }


    private fun generate(task: ComputingTask): ComputingTask {
        return when (numbersGenerator.getTaskType()) {
            0 -> addition(task)
            1 -> multiplication(task)
            2 -> division(task)
            else -> {
                addition(task)
            }
        }


    }

    private fun addition(task: ComputingTask): ComputingTask {
        task.isRight = numbersGenerator.isRightTask()
        val answer = numbersGenerator.getAdditionAnswer()
        val first = numbersGenerator.getAdditionFirstNumber(answer)
        val second = answer - first
        val answerArray = if (task.isRight) arrayOf(first, second, answer) else numbersGenerator.generateAdditionWrong(first, second, answer)
        task.first = answerArray[0]
        task.second = answerArray[1]
        task.answer = answerArray[2]
        task.type = ComputingType.ADDITION
        task.question = generateAnswerString(answerArray, ComputingType.ADDITION)
        return task
    }

    private fun subtraction(task: ComputingTask) {


    }

    private fun multiplication(task: ComputingTask): ComputingTask {
        task.isRight = numbersGenerator.isRightTask()
        val first = numbersGenerator.getMultiplicationTerm()
        val second = numbersGenerator.getMultiplicationTerm()
        val answer = first * second
        val answerArray = if (task.isRight) arrayOf(first, second, answer) else numbersGenerator.generateMultiplicationWrong(first, second, answer)
        task.first = answerArray[0]
        task.second = answerArray[1]
        task.answer = answerArray[2]
        task.type = ComputingType.MULTIPLICATION
        task.question = generateAnswerString(answerArray, ComputingType.MULTIPLICATION)
        return task
    }

    private fun division(task: ComputingTask): ComputingTask {
        task.isRight = numbersGenerator.isRightTask()
        val first = numbersGenerator.getMultiplicationTerm()
        val second = numbersGenerator.getMultiplicationTerm()
        val answer = first * second
        val answerArray = if (task.isRight) arrayOf(first, second, answer) else numbersGenerator.generateMultiplicationWrong(first, second, answer)
        val tmp = answerArray[2]
        answerArray[2] = answerArray[0]
        answerArray[0] = tmp
        task.first = answerArray[0]
        task.second = answerArray[1]
        task.answer = answerArray[2]
        task.type = ComputingType.DIVISION
        task.question = generateAnswerString(answerArray, ComputingType.DIVISION)
        return task

    }

    private fun generateAnswerString(answerArray: Array<Int>, type: ComputingType): String {
        return answerArray[0].toString() + " " + type.text + " " + answerArray[1].toString() + " = " + answerArray[2].toString()
    }


}

enum class ComputingType constructor(val text: String) {
    ADDITION("+"), SUBTRACTION("-"), MULTIPLICATION("*"), DIVISION("/");

    override fun toString(): String {
        return text
    }
}

class NumbersGenerator(private val level: Int) {
    private val random = Random()

    fun getTaskType() = random.nextInt(3)

    fun getAdditionAnswer() = random.nextInt(90 + level * 10) + 2

    fun getAdditionFirstNumber(answer: Int) = random.nextInt(answer) + 1

    fun isRightTask() = random.nextBoolean()

    fun generateAdditionWrong(vararg args: Int): Array<Int> {
        val position = random.nextInt(2)
        while (checkAdditionRight(args[0], args[1], args[2])) {
            args[position] = getNext(random, args[position], 10 + level)
        }
        return arrayOf(args[0], args[1], args[2])
    }

    fun getMultiplicationTerm() = random.nextInt(8 + level) + 1

    fun generateMultiplicationWrong(vararg args: Int): Array<Int> {
        val position = random.nextInt(2)
        while (checkMultiplicationRight(args[0], args[1], args[2])) {
            if (position == 2)
                args[position] = getNext(random, args[position], 2)
            else
                args[position] = getNext(random, args[position], 4 + level)
        }
        return arrayOf(args[0], args[1], args[2])
    }

    private fun getNext(random: Random, value: Int, bounds: Int): Int {
        val max = value + bounds
        var min = value - bounds
        if (min < 0)
            min = 0
        val newValue = random.nextInt(max - min) + min
        if (newValue == value)
            getNext(random, value, bounds)
        return newValue
    }

    private fun checkAdditionRight(vararg args: Int): Boolean {
        return args[0] + args[1] == args[2]
    }

    private fun checkMultiplicationRight(vararg args: Int): Boolean {
        return args[0] + args[1] == args[2]
    }
}
