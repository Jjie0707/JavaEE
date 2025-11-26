import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            int n = in.nextInt(), a = in.nextInt(), b = in.nextInt();
            long ret = 0;
            if ((b / 3.0) <= (a / 2.0)) {
                //只要三人船的平均单价小 那我们就选择三人船
                int cnt = n / 3;
                ret += cnt * b;
                if (n- 3 * cnt > 0) {
                    ret += Math.min(a, b);
                }
                System.out.println(ret);
            } else {
                int cnt = n / 2;
                ret += cnt * a;
                if (n - 2 * cnt > 0) {
                    ret += Math.min(a, b);
                }
                System.out.println(ret);
            }
        }
    }
}
