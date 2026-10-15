package _1014;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class T1 {
    public static void main(String[] args) {
        KthLargest a=new KthLargest(3,new int[]{4,5,8,2});
        System.out.println(a.add(3));
    }
    static class KthLargest {
        private  int k;
        private  int[] nums;
        private PriorityQueue<Integer> heap =new PriorityQueue<>();

        public KthLargest(int k, int[] nums) {
            this.nums=nums;
            for(int num:nums){
                if(k>0){
                    heap.offer(num);k--;
                }else{
                    //堆里的数的个数=k
                    if(num>heap.peek()){
                        heap.poll();heap.offer(num);
                    }
                }
            }
            this.k=k;
        }

        public int add(int val) {
            if(k>0){
                heap.offer(val);k--;
            }else if(val>heap.peek()){
                heap.poll();heap.offer(val);
            }
            return heap.peek();
        }
    }

}
