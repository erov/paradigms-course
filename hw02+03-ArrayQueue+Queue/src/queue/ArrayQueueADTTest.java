package queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ArrayQueueADTTest {
    private static int testCounter = 0;
    private static int seed = 333;  // Enter seed for random
    private static final int randomTestCount = 1000;
    private static final ArrayQueueADT[] queueADT = new ArrayQueueADT[10];

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            queueADT[i] = ArrayQueueADT.create();
        }
        int i = 0;
        if (testHandMade()) {
            while (i++ < randomTestCount && testRandom()) {
                seed = seed * 2 + seed / 5;
            }
        }
    }

    private static boolean check(List<Object> result, List<Object> correct) {
        boolean equal = result.size() == correct.size();
        int i = 0;
        while (i < correct.size() && equal) {
            if (!result.get(i).equals(correct.get(i))) {
                equal = false;
                break;
            }
            i++;
        }
        System.out.printf("Test %d:", ++testCounter);
        if (!equal) {
            System.out.println(" incorrect :(");
            System.out.printf("Output:\n%s\nCorrect output:\n%s\nMismatch at position %d: result - '%s', correct - '%s'\n",
                    result.toString(), correct.toString(),
                    i, result.get(i).toString(), correct.get(i).toString());
            return false;
        } else {
            System.out.printf(" correct %d items\n", correct.size());
            return true;
        }
    }

    private static boolean testRandom() {
        Random rnd = new Random(seed * 4 + 5);
        int id = Math.abs(rnd.nextInt() % 10);
        ArrayQueueADT.clear(queueADT[id]);

        List<Object> result = new ArrayList<>();
        List<Object> correct = new ArrayList<>();
        List<Object> inputCopy = new ArrayList<>();

        boolean toArrayCheck = true, toStrCheck = true;
        for (int i = 0; i < randomTestCount; i++) {
            int dequeueCounter = rnd.nextInt() % 20 + 20;
            int removeCounter = rnd.nextInt() % 20 + 20;

            for (int j = 0; j < rnd.nextInt() % 50 + 50; j++) {
                Object e = rnd.nextInt() % 20;
                if (j % 3 > 0) {
                    // test enqueue(e)
                    inputCopy.add(e);
                    ArrayQueueADT.enqueue(queueADT[id], e);
                } else {
                    // test push(e)
                    inputCopy.add(0, e);
                    ArrayQueueADT.push(queueADT[id], e);
                }
            }

            // test size()
            result.add(ArrayQueueADT.size(queueADT[id]));
            correct.add(inputCopy.size());

            // test dequeue()
            while (inputCopy.size() > 0 && dequeueCounter-- > 0) {
                correct.add(inputCopy.get(0));
                inputCopy.remove(0);
                result.add(ArrayQueueADT.dequeue(queueADT[id]));
            }

            // test remove()
            while (inputCopy.size() > 0 && removeCounter-- > 0) {
                correct.add(inputCopy.get(inputCopy.size() - 1));
                inputCopy.remove(inputCopy.size() - 1);
                result.add(ArrayQueueADT.remove(queueADT[id]));
            }

            // test element()
            if (inputCopy.size() > 0) {
                result.add(ArrayQueueADT.element(queueADT[id]));
                correct.add(inputCopy.get(0));
            }

            // test peek()
            if (inputCopy.size() > 0) {
                result.add(ArrayQueueADT.peek(queueADT[id]));
                correct.add(inputCopy.get(inputCopy.size() - 1));
            }

            // test isEmpty()
            result.add(ArrayQueueADT.isEmpty(queueADT[id]));
            correct.add(inputCopy.isEmpty());

            // test.clear()
            if (Math.abs(rnd.nextInt() % 20) == i % 20) {
                ArrayQueueADT.clear(queueADT[id]);
                inputCopy.clear();
            }

            // test toArray()
            toArrayCheck = toArrayCheck & Arrays.equals(inputCopy.toArray(), ArrayQueueADT.toArray(queueADT[id]));

            // test toStr()
            toStrCheck = toStrCheck & inputCopy.toString().equals(ArrayQueueADT.toStr(queueADT[id]));
        }

        correct.add("toArray() correct");
        if (toArrayCheck) {
            result.add("toArray() correct");
        } else {
            result.add("toArray() incorrect");
        }

        correct.add("toStr() correct");
        if (toStrCheck) {
            result.add("toStr() correct");
        } else {
            result.add("toStr() incorrect");
        }


        return check(result, correct);
    }

    private static boolean testHandMade() {
        Random rnd = new Random(seed * 4 + 5);
        int id = Math.abs(rnd.nextInt() % 10);

        ArrayQueueADT.clear(queueADT[id]);
        List<Object> result = new ArrayList<>();

        result.add(ArrayQueueADT.isEmpty(queueADT[id]));  // [], R == true
        ArrayQueueADT.enqueue(queueADT[id],1);  // [1]
        ArrayQueueADT.enqueue(queueADT[id],2);  // [1, 2]
        ArrayQueueADT.enqueue(queueADT[id],"test");  // [1, 2, "test"]
        ArrayQueueADT.enqueue(queueADT[id],3);  // [1, 2, "test", 3]
        result.add(ArrayQueueADT.element(queueADT[id]));  // [1, 2, "test", 3], R == 1
        result.add(ArrayQueueADT.dequeue(queueADT[id]));  // [2, "test", 3], R == 1
        result.add(ArrayQueueADT.dequeue(queueADT[id]));  // ["test", 3], R == 2
        result.add(ArrayQueueADT.size(queueADT[id]));  // ["test", 3], R == 2
        ArrayQueueADT.enqueue(queueADT[id],4);  // ["test", 3, 4]
        ArrayQueueADT.enqueue(queueADT[id],5);  // ["test", 3, 4, 5]
        result.add(ArrayQueueADT.dequeue(queueADT[id]));  // [3, 4, 5], R == "test"
        result.add(ArrayQueueADT.element(queueADT[id]));  // [3, 4, 5], R == 3
        ArrayQueueADT.clear(queueADT[id]);  // []
        result.add(ArrayQueueADT.isEmpty(queueADT[id]));  // [], R == true
        ArrayQueueADT.enqueue(queueADT[id],1);  // [1]
        ArrayQueueADT.enqueue(queueADT[id],2);  // [1, 2]
        ArrayQueueADT.enqueue(queueADT[id],true);  // [1, 2, true]
        ArrayQueueADT.enqueue(queueADT[id],4);  // [1, 2, true, 4]
        ArrayQueueADT.enqueue(queueADT[id],"5");  // [1, 2, true, 4, "5"]
        result.add(ArrayQueueADT.element(queueADT[id]));  // [1, 2, true, 4, "5"], R == 1
        ArrayQueueADT.enqueue(queueADT[id],6);  // [1, 2, true, 4, "5", 6]
        ArrayQueueADT.enqueue(queueADT[id],"7");  // [1, 2, true, 4, "5", 6, "7"]
        ArrayQueueADT.enqueue(queueADT[id],8);  // [1, 2, true, 4, "5", 6, "7", 8]
        ArrayQueueADT.enqueue(queueADT[id],9);  // [1, 2, true, 4, "5", 6, "7", 8, 9]
        ArrayQueueADT.enqueue(queueADT[id],10);  // [1, 2, true, 4, "5", 6, "7", 8, 9, 10]
        result.add(ArrayQueueADT.dequeue(queueADT[id]));  // [2, true, 4, "5", 6, "7", 8, 9, 10], R == 1
        result.add(ArrayQueueADT.dequeue(queueADT[id]));  // [true, 4, "5", 6, "7", 8, 9, 10], R == 2
        result.add(ArrayQueueADT.dequeue(queueADT[id]));  // [4, "5", 6, "7", 8, 9, 10], R == true
        result.add(ArrayQueueADT.isEmpty(queueADT[id]));  // [4, "5", 6, "7", 8, 9, 10], R == false
        result.add(ArrayQueueADT.dequeue(queueADT[id]));  // ["5", 6, "7", 8, 9, 10], R == 4
        result.add(ArrayQueueADT.dequeue(queueADT[id]));  // [6, "7", 8, 9, 10], R == "5"
        result.add(ArrayQueueADT.size(queueADT[id]));  // [6, "7", 8, 9, 10], R == 5
        ArrayQueueADT.clear(queueADT[id]);  // []
        result.add(ArrayQueueADT.size(queueADT[id]));  // [], R == 0

        return check(result, List.of(true, 1, 1, 2, 2, "test", 3, true, 1, 1, 2, true, false, 4, "5", 5, 0));
    }
}
