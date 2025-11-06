package test0;

import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        String str=in.next();
        char[] s=str.toCharArray();
        int n=s.length;
        boolean flag=false;
        for(int i=1;i<n;i++){
            if(s[i]!=s[0]){
                flag=true;
                break;
            }
        }
        if(flag){
            flag=false;
            int left=0,right=n-1;
            while(left<right){
                if(s[left] != s[right]){
                    flag=true;
                    break;
                }
                left++;right--;
            }
            if(flag) System.out.println(n);
            else System.out.println(n-1);
        }else{
            System.out.println(0);
        }
    }
}
