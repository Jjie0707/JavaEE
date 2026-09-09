package _0817;

public class T2 {
    public static void main(String[] args) {
        String s1="21",s2="5";
        System.out.println(multiply(s1,s2));
    }
    public static  String multiply(String num1, String num2) {
        char[] s1=num1.toCharArray(),s2=num2.toCharArray();
        int len2=s2.length;
        String s="0";
        int count=0;
        for(int i=len2-1;i>=0;i--){
            String tem=mul(s1,s2[i],count);
            s=add(s,tem);
            count++;
        }
        return s;
    }
    public static String mul(char[] s1,char s2,int count){
        StringBuilder ret=new StringBuilder();
        int res=0,num1=s2-'0';
        for(int i=s1.length-1;i>=0;i--){
            int num2=s1[i]-'0';
            int tem=num2*num1+res;res=0;
            if(tem>=10){
                res=tem/10;tem=tem%10;
            }
            ret.append((char)(tem+'0'));
        }
        if(res>0) ret.append((char)(res+'0'));
        ret.reverse();
        for(int i=count;i>0;i--) ret.append('0');
        return ret.toString();
    }
    public static String add(String s1,String s2){
        char[] cs1=s1.toCharArray(),cs2=s2.toCharArray();
        StringBuilder ret=new StringBuilder();
        int i=cs1.length-1,j=cs2.length-1;
        int tem=0;
        while(i>=0 && j>=0){
            int num1=cs1[i]-'0',num2=cs2[j]-'0';
            int num=tem+num1+num2;tem=0;
            tem=num/10;num=num%10;
            ret.append((char)(num+'0'));
            i--;j--;
        }
        while(i>=0){
            int num1=cs1[i]-'0';
            int num=tem+num1;tem=0;
            tem=num/10;num=num%10;
            ret.append((char)(num+'0'));
            i--;
        }
        while(j>=0){
            int num2=cs2[j]-'0';
            int num=tem+num2;tem=0;
            tem=num/10;num=num%10;
            ret.append((char)(num+'0'));
            j--;
        }
        if(tem==1) ret.append('1');
        ret.reverse();
        return ret.toString();
    }
}
