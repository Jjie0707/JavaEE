import java.util.HashMap;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s = in.next();
        HashMap<Character, Integer> m = new HashMap<>();
        if(s.isEmpty()){
            System.out.println("No Answer");
            System.out.println(0);
        }
        char[] str = s.toCharArray();

        for (int i = 0; i < str.length; i++) {
            m.put(str[i], m.getOrDefault(str[i], 0) + 1);
        }
        int maxn = 0, minn = 101;
        for (int tem : m.values()) {
            if(tem>=maxn) maxn=tem;
        }
        for (int tem : m.values()) {
            if(tem<=minn) minn=tem;
        }
        if (func(maxn - minn)) {
            System.out.println("Lucky Word");
            System.out.println(maxn - minn);
        } else {
            System.out.println("No Answer");
            System.out.println(0);
        }
    }

    public static boolean func(int n) {
        if (n == 1 || n==0) return false;
        if (n == 2) return true;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
