package test0;

import java.util.ArrayDeque;
import java.util.Deque;

public class Q1 {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] ret=new int[nums.length-(k-1)];
        Deque<Integer> q=new ArrayDeque<>();
        for(int right=0;right<nums.length;right++){
            while(!q.isEmpty() && nums[q.getLast()]<nums[right]){
                q.removeLast();
            }
            q.addLast(right);

            int left=right-k+1;
            while(!q.isEmpty() && q.getFirst()<left){
                q.removeFirst();
            }

            if(left>=0){
                ret[left]=nums[q.getFirst()];
            }
        }
        return ret;
    }
}
