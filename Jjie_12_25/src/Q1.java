import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class Q1 {
    static void main() {
        System.out.println(Arrays.toString(solve(new int[]{1,2,3,4})));
    }
    public  static int[] solve (int[] a) {
        // write code here
        int n=a.length;
        int[] ret=new int[n];int index=0;
        Stack<Integer> st=new Stack<>();
        HashSet<Integer> s=new HashSet<>();
        int aim=n;
        for(int cur:a){
            st.push(cur);
            s.add(cur);
            while(s.contains(aim)) aim--;
            while(!st.isEmpty() && st.peek()>aim) ret[index++]=st.pop();
        }
        return ret;
    }
}
