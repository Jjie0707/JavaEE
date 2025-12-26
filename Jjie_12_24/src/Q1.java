import java.util.HashSet;
import java.util.Stack;

public class Q1 {
    static void main() {
        System.out.println(66);
    }
    public int[] solve (int[] a) {
        // write code here
        int n=a.length;
        int[] ret=new int[n];int index=0;
        Stack<Integer> s=new Stack<>();
        HashSet<Integer> s1=new HashSet<>();
        HashSet<Integer> s2 = new HashSet<>();
        int aim = n;
        for (int cur : a) {
            s.push(cur);
            s1.add(cur);
            while(s1.contains(aim)){
                while (s1.contains(aim)) {
                    int tem = s.pop();
                    s1.remove(tem);
                    s2.add(tem);
                    ret[index++] = tem;
                    if (tem == aim) aim--;
                }
                while (s2.contains(aim)) aim--;
            }
        }
        while(!s.isEmpty()){
            ret[index++]=s.pop();
        }
        return ret;
    }
}
