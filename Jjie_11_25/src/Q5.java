import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Q5 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt();
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            int a = in.nextInt(), b = in.nextInt();
            arr[i] = new int[] {a, b};
        }
        Arrays.sort(arr, (e1, e2)->e1[0] - e2[0]);
        int[] cnt = new int[n];
        HashMap<Integer,Integer> m=new HashMap<>();

        for (int i = 0; i < n; i++) {
            int start = arr[i][0], end = arr[i][1];

            int  max = 0;
            for(int key:m.keySet()){
                if(key>start) break;
                max=Math.max(max,m.get(key));
            }
            cnt[i] = 1 + max;
            if(cnt[i]>m.getOrDefault(end,0)){
                m.put(end,cnt[i]);
            }
        }
        int ret = 0;
        for (int i = 0; i < n; i++) {
            ret = Math.max(ret, cnt[i]);
        }
        System.out.println(ret);
    }
}
