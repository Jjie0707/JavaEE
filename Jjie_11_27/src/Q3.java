import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Q3 {
    public static HashSet<String> list;
    public static char[] s;

    public static void swap(int index1,int index2){
        char tem=s[index1];
        s[index1]=s[index2];
        s[index2]=tem;
    }
    public static void func(int count){
        if(count<=0) return;
        int index=s.length-count;
        for(int i=s.length-count;i<s.length;i++){
            swap(i,index);
            func(count-1);
            swap(i,index);
        }
        if(count==1) list.add(new String(s));
    }

    public static ArrayList<String> Permutation (String str) {
        s=str.toCharArray();
        list=new HashSet<>();

        // write code here
        if(str.isEmpty()){
            list.add("");
        }else{
            func(str.length());
        }

        return new ArrayList<>(list);
    }

    public static void main(String[] args) {
        ArrayList<String> tem=Permutation("abc");
        for(String s:tem) System.out.println(s);
    }
}

