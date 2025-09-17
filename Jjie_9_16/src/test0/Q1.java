package test0;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Q1 {
    public static void main(String[] args) {
        int[] heights={2,1,5,6,2,3};
        System.out.println(largestRectangleArea(heights));
    }
    public static int largestRectangleArea(int[] heights) {
        int n=heights.length;
        if(n==0) return 0;
        int ret=0;
        int[] left=new int[n];

        LinkedList<Integer> stack=new LinkedList<>();
        for(int i=0;i<n;i++){
            int cur=heights[i];
            while(!stack.isEmpty() && heights[stack.getLast()]>=cur){
                stack.removeLast();
            }
            left[i]= stack.isEmpty() ? -1 : stack.getLast();
            stack.addLast(i);
        }
        stack=new LinkedList<>();
        for(int i=n-1;i>=0;i--){
            int cur=heights[i];
            while(!stack.isEmpty() && heights[stack.getLast()]>=cur){
                stack.removeLast();
            }
            int right= stack.isEmpty() ? n : stack.getLast();
            int tem=cur*(right-left[i]-1);
            if(tem>=ret) ret=tem;
            stack.addLast(i);
        }
        return ret;
    }
    public static void main0(String[] args) {
        int[] temperatures =
                {89,62,70,58,47,47,46,76,100,70};
        System.out.println(Arrays.toString(dailyTemperatures(temperatures)));
    }
    public static int[] dailyTemperatures(int[] temperatures){
        int n = temperatures.length;
        int[] ret = new int[n];
        if (n == 0) return ret;
        LinkedList<Integer> val = new LinkedList<>();
        LinkedList<Integer> index = new LinkedList<>();
        for (int i = n - 1; i >= 0; i--) {
            if (val.isEmpty() || val.getLast() > temperatures[i]) {
                if (val.isEmpty()) {
                    ret[i] = 0;
                } else {
                    ret[i] = index.getLast() - i;
                }
                val.addLast(temperatures[i]);
                index.addLast(i);
            } else {
                while (!val.isEmpty() && val.getLast() <= temperatures[i]) {
                    val.removeLast();
                    index.removeLast();
                }
                if (val.isEmpty()) {
                    ret[i] = 0;
                } else {
                    ret[i] = index.getLast() - i;
                }
                val.addLast(temperatures[i]);
                index.addLast(i);
            }
        }
        return ret;
    }
}
