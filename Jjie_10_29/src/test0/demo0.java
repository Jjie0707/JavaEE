package test0;

import java.util.Scanner;

public class demo0 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s1=in.next();
        String s2=in.next();
        char[] str1=s1.toCharArray(),str2=s2.toCharArray();
        int[] chars=new int[26];
        for(int i=0;i<str1.length;i++){
            chars[str1[i]-'A']++;
        }
        for(int i=0;i<str2.length;i++){
            chars[str2[i]-'A']--;
        }
        boolean flag=true;
        for(int i=0;i<26;i++){
            if(chars[i]<0){
                System.out.println("No");
                flag=false;
            }
        }
        if(flag){
            System.out.println("Yes");
        }
    }
}
