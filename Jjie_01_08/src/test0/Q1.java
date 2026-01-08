package test0;

import java.util.HashMap;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        char[] s=in.next().toCharArray();
        HashMap<Integer, Character> m=new HashMap<>();
        for(int i=0;i<n;i++){
            int l=0,r=m.size();
            while(l<r){
                int mid=l+(r-l+1)/2;
                if(!m.isEmpty() && m.get(mid)>s[i])  r=mid-1;
                else l=mid;
            }//走到这里说明找到了插入的位置
            if(!m.containsKey(l+1) || m.get(l+1)>=s[i]) m.put(l+1,s[i]);
        }
        System.out.println(n-m.size());
    }
}
