package _0921;

import java.util.Stack;

public class T2 {
    public boolean backspaceCompare(String s, String t) {
        Stack<Character> s1,s2;
        char[] c1=s.toCharArray(),c2=t.toCharArray();
        s1=func(c1);s2=func(c2);
        if(s1.size()!=s2.size()) return false;
        while(!s1.isEmpty() && !s2.isEmpty()){
            if(s1.pop()!=s2.pop()) return false;
        }
        return true;
    }
    public Stack<Character> func(char[] s){
        Stack<Character> ret=new Stack<>();
        for(char tem:s){
            if(tem!='#') ret.push(tem);
            else ret.pop();
        }
        return ret;
    }
}
