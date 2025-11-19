import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s=in.next();
        char[] str=s.toCharArray();
        int ret=Integer.MAX_VALUE;
        for(int i=1;i<str.length-1;i++){
            if(str[i-1]==str[i] || str[i+1]==str[i]) ret=2;
            else if(str[i-1]==str[i+1]) ret=3;
        }
        System.out.println(ret==Integer.MAX_VALUE ? -1 : ret);
    }

}
