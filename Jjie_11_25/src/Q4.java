import java.util.Scanner;

public class Q4 {
    public class Main {
        private static boolean func(long n) {
            if (n == 1) {
                return false;
            } else if (n == 2) {
                return true;
            }
            for (int i = 2; i < (int)(Math.pow(n,0.5)) + 1; i++) {
                if (n % i == 0) return false;
            }
            return true;
        }
        public static void main(String[] args) {
            Scanner in = new Scanner(System.in);
            // 注意 hasNext 和 hasNextLine 的区别
            int t = in.nextInt();
            long cur = t;
            t/=10;
            while (t > 0) {
                cur = cur * 10 + t % 10;
                t /= 10;
            }
            if (func(cur)) System.out.println("prime");
            else System.out.println("noprime");
        }
    }
}
