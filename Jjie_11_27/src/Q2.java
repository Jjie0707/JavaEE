import java.util.HashMap;
import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str=in.next();
        char[] s=str.toCharArray();
        HashMap<Character,Integer> m=new HashMap<>();
        int ret=0;
        int left=0;
        for(int i=0;i<s.length;i++){
            char tem=s[i];
            m.put(tem,m.getOrDefault(tem,0)+1);
            if(m.size()==2) ret=Math.max(ret,i-left+1);
            while(m.size()>2){
                char out=s[left];
                if(m.get(out)>1){
                    m.put(out,m.get(out)-1);
                }else m.remove(out);
                left++;
            }
        }
        if(m.size()==2) ret=Math.max(ret,s.length-left);
        System.out.println(ret);
    }
}
