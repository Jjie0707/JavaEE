import java.util.LinkedList;

public class Q1 {
    public int[] solve (int[] a) {
        // write code here
        int n=a.length;
        int[] ret=new int[n];int index=1;
        ret[0]=n;
        int max=Integer.MIN_VALUE;
        LinkedList<Integer> s=new LinkedList<>();
        for(int i=0;i<n;i++){
            int cur=a[i];
            if(cur==n) continue;
            if( (max==Integer.MIN_VALUE && cur!=n ) || cur<ret[index-1]-1){
                s.addLast(cur);
                max=Math.max(max,cur);
            }
        }
        return ret;
    }
}
