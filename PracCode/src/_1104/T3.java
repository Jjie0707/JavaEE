package _1104;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class T3 {
    public static void main(String[] args) {
        String[] words={
            "wrt", "wrf", "er", "ett", "rftt"
        };
        System.out.println(alienOrder(words));
    }
    static public String alienOrder(String[] words) {
        HashSet<Character> count=new HashSet<>();
        if(words.length==0) return "";
        if(words.length==1) return words[0];
        HashMap<Character,ArrayList<Character>> map=new HashMap<>();
        HashMap<Character,Integer> pre=new HashMap<>();
        for(int slow=0,fast=1;fast<words.length;slow++,fast++){
            char[] s1=words[slow].toCharArray(),
                    s2=words[fast].toCharArray();
            if(s1.length>s2.length) if(check(s1,s2)) return "";
            boolean flag=true;
            for(int i=0;i<Math.min(s1.length,s2.length);i++){
                if(flag && s1[i] != s2[i]){
                    ArrayList<Character> tem = map.getOrDefault(s1[i], new ArrayList<>());
                    tem.addLast(s2[i]);
                    map.put(s1[i], tem);
                    pre.put(s2[i], pre.getOrDefault(s2[i], 0) + 1);
                    flag = false;
                }
                count.add(s1[i]);
                count.add(s2[i]);
            }
            for(int i=Math.min(s1.length,s2.length);i<s1.length;i++) count.add(s1[i]);
            for(int i=Math.min(s1.length,s2.length);i<s2.length;i++) count.add(s2[i]);
        }//走到这里,完成建图
        StringBuilder ret=new StringBuilder();
        ArrayDeque<Character> q=new ArrayDeque<>();
        for(char tem:count) pre.put(tem,pre.getOrDefault(tem,0));
        for(char key: pre.keySet()){
            if(pre.get(key)==0) q.addLast(key);
        }
        while(!q.isEmpty()){
            char cur=q.removeFirst();ret.append(cur);
            ArrayList<Character> tem=map.get(cur);
            if(tem!=null){
                for (char next : tem) {
                    pre.put(next, pre.get(next) - 1);
                    if (pre.get(next) == 0) q.addLast(next);
                }
            }
        }
        return ret.length()==count.size() ? ret.toString() : "";
    }
    static private boolean check(char[] s1,char[] s2){
        for(int i=0;i<s2.length;i++){
            if(s1[i]!=s2[i]) return false;
        }
        return true;
    }
}
