import java.util.HashMap;

public class demo0 {
    public static void main(String[] args) {
        System.out.println(subarraySum(new int[] {1,1,1},2));
    }

    public static int subarraySum(int[] nums, int k) {
        int n=nums.length;
        HashMap<Integer,Integer> m=new HashMap<>();
        int[] pre=new int[n+1];
        m.put(0,1);
        int ret=0;
        for(int i=1;i<=n;i++){
            pre[i]=pre[i-1]+nums[i-1];
            ret+=m.getOrDefault(pre[i]-k,0);
            m.put(pre[i],m.getOrDefault(pre[i],0)+1);
        }
        return ret;
    }



    public int[] productExceptSelf(int[] nums) {
        int n=nums.length;
        int[] pre=new int[n+1],sub=new int[n+2];
        pre[0]=1;sub[n+1]=1;
        for(int i=1;i<=n;i++){
            pre[i]=pre[i-1]*nums[i-1];
        }
        for(int i=n;i>=1;i--){
            sub[i]=sub[i+1]*nums[i-1];
        }
        int[] ret=new int[n];
        for(int i=1;i<=n;i++) ret[i-1]=pre[i-1]*sub[i+1];
        return ret;
    }



    public static int pivotIndex(int[] nums) {
        int n=nums.length;
        int[] dp=new int[n+1];
        for(int i=1;i<=n;i++){
            dp[i]=dp[i-1]+nums[i-1];
        }
        int ret=-1;
        for(int i=1;i<=n;i++){
            if(dp[i-1]==dp[n]-dp[i])
            {
                if(ret==-1) ret=i-1;
                else ret=Math.min(ret,i-1);
            }
        }
        return ret;
    }


}
