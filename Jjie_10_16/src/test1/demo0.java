package test1;

import java.util.Scanner;

public class demo0 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
//        7
//        8 9 2 5 4 7 1
        int n=in.nextInt();
        int[] nums=new int[n];
        int ret=0,min=Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            int cur=in.nextInt();
            if(cur<=min){
                min=cur;
            }else{
                ret=Math.max(ret,cur-min);
            }
        }
        System.out.println(ret);
    }






    public int getLongestPalindrome (String A) {
        // write code here
        int ret=0;
        int length=A.length();
        for(int i=0;i<length;i++){
            for(int j=i;j<length;j++){
                String tem=A.substring(i,j+1);
                if(j-i+1>=ret && func(tem,j-i+1)){
                    ret=j-i+1;
                }
            }
        }
        return ret;
    }
    public boolean func(String str,int length){
        char[] strs=str.toCharArray();
        int left=0,right=length-1;
        while(left<right){
            if(strs[left]!=strs[right]) return false;
            left++;right--;
        }
        return true;
    }
}
