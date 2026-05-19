public class demo0 {
    public int singleNumber(int[] nums) {
        int ret=0;
        for(int i=0;i<32;i++){
            int tem=0;
            for(int num:nums){
                if(((num>>i)&1)==1) tem++;
            }
            tem%=3;
            ret=ret|(tem<<i);
        }
        return ret;
    }

    public int getSum(int a, int b) {
        boolean flag=false;
        int num1=0,num2=0;
        int ret=0;
        for(int i=0;i<32;i++){
            num1=(a>>i)&1;num2=(b>>i)&1;
            if(num1==1&&num2==1){
                if(flag) ret=ret|(1<<i);
                flag=true;
            }else if(num1==1 || num2==1){
                if(!flag){
                    ret=ret|(1<<i);
                }
            }else{
                if(flag) ret=ret|(1<<i);
                flag=false;
            }
        }
        return ret;
    }
    public int missingNumber(int[] nums) {
        int num=nums.length;
        for(int i=0;i<nums.length;i++){
            num=(num^i)^nums[i];
        }
        return num;
    }
    public boolean isUnique1(String astr) {
        char[] s = astr.toCharArray();
        int num = 0;
        for (char c : s) {
            int tem = (1 << (c - 'a'));
            if ((((num >> (c - 'a')) & 1)) == 1) return false;
            num = num | tem;
        }
        return true;
    }

    public boolean isUnique(String astr) {
        int[] arr=new int[26];
        char[] s=astr.toCharArray();
        for (char tem : s) {
            arr[tem - 'a']++;
            if (arr[tem - 'a'] > 1) return false;
        }
        return true;
    }
}
