import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;


public class Q2 {
    static void main() {
        System.out.println(666);
    }
    public int[] solve (int[] a) {
        int n=a.length;
        int[] ret=new int[n];int index=0;
        HashSet<Integer> m=new HashSet<>();
        Stack<Integer> s=new Stack<>();
        int aim=n;
        for (int cur : a) {
            s.push(cur);
            m.add(cur);
            while(m.contains(aim)) aim--;

            while(!s.isEmpty() && s.peek()>=aim){
                ret[index++]=s.pop();
            }
        }
        return ret;
    }
}
