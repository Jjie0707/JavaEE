package test0;

public class Q2 {
    public int mySqrt(int x) {
        long left=0,right=x+1;
        while(left<right){
            long mid=left+(right-left+1)/2;
            if(mid*mid<=x) left=mid;
            else right=mid-1;
        }
        return (int)left;
    }
}
