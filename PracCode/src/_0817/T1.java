package _0817;

import java.util.ArrayList;

public class T1 {
    public static void main(String[] args) {
        String a="11",b="1";
        System.out.println();
        addBinary(a,b);
    }
    public static String addBinary(String a, String b) {
        char[] sa=a.toCharArray(),sb=b.toCharArray();
        StringBuilder ret=new StringBuilder();
        int tem=0;
        int i=sa.length-1,j=sb.length-1;
        while(i>=0 && j>=0){
            int na=sa[i]=='1' ? 1 : 0,nb=sb[j]=='1' ? 1 : 0 ;
            i--;j--;
            int num=na+nb+tem;tem=0;
            if(num>=2){
                num-=2;tem=1;
            }
            ret.append((char)(num+'0'));
        }
        while(i>=0){
            int na=sa[i]=='1' ? 1 : 0;
            int num=na+tem;tem=0;
            if(num>=2){
                num-=2;tem=1;
            }
            ret.append((char)(num+'0'));
            i--;
        }
        while(j>=0){
            int nb=sb[j]=='1' ? 1 : 0;
            int num=nb+tem;tem=0;
            if(num>=2){
                num-=2;tem=1;
            }
            ret.append((char)(num+'0'));
            j--;
        }
        if(tem==1) ret.append('1');
        ret.reverse();
        return ret.toString();
    }
}
