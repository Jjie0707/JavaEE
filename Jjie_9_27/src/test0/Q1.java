package test0;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class Q1 {

    public int LastRemaining_Solution (int n, int m) {
        // write code here
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) list.add(i);
        int start = 0;
        while (list.size() > 1) {
            int tem=m%n;
            start = start + tem - 1 < n ? start + tem - 1 : start + tem - 1 - n;
            list.remove(start);
            n--;
            if (start == n) start = 0;
        }
        return list.get(0);
    }


    public static void main(String[] args) {
//        Deque<Integer> list=new ArrayDeque<>();
//        list.addLast(1);
//        list.addLast(2);
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        int q=in.nextInt();
//        for(int i=0;i<q;i++){
//            int a=in.nextInt();//y
//            int b=in.nextInt();//o
//            int c=in.nextInt();//u
//            int ret=0;
//            int tem=Math.min(a,Math.min(b,c));
//            ret+=2*tem;
//            b-=tem;
//            if(b>=2) ret+=b-1;
//            System.out.println(ret);
//        }
    }
}
