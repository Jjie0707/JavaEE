import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str=in.next();
        char[] s=str.toCharArray();
        long ret=Integer.MAX_VALUE;
        for(int i='a';i<='z';i++){
            long tem=0;
            for(char c:s){
                tem+=Math.min(Math.abs(c-i),26-Math.abs(c-i));
            }
            ret=Math.min(ret,tem);
        }
        System.out.println(ret);
    }
}
