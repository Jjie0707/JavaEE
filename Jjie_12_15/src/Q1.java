import java.util.HashMap;
import java.util.Scanner;

public class Q1 {

    public static char[] s;

    public static long func(int n){
        int length=s.length;
        int left=0;
        long ret=0;
        HashMap<Character,Integer> m=new HashMap<>();
        for(int i=0;i<length;i++){
            char c=s[i];
            m.put(c,m.getOrDefault(c,0)+1);
            while(m.size()>=n){
                char out=s[left];
                if(m.get(out)==1)m.remove(out);
                else m.put(out,m.get(out)-1);
                left++;
            }
            ret=ret+i-left+1;
        }
        return ret;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),l=in.nextInt(),r=in.nextInt();
        s=in.next().toCharArray();
        System.out.println(func(r+1)-func(l));
    }
}
