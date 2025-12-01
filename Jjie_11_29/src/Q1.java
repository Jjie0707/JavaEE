import java.util.ArrayList;
import java.util.Scanner;

public class Q1 {
    public static ArrayList<Integer> func(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i <= n; i++) list.add(i);
        return list;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) {
            int n = in.nextInt();
            ArrayList<Integer> list = func(n);
            n++;
            while (n > 1) {
                int pos = 1;
                ArrayList<Integer> tem = new ArrayList<>();
                while (pos <= n) {
                    if (pos % 2 == 0) {
                        tem.add(list.get(pos - 1));
                    }
                    pos++;
                }
                n /= 2;
                list = tem;
            }
            System.out.println(list.get(0));
        }
    }
}
