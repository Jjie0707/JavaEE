package _1004;

import java.util.Stack;

public class T1 {
    private static Stack<Integer> S=new Stack<>();

    public static void main(String[] args) {
        validateStackSequences(new int[]{1,2,3,4,5},new int[]{4,5,3,2,1});
    }
    public static  boolean validateStackSequences(int[] pushed, int[] popped) {
        S.clear();;
        int count=popped.length;
        int i=0,j=0;
        while(i<popped.length && j<popped.length){
            S.push(pushed[i++]);
            while(!S.isEmpty() && S.peek()==popped[j]){
                S.pop();j++;
                count--;
            }
        }
        while(!S.isEmpty() && S.peek()==popped[j] && j<popped.length){
            S.pop();j++;
            count--;
        }
        if(count>0) return false;
        return true;
    }
}
