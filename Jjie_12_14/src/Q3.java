import java.util.HashMap;
import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),l=in.nextInt(),r=in.nextInt();
        char[] s=in.next().toCharArray();
        long ret=0;
        for(int i=l;i<=r;i++){
            //每一次只考虑种类为i的字符串
            int left = 0;
            HashMap<Character,Integer> m=new HashMap<>();
            for (int right = 0; right < n; right++) {
                m.put(s[right], m.getOrDefault(s[right], 0) + 1);
                while (m.size()>i) {
                    char out = s[left];
                    if (m.get(out) == 1) m.remove(out);
                    else m.put(out, m.get(out) - 1);
                    left++;
                }
                if(m.size()==i){
                    for(int j=right;j<n;j++){
                        if(m.containsKey(s[j])) ret++;
                        else break;
                    }
                    if(m.get(s[left])==1){
                        m.remove(s[left]);
                    }else{
                        m.put(s[left],m.get(s[left])-1);
                    }
                    left++;
                }//统计当前左端点起始的所有可能右端点，并右移左端点，防止固定右端点时重复
                while (m.size()==i) {
                    ret++;
                    char out = s[left];
                    if (m.get(out) == 1) m.remove(out);
                    else m.put(out, m.get(out) - 1);
                    left++;
                }//统计当前右端点所有可能的左端点，刚刚已经去掉了可能重复的那个left所以不会重复
            }
        }
        System.out.println(ret);
    }
}
