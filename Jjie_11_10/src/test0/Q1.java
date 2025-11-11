package test0;

import java.util.HashMap;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt(), m = in.nextInt();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int tem = in.nextInt();
            map.put(tem, map.getOrDefault(tem, 0) + 1);
        }
        if (map.size() > m) {
            System.out.println(-1);
        } else {
            int left = 1, right = 1;
            for (int tem : map.values()) right = Math.max(right, tem);
            int ret = Integer.MAX_VALUE;
   
            while (left < right) {
                int count = 0;
                int mid = (right - left) / 2 + left;
                for (int tem : map.values()) {
                    count = count + tem / mid;
                    count = count +( tem % mid == 0 ? 0 : 1);
                }
                if (count > m) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            System.out.println(right);
        }
    }
}
