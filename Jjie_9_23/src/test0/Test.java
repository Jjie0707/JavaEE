package test0;

import java.util.Scanner;

public class Test {



    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int n=in.nextInt();

            String a=null,b=null;
            for(int i=0;i<2;i++){
                if(a==null) a=in.next();
                if(b==null) b=in.next();
            }
            int index1=-1,index2=-1;
            int ret=0;
            for(int i = 0;i<n;i++){
                String s=in.next();

                if(s.equals(a)) index1=i;
                else if(s.equals(b)) index2=i;

                if(index1!=-1 && index2!=-1 && Math.abs(index1-index2)<=ret) ret=Math.abs(index1-index2);
            }
            if(index1!=-1 && index2!=-1) System.out.println(ret);
            else System.out.println(-1);
        }
    }



//    public static void main(String[] args) {
//        int len = 10;
//        int[] cost = {1, 100, 1, 1, 1, 90, 1, 1, 80, 1};
//
//
//        int slow = 0, fast = 1;
//        int cost1 = 0, cost2 = 0;
//        while (slow < len) {
//            cost1 += cost[slow];
//            if (slow + 2 < len) {
//                slow = cost[slow + 2] <= cost[slow + 1] ? slow + 2 : slow + 1;
//            } else slow = slow + 2;
//        }
//        while (fast < len) {
//            cost2 += cost[fast];
//            if (fast + 2 < len) {
//                fast = cost[fast + 2] < cost[fast + 1] ? fast + 2 : fast + 1;
//            } else fast = fast + 2;
//        }
//        System.out.println(Math.min(cost1, cost2));
//
//    }
}
