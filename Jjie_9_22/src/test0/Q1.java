package test0;

import java.util.LinkedList;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String input=in.next();
            //走到这里获取到了当前的字符串

            char[] cs=input.toCharArray();
            LinkedList<Character> stack=new LinkedList<>();
            for(char c:cs){
                if(stack.isEmpty() || stack.getLast()!=c) stack.addLast(c);
                else if(stack.getLast()==c){
                    stack.removeLast();
                }
            }
            StringBuilder sb=new StringBuilder("");
            while(!stack.isEmpty()) sb.append(stack.removeFirst());
            System.out.println(sb);
        }
    }
}
