package _0809;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class T2 {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> ret=new ArrayList<>();
        HashMap<String,ArrayList<String>> m=new HashMap<>();
        for(String tem:strs){
            int[] s=new int[26];char[] cs=tem.toCharArray();
            for(char c:cs) s[c-'a']++;
            String key=getKey(s);
            ArrayList<String> list=m.getOrDefault(key,new ArrayList<>());
            list.add(tem);
            m.put(key,list);
        }
        for(String key:m.keySet()){
            ret.add(m.get(key));
        }
        return ret;
    }
    public String getKey(int[] s){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<26;i++){
            if(s[i]!=0){
                sb.append(s[i]);
                sb.append('a'+i);
            }
        }
        return sb.toString();
    }
}
