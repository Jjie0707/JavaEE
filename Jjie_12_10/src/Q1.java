import java.util.Scanner;

public class Q1 {
    public static int[] arr = new int[26];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) {
            String s = in.next();
            char[] tem = s.toCharArray();
            for (char c : tem) {
                arr[c - 'a']++;
            }
        }
        int ret = 0;
        char c = 'a';
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > ret) {
                c = (char)('a' + i);
                ret = arr[i];
            }
        }
        System.out.println(c);
    }
}
