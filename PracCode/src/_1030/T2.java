package _1030;

import java.util.LinkedList;
import java.util.List;

public class T2 {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int ret=0;
        int len=wordList.size();
        int[] pos=new int[len];
        LinkedList<String> q=new LinkedList<>();
        q.addLast(beginWord);
        int step=1;
        while(!q.isEmpty()){
            int size=q.size();
            for(int i=0;i<size;i++){
                String cur=q.removeFirst();
                for(int j=0;j<len;j++){
                    String tem=wordList.get(j);
                    if(pos[j]==0 && moveable(cur,tem)){
                        if(tem.equals(endWord)) return step+1;
                        pos[j]=1;q.addLast(tem);
                    }
                }
            }
            step++;
        }
        return ret;
    }
    public boolean moveable(String s1,String s2){
        char[] cs1=s1.toCharArray(),cs2=s2.toCharArray();
        int count=0;int length=s1.length();
        for(int i=0;i<length;i++){
            if(cs1[i]!=cs2[i]) count++;
        }
        return count==1;
    }
}
