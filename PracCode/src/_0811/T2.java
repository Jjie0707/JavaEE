package _0811;

import java.util.ArrayList;
import java.util.HashMap;

public class T2 {
    public static void main(String[] args) {
        String s="abbcccba";
        System.out.println(longestPalindrome(s));
    }
    public static String longestPalindrome(String s) {
        char[] ss=s.toCharArray();
        String ret=s.charAt(0)+"";
        HashMap<Character, ArrayList<Integer>> m=new HashMap<>();
        for(int i=0;i<ss.length;i++){
            char c=ss[i];
            if(!m.containsKey(c)){
                ArrayList<Integer> list=new ArrayList<>();
                list.addLast(i);m.put(c,list);
            }else{
                ArrayList<Integer> list=m.get(c);
                for(int tem:list){
                    if(i+1-tem>ret.length()){
                        if(func(ss,tem,i)){
                            ret=s.substring(tem,i+1);
                        }
                    }
                }
                list.addLast(i);m.put(c,list);
            }
        }
        return ret;
    }
    public static boolean func(char[] s,int left,int right){
        while(left<right){
            if(s[left]!=s[right]) return false;
            else {
                left++;right--;
            }
        }
        return true;
    }
}
