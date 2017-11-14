package tsybl.mindgames.data

import io.reactivex.Flowable
import tsybl.mindgames.entities.ComputingTask
import java.util.*

class ComputationRepository(private val numbersGenerator: NumbersGenerator) : ComputingDataSource {

    override fun getTask(previousTask: ComputingTask): Flowable<ComputingTask> {
        return Flowable.just(generate(previousTask))
    }


    private fun generate(task: ComputingTask): ComputingTask {
        //        //int type = 1;
        //        switch (type) {
        //            case 1:
        //                addition(task, level, random);
        //                break;
        //            case 2:
        //                multiplication(task, level, random);
        //                break;
        //        }
        return if (numbersGenerator.getTaskType())
            multiplication(task)
        else
            addition(task)

    }

    private fun addition(task: ComputingTask): ComputingTask {
        task.isRight = numbersGenerator.isRightTask()
        val answer = numbersGenerator.getAdditionAnswer()
        val first = numbersGenerator.getAdditionFirstNumber(answer)
        val second = answer - first
        val answerArray = if (task.isRight) arrayOf(first, second, answer) else numbersGenerator.generateAdditionWrong(first, second, answer)
        task.question = generateAnswerString(answerArray, ComputingType.ADDITION)
        return task
    }

    private fun subtraction(task: ComputingTask, level: Int, random: Random) {}

    private fun multiplication(task: ComputingTask): ComputingTask {
        task.isRight = numbersGenerator.isRightTask()
        val first = numbersGenerator.getMultiplicationTerm()
        val second = numbersGenerator.getMultiplicationTerm()
        val answer = first * second
        val answerArray = if (task.isRight) arrayOf(first, second, answer) else numbersGenerator.generateMultiplicationWrong(first, second, answer)
        task.question = generateAnswerString(answerArray, ComputingType.MULTIPLICATION)
        return task
    }

    private fun division(task: ComputingTask, level: Int, random: Random) {}

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

    fun getTaskType() = random.nextBoolean()

    fun getAdditionAnswer() = random.nextInt(99) + 1

    fun getAdditionFirstNumber(answer: Int) = random.nextInt(answer - 1) + 1

    fun isRightTask() = random.nextBoolean()

    fun generateAdditionWrong(vararg args: Int): Array<Int> {
        val position = random.nextInt(2)
        args[position] = getNext(random, args[position], 10)
        return arrayOf(args[0], args[1], args[2])
    }

    fun getMultiplicationTerm() = random.nextInt(9) + 1

    fun generateMultiplicationWrong(vararg args: Int): Array<Int> {
        val position = random.nextInt(2)
        if (position == 2)
            args[position] = getNext(random, args[position], 2)
        else
            args[position] = getNext(random, args[position], 10)
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


}
