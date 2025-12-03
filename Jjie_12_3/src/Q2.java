import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt();
        int[] nums = new int[n];
        int ret = 1;
        for(int i=0;i<n;i++) nums[i]=in.nextInt();
        int slow = nums[0], fast = nums[0];
        for (int i = 1; i < n; i++) {
            int tem = nums[i];
            if (slow == fast) {
                fast = tem;
            } else {
                if (fast > slow) {
                    if (tem >= fast) {
                        fast = tem;
                    } else {
                        ret++;
                        fast = slow = tem;
                    }
                } else {
                    if (tem <= fast) {
                        fast = tem;
                    } else {
                        ret++;
                        fast = slow = tem;
                    }
                }
            }
        }
        System.out.println(ret);
    }
}
