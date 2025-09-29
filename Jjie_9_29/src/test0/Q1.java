package test0;

import java.util.ArrayList;

public class Q1 {
    public int LastRemaining_Solution (int n, int m) {
        // write code here
        int len=n;

        ArrayList<Integer> list=new ArrayList<>();

        for(int i=0;i<n;i++){
            list.add(i);
        }

        int cur=0;
        while(list.size()>1){

            cur=cur+m-1<n ? cur+m-1 : (cur+m-1)%n;
            list.remove(cur);
            n--;
        }
        return list.get(0);
    }
}
