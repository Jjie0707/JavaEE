public class Q1 {
    public int getDis (int[] A, int n) {
        // write code here
        int ret=0;
        int min=A[0];
        for(int i=1;i<n;i++){
            int tem=A[i];
            min=Math.min(tem,min);
            ret=Math.max(ret,tem-min);
        }
        return ret;
    }
}
