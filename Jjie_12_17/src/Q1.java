import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Q1 {
    public static int[] A=new int[168];
    public static int ret=0;
    public static ArrayList<Integer>[] arr;
    public static HashSet<Integer> s=new HashSet<>();

    public static boolean func(int n){
        if(n==1) return false;
        else if(n==2) return true;
        for(int i=2;i<=Math.pow(n,0.5)+1;i++){
            if(n%i==0) return false;
        }
        return true;
    }
    public static int dfs(int index){
        if(index>= arr.length) return 0;
        ArrayList<Integer> list=arr[index];
        int re=-1,min=-1;
        for(int cur:list){
            if(!s.contains(cur)){
                s.add(cur);
                int tem=dfs(index+1);
                if(tem!=-1){
                    if(min==-1) min=tem;
                    else min=Math.min(min,tem);
                    if(re==-1) re=min+cur;
                    else re=Math.min(re,min+cur);
                }
                s.remove(cur);
            }
        }
        return re;
    }
    public static void main(String[] args) {
        if(A[0]==0){
            int index=0;
            for(int i=2;i<=1000;i++){
                if(func(i)) A[index++]=i;
            }
        }
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[] nums=new int[n];
        for(int i=0;i<n;i++) nums[i]=in.nextInt();
        arr=new ArrayList[n];
        for(int i=0;i<n;i++){
            int tem=nums[i];
            ArrayList<Integer> list=new ArrayList<>();
            for(int num:A) {
                if (tem % num == 0) list.add(num);
            }
            arr[i]=list;
        }
        ret=dfs(0);
        System.out.println(ret);
    }
}
