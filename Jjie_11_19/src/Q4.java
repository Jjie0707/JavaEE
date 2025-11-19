import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Q4 {
    public int minmumNumberOfHost (int n, int[][] startEnd) {
        // write code here
        Arrays.sort(startEnd,(e1,e2)->e1[0]-e2[0]);
        //走到这里已经把会议数组按照会议开始时间排序
        PriorityQueue<Integer> heap=new PriorityQueue<>();
        for(int[] tem:startEnd){
            if(heap.isEmpty() || heap.peek()<=tem[0]){
                heap.poll();
            }
            heap.offer(tem[1]);
        }
        return heap.size();
    }
}
