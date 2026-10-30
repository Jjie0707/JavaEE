package _1030;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class T1 {
    public static void main(String[] args) {
        String[] bank ={"AACCGGTA","AACCGCTA","AAACGGTA"};
        System.out.println(minMutation("AACCGGTT", "AAACGGTA", bank));;
    }
    static public int minMutation(String startGene, String endGene, String[] bank) {
        int ret=-1;
        int len=startGene.length();
        int[] pos=new int[bank.length];
        LinkedList<String> q=new LinkedList<>();
        q.addLast(startGene);
        int step=1;
        while(!q.isEmpty()){
            int size=q.size();
            for(int i=0;i<size;i++){
                String cur=q.removeFirst();
                for(int j=0;j<bank.length;j++){
                    String tem=bank[j];
                    if(pos[j]==0 && moveable(cur,tem)){
                        if(tem.equals(endGene)) return step;
                        pos[j]=1;q.addLast(tem);
                    }
                }
            }
            step++;
        }
        return ret;
    }
    static public boolean moveable(String s1,String s2){
        char[] cs1=s1.toCharArray(),cs2=s2.toCharArray();
        int count=0;
        for(int i=0;i<8;i++){
            if(cs1[i]!=cs2[i]) count++;
        }
        return count==1;
    }
}
