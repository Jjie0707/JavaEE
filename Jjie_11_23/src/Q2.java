import java.util.Arrays;
import java.util.PriorityQueue;

public class Q2 {
    public int minmumNumberOfHost (int n, int[][] startEnd) {
        // write code here
        Arrays.sort(startEnd,(e1,e2)->e1[0]-e2[0]);

        PriorityQueue<Integer> heap=new PriorityQueue<>();
        for(int i=0;i<n;i++){
            int start=startEnd[i][0];
            if(heap.isEmpty() || heap.peek()>start){
                heap.offer(start);
            }else{
                heap.poll();
                heap.offer(start);
            }
        }
        return heap.size();
    }
}
