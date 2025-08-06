package test0;

import java.util.HashMap;

public class Q1 {
//    s = "ADOBECODEBANC", t = "ABC"
//    输出："BANC"
    public static void main(String[] args) {
        String s = "ADOBECODEBANC", t = "ABC";
        System.out.println(minWindow(s, t));
    }
    public static String minWindow(String s, String t) {
        String ret="";
        HashMap<Character,Integer> m=new HashMap<>();

        int length1=s.length(),length2=t.length();
        if(length2>length1) return ret;

        for(int i=0;i<length2;i++){
            char c=t.charAt(i);
            m.put(c,m.getOrDefault(c,0)+1);
        }

        int left=0,right=0;
        int count=length2;
        for(;right<length1;right++){
            char c=s.charAt(right);
            if(m.get(c)!=null){
                if(m.get(c)>0) count--;
                m.put(c,m.get(c)-1);
            }
            while(count==0){
                if(ret.isEmpty() || ret.length()>=(right-left+1)) ret=s.substring(left,right+1);
                char tem=s.charAt(left);
                if(m.get(tem)!=null) {
                    m.put(tem, m.get(tem) + 1);
                    if(m.get(tem)>0) count++;
                }
                left++;
            }
        }
        return ret;
    }
}
