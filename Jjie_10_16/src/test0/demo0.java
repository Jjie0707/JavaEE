package test0;

import java.util.Scanner;

public class demo0 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String cur=in.nextLine();
        char[] str=cur.toCharArray();
        if(cur.length()<=3) {
            System.out.println(cur);
        }
        else{
            StringBuilder sb = new StringBuilder();
            int count=0;
            for(int i=str.length-1;i>=0;i--){
                sb.append(str[i]);
                count++;
                if(count==3){
                    sb.append(',');
                    count=0;
                }
            }
            System.out.println(sb.reverse());
        }
    }
}
