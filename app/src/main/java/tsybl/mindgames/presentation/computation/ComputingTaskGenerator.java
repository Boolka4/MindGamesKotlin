package tsybl.mindgames.presentation.computation;

import java.util.Random;

import tsybl.mindgames.entities.ComputingTask;

/**
 * Created by Boolka4 on 12.11.2017.
 */

public class ComputingTaskGenerator {

    public static void generate(ComputingTask task, int level, Random random) {
        int type = random.nextInt(1) + 1;
//        //int type = 1;
//        switch (type) {
//            case 1:
//                addition(task, level, random);
//                break;
//            case 2:
//                multiplication(task, level, random);
//                break;
//        }
        if (random.nextBoolean())
            multiplication(task, level, random);
        else
            addition(task, level, random);
    }

    private static void addition(ComputingTask task, int level, Random random) {
        task.setRight(random.nextBoolean());
        int answer = random.nextInt(99) + 1;
        int position = random.nextInt(2);
        int first = random.nextInt(answer - 1) + 1;
        int second = answer - first;
        String[] answerArray = {String.valueOf(first), String.valueOf(second), String.valueOf(answer)};
        if (!task.isRight())
            answerArray[position] = String.valueOf(getNext(random, Integer.valueOf(answerArray[position]), 10));
        task.setQuestion(generateAnswerString(answerArray, 0));
    }

    private static void subtraction(ComputingTask task, int level, Random random) {
    }

    private static void multiplication(ComputingTask task, int level, Random random) {
        task.setRight(random.nextBoolean());
        int position = random.nextInt(2);
        task.setPosition(position);
        int first = random.nextInt(9) + 1;
        task.setFirst(first);
        int second = random.nextInt(9) + 1;
        task.setSecond(second);
        int answer = first * second;
        task.setAnswer(answer);
        String[] answerArray = {String.valueOf(first), String.valueOf(second), String.valueOf(answer)};
        if (!task.isRight()) {
            if (position == 2)
                answerArray[position] = String.valueOf(getNext(random, Integer.valueOf(answerArray[position]), 2));
            else
                answerArray[position] = String.valueOf(getNext(random, Integer.valueOf(answerArray[position]), 10));
        }
        task.setChanged(answerArray[position]);
        task.setQuestion(generateAnswerString(answerArray, 2));
    }

    private static void division(ComputingTask task, int level, Random random) {
    }

    private static String generateAnswerString(String[] answerArray, int type) {
        return answerArray[0] + " " + ComputingType.values()[type].getText() + " " + answerArray[1] + " = " + answerArray[2];
    }

    private static int getNext(Random random, int value, int bounds) {
        int max = value + bounds;
        int min = value - bounds;
        if (min < 0)
            min = 0;
        int newValue = random.nextInt(max - min) + min;
        if (newValue == value)
            getNext(random, value, bounds);
        return newValue;
    }

}
