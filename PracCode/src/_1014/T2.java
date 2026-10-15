package _1014;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class T2 {
    public static void main(String[] args) {
        System.out.println(topKFrequent(new String[]{"the","day","is","sunny","the","the","the","sunny","is","is"}, 4));
    }

    static class Pair implements Comparable<Pair> {
        private String word;
        private int count;

        Pair(String word, int count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public int compareTo(Pair o) {
            //   ret>0 --> this > o
            if (this.count != o.count)
                return this.count - o.count;
            return o.word.compareTo(this.word);
        }
    }

    static public List<String> topKFrequent(String[] words, int k) {
        List<String> ret = new ArrayList<>();
        HashMap<String, Integer> m = new HashMap<>();
        for (String tem : words) {
            m.put(tem, m.getOrDefault(tem, 0) + 1);
        }
        PriorityQueue<Pair> heap = new PriorityQueue<>();
        int count = k;
        for (String key : m.keySet()) {
            Pair tem = new Pair(key, m.get(key));
            if (count > 0) {
                heap.offer(tem);
                count--;
            } else {
                if (tem.compareTo(heap.peek()) > 0) {
                    heap.poll();
                    heap.offer(tem);
                }
            }
        }
        while (!heap.isEmpty()) {
            ret.add(0, heap.poll().word);
        }
        return ret;
    }
}
