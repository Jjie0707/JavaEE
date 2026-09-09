package _0809;

import java.util.HashMap;

public class T1 {
    public static void main(String[] args) {
        System.out.println(CheckPermutation("abc","bca"));
    }
    public static  boolean CheckPermutation(String s1, String s2) {
        char[] c1=s1.toCharArray(),c2=s2.toCharArray();
        HashMap<Character,Integer> m1=new HashMap<>(),m2=new HashMap<>();
        for(char tem:c1){
            m1.put(tem, m1.getOrDefault(tem,0)+1);
        }
        for(char tem:c2){
            m2.put(tem, m2.getOrDefault(tem,0)+1);
        }
        if(m1.keySet().size()!=m2.keySet().size()) return false;
            for(char key:m1.keySet()){
                if(m1.get(key)!=m2.getOrDefault(key,0)) return false;
            }
        return true;
    }
}
