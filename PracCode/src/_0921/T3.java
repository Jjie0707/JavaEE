package _0921;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class T3 {
    public static void main(String[] args) {
        System.out.println(calculate(" 3/2 "));
    }
    public static  int calculate(String S) {
        char[] cs=S.toCharArray();
        Stack<Integer> s1=new Stack<>();
        Stack<Character> s2=new Stack<>();s2.push('+');
        int ret=0;
        for(int i=0;i<cs.length;){
            char tem=cs[i];
            if(tem==' ') {
                i++;
                continue;
            }
            if(tem=='+' || tem=='-' || tem=='/' || tem=='*') {
                s2.push(tem);
                i++;
            }
            else{
                int num=0;
                while(i<cs.length && cs[i]>='0' && cs[i]<='9'){
                    num*=10;num+=(cs[i]-'0');i++;
                }
                if(s2.peek()=='*' || s2.peek()=='/'){
                    char sy=s2.pop();
                    if(sy=='/') num=s1.pop()/num;
                    else num=s1.pop()*num;
                }
                s1.push(num);
            }
        }
        while(!s1.isEmpty() && !s2.isEmpty()){
            char sy=s2.pop();
            if(sy=='+') ret=ret+s1.pop();
            else ret=ret-s1.pop();
        }
        return ret;
    }
}
