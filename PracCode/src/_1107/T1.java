package _1107;

import java.util.List;
import java.util.Stack;

public class T1 {
    public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        func(A,B,C,A.size());

    }
    public void func(List<Integer> a,List<Integer> b,List<Integer> c,int n){
        if(n==1) {
            c.addLast(a.removeLast());
            return;
        }
        func(a,c,b,n-1);
        func(a,b,c,1);
        func(b,a,c,n-1);
    }
}
