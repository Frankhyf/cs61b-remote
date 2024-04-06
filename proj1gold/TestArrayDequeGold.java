import static  org.junit.Assert.*;
import  org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testDeque() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();
        String message = ""; // 用于生成操作序列的消息字符串

        for (int i = 0; i < 1000; i++) { // 这里我们暂时只循环10次作为示例
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.25) {
                // 以一定概率调用addFirst
                Integer val = StdRandom.uniform(0, 100);
                studentDeque.addFirst(val);
                solutionDeque.addFirst(val);
                message += "\naddFirst(" + val + ")";
            } else if (numberBetweenZeroAndOne >= 0.25 && numberBetweenZeroAndOne < 0.5) {
                // 以一定概率调用addLast
                Integer val = StdRandom.uniform(0, 100);
                studentDeque.addLast(val);
                solutionDeque.addLast(val);
                message += "\naddLast(" + val + ")";
            } else if (numberBetweenZeroAndOne >= 0.5 && numberBetweenZeroAndOne < 0.75) {
                // 以一定概率调用removeFirst，如果队列不为空
                if (!studentDeque.isEmpty()) {
                    Integer studentVal = studentDeque.removeFirst();
                    Integer solutionVal = solutionDeque.removeFirst();
                    message += "\nremoveFirst()";
                    assertEquals(message, solutionVal, studentVal);
                }
            } else {
                // 以一定概率调用removeLast，如果队列不为空
                if (!studentDeque.isEmpty()) {
                    Integer studentVal = studentDeque.removeLast();
                    Integer solutionVal = solutionDeque.removeLast();
                    message += "\nremoveLast()";
                    assertEquals(message, solutionVal, studentVal);
                }
            }
        }
    }
}
