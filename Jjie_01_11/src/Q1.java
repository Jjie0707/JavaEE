import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Q1 {

    static int ret;
    static HashMap<Character,Integer> m;
    static PriorityQueue<Integer> heap;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while(in.hasNext()){
            ret=0;
            char[] tem = in.next().toCharArray();

            m = new HashMap<>();
            heap = new PriorityQueue<>();
            for (char c : tem) m.put(c, m.getOrDefault(c, 0) + 1);
            for (char c : m.keySet()) heap.offer(m.get(c));
            while (heap.size() >= 2) {
                int num1 = heap.poll(), num2 = heap.poll();
                ret=ret+num1+num2;
                heap.offer(num1+num2);
            }
            System.out.println(ret);
        }
    }
}
