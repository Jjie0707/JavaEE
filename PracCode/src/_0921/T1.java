package _0921;

import java.util.Stack;

public class T1 {

    public String removeDuplicates(String s) {
        Stack<Character> S=new Stack<>();
        char[] cs=s.toCharArray();
        for(char tem:cs){
            if(S.isEmpty()){
                S.push(tem);
            }else{
                while (!S.isEmpty() && S.peek() == tem) {
                    S.pop();
                }
            }
        }
        StringBuilder ret=new StringBuilder();
        while(!S.isEmpty()) ret.append(S.pop());
        return ret.reverse().toString();
    }
}
