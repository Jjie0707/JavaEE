package test0;

import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a,h,b,k;
        a=in.nextInt();
        h=in.nextInt();
        b=in.nextInt();
        k=in.nextInt();
        long ret=0;
        while(h>0 && k>0){
            ret=ret+a+b;
            h-=b;
            k-=a;
        }
        if(h>0) ret+=a*10;
        if(k>0) ret+=b*10;
        System.out.println(ret);
    }
}
