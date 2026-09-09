package _0811;

public class T1 {
    public String longestCommonPrefix(String[] strs) {
        if(strs.length==1) return strs[0];
        else{
            StringBuilder ret=new StringBuilder();
            String s=strs[0];int length=s.length( );
            for(int i=0;i<length;i++){
                char c=s.charAt(i);
                //一轮循环加一个字符
                for (int j = 1; j < strs.length; j++) {
                    if(i>=strs[j].length() || strs[j].charAt(i)!=c){
                        return ret.toString();
                    }
                }
                ret.append(c);
            }
            return ret.toString();
        }
    }
}
