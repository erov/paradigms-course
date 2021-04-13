package queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ArrayQueueModuleTest {
    private static int testCounter = 0;
    private static int seed = 565;  // Enter seed for random
    private static final int randomTestCount = 1000;

    public static void main(String[] args) {
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
        ArrayQueueModule.clear();

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
                    ArrayQueueModule.enqueue(e);
                } else {
                    // test push(e)
                    inputCopy.add(0, e);
                    ArrayQueueModule.push(e);
                }
            }

            // test size()
            result.add(ArrayQueueModule.size());
            correct.add(inputCopy.size());

            // test dequeue()
            while (inputCopy.size() > 0 && dequeueCounter-- > 0) {
                correct.add(inputCopy.get(0));
                inputCopy.remove(0);
                result.add(ArrayQueueModule.dequeue());
            }

            // test remove()
            while (inputCopy.size() > 0 && removeCounter-- > 0) {
                correct.add(inputCopy.get(inputCopy.size() - 1));
                inputCopy.remove(inputCopy.size() - 1);
                result.add(ArrayQueueModule.remove());
            }

            // test element()
            if (inputCopy.size() > 0) {
                result.add(ArrayQueueModule.element());
                correct.add(inputCopy.get(0));
            }

            // test peek()
            if (inputCopy.size() > 0) {
                result.add(ArrayQueueModule.peek());
                correct.add(inputCopy.get(inputCopy.size() - 1));
            }

            // test isEmpty()
            result.add(ArrayQueueModule.isEmpty());
            correct.add(inputCopy.isEmpty());

            // test clear()
            if (Math.abs(rnd.nextInt() % 20) == i % 20) {
                ArrayQueueModule.clear();
                inputCopy.clear();
            }

            // test toArray()
            toArrayCheck = toArrayCheck & Arrays.equals(inputCopy.toArray(), ArrayQueueModule.toArray());

            // test toStr()
            toStrCheck = toStrCheck & inputCopy.toString().equals(ArrayQueueModule.toStr());
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
        ArrayQueueModule.clear();
        List<Object> result = new ArrayList<>();

        result.add(ArrayQueueModule.isEmpty());  // [], R == true
        ArrayQueueModule.enqueue(1);  // [1]
        ArrayQueueModule.enqueue(2);  // [1, 2]
        ArrayQueueModule.enqueue("test");  // [1, 2, "test"]
        ArrayQueueModule.enqueue(3);  // [1, 2, "test", 3]
        result.add(ArrayQueueModule.element());  // [1, 2, "test", 3], R == 1
        result.add(ArrayQueueModule.dequeue());  // [2, "test", 3], R == 1
        result.add(ArrayQueueModule.dequeue());  // ["test", 3], R == 2
        result.add(ArrayQueueModule.size());  // ["test", 3], R == 2
        ArrayQueueModule.enqueue(4);  // ["test", 3, 4]
        ArrayQueueModule.enqueue(5);  // ["test", 3, 4, 5]
        result.add(ArrayQueueModule.dequeue());  // [3, 4, 5], R == "test"
        result.add(ArrayQueueModule.element());  // [3, 4, 5], R == 3
        ArrayQueueModule.clear();  // []
        result.add(ArrayQueueModule.isEmpty());  // [], R == true
        ArrayQueueModule.enqueue(1);  // [1]
        ArrayQueueModule.enqueue(2);  // [1, 2]
        ArrayQueueModule.enqueue(true);  // [1, 2, true]
        ArrayQueueModule.enqueue(4);  // [1, 2, true, 4]
        ArrayQueueModule.enqueue("5");  // [1, 2, true, 4, "5"]
        result.add(ArrayQueueModule.element());  // [1, 2, true, 4, "5"], R == 1
        ArrayQueueModule.enqueue(6);  // [1, 2, true, 4, "5", 6]
        ArrayQueueModule.enqueue("7");  // [1, 2, true, 4, "5", 6, "7"]
        ArrayQueueModule.enqueue(8);  // [1, 2, true, 4, "5", 6, "7", 8]
        ArrayQueueModule.enqueue(9);  // [1, 2, true, 4, "5", 6, "7", 8, 9]
        ArrayQueueModule.enqueue(10);  // [1, 2, true, 4, "5", 6, "7", 8, 9, 10]
        result.add(ArrayQueueModule.dequeue());  // [2, true, 4, "5", 6, "7", 8, 9, 10], R == 1
        result.add(ArrayQueueModule.dequeue());  // [true, 4, "5", 6, "7", 8, 9, 10], R == 2
        result.add(ArrayQueueModule.dequeue());  // [4, "5", 6, "7", 8, 9, 10], R == true
        result.add(ArrayQueueModule.isEmpty());  // [4, "5", 6, "7", 8, 9, 10], R == false
        result.add(ArrayQueueModule.dequeue());  // ["5", 6, "7", 8, 9, 10], R == 4
        result.add(ArrayQueueModule.dequeue());  // [6, "7", 8, 9, 10], R == "5"
        result.add(ArrayQueueModule.size());  // [6, "7", 8, 9, 10], R == 5
        ArrayQueueModule.clear();  // []
        result.add(ArrayQueueModule.size());  // [], R == 0
        
        return check(result, List.of(true, 1, 1, 2, 2, "test", 3, true, 1, 1, 2, true, false, 4, "5", 5, 0));
    }
}
