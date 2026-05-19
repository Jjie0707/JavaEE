public class demo0 {
    public String convert(String s, int numRows) {
        String[] strs=new String[numRows];
        char[] str=s.toCharArray();
        for(int i=0;i<str.length;i++){

        }
    }
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        int ret=0,pre=0;
        for(int num:timeSeries){
            if(num<=pre && pre!=0) ret+=(num-(pre-duration));
            else {
                ret+=duration;
                pre=num+duration;
            }
            pre=num+duration;
        }
        return ret;
    }
    public String modifyString(String s) {
        char pre=0;
        char[] str=s.toCharArray();
        int n=str.length;
        for(int i=0;i<n;i++){
            if(str[i]=='?'){
                if(i+1>=n){
                    if(pre==0) str[i]='a';
                    else {
                        char tem='a';
                        while(tem==pre) tem++;
                        str[i]=tem;
                    }
                }else {
                    char tem='a';
                    while(tem==pre || tem==str[i+1]) tem++;
                    str[i]=tem;
                }
            }
            pre=str[i];
        }
        return new String(str);
    }
    public int[] missingTwo(int[] nums) {
        int num = 0;int n=nums.length+2;
        for (int i = 1; i <=n ;i++) num^=i;
        int tem=num;
        for (int j : nums) tem ^= j;

        int pos=0;
        while(((tem>>pos)&1)!=1){
            pos++;
        }
        int[] ret=new int[2];
        //走到这里找到了tem为1的最低比特位
        for(int i=1;i<=n;i++){
            if(((i>>pos)&1)==1) ret[0]^=i;
            else ret[1]^=i;
        }
        for(int j:nums){
            if(((j>>pos)&1)==1) ret[0]^=j;
            else ret[1]^=j;
        }
        return ret;
    }
}
