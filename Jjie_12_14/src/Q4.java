import java.util.HashMap;
import java.util.Scanner;

public class Q4 {
    static char[] s;

    public static long func(int n) {//求至少有n种 m.size()>=n 通过A-m.size()<=n-1得到
        HashMap<Character, Integer> m = new HashMap<>();
        int left = 0;
        long ret = 0, l = s.length;
        for (int i=0;i<l;i++) {
            char c=s[i];
            m.put(c, m.getOrDefault(c, 0) + 1);
            while (!(m.size() <= n-1)) {  //<=n-1
                char tem = s[left];
                if (m.get(tem) == 1) m.remove(tem);
                else m.put(tem, m.get(tem) - 1);
                left++;
            }
            int len=i-left+1;
            ret=ret+len*(len+1)/2;
        }
        return l * (l + 1) / 2 - ret;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt(), l = in.nextInt(), r = in.nextInt();
        s = in.next().toCharArray();
        System.out.println(func(l)-func(r+1));
    }
}
