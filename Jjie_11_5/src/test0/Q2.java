package test0;

import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        int[] pres=new int[n+1];
        pres[0]=0;
        for(int i=0;i<n;i++){
            int tem=in.nextInt();
            pres[i+1]=pres[i]+tem;
        }
        //走到这里完成了前缀和数组的初始化,且长度至少为2
        int min=pres[0];
        int ret=Integer.MIN_VALUE;
        for(int i=2;i<pres.length;i++){
            int tem=pres[i];
            ret=Math.max(ret,tem-min);
            if(ret<=min) min=ret;
        }
        System.out.println(ret);
    }
}
