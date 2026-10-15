package _1014;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class T3 {
    public static void main(String[] args) {
        MedianFinder m=new MedianFinder();
        m.addNum(-1);
        System.out.println(m.findMedian());
        m.addNum(-2);
        System.out.println(m.findMedian());
        m.addNum(-3);
        System.out.println(m.findMedian());
        m.addNum(-4);
        System.out.println(m.findMedian());
        m.addNum(-5);
        System.out.println(m.findMedian());
    }
    static class MedianFinder {
        int count = 0;
        //   左大  右小
        private PriorityQueue<Integer> left = new PriorityQueue<>((e1, e2) -> e2 - e1);
        private PriorityQueue<Integer> right = new PriorityQueue<>();

        public MedianFinder() {
        }

        public void addNum(int num) {
            count++;
            int targetSizeR = (count + 1) / 2, targetSizeL = count - targetSizeR;
            int rightSize = right.size(), leftSize = left.size();
            if (rightSize == 0) {
                right.offer(num);
            } else {
                if (num > right.peek()) {
                    if (rightSize >= targetSizeR) {
                        left.offer(right.poll());
                    }
                    right.offer(num);
                } else {
                    left.offer(num);
                    if (leftSize >= targetSizeL) {
                        right.offer(left.poll());
                    }
                }
            }
        }

        public double findMedian() {
            if (count % 2 == 1)
                return right.peek();
            return (left.peek() + right.peek()) / 2.0;
        }
    }
}
