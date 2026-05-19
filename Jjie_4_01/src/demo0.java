import java.util.ArrayList;
import java.util.Arrays;

public class demo0 {
    public static void main(String[] args) {
        int[] nums={1,2,1,3,2,5};
        System.out.println(Arrays.toString(singleNumber(nums)));
    }
    public static  int[] singleNumber(int[] nums) {
        ArrayList<Integer> list=new ArrayList<>();
        ArrayList<Integer> list2=new ArrayList<>();
        int tem=0;
        for(int num:nums) tem=tem^num;
        int pos=0;
        while((tem&1)!=1){
            pos++;
            tem=tem>>1;
        }
        tem=(1<<pos);
        int[] ret=new int[2];
        for(int num:nums){
            if((num&tem)==tem){
                ret[0]^=num;
            }else{
                ret[1]^=num;
            }
        }
        return ret;
    }

    public int hammingDistance(int x, int y) {
        int tem=x^y;
        int ret=0;
        while(tem>0){
            if((tem&1)==1) ret++;
            tem=tem>>1;
        }
        return ret;
    }
    public int[] countBits(int n) {
        int[] ret=new int[n];
        for(int i=0;i<n;i++){
            int count=0;
            int tem=i;
            for(int j=0;j<32;j++){
                if((tem&1)==1) count++;
            }
            ret[i]=count;
        }
        return ret;
    }
    public int hammingWeight(int n) {
        int ret=0;
        for(int i=0;i<32;i++){
            if((n&1)==1)ret++;
            n=n>>1;
        }
        return ret;
    }
}
