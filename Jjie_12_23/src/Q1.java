import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Q1 {

    public static int ret=0,na,nb,nc,nd;
    public static HashMap<Integer, ArrayList<Integer>> m;
    public static char[] cur=new char[12];

    public static boolean func(int index) {
        ArrayList<Integer> list = m.get(index);
        if (list == null)
            return true;
        for (int tem : list) {
            if (cur[index] != cur[tem]) return false;
        }
        return true;
    }

    public static void dfs(int index){
        if(index>=12) return ;
        for(char c='A';c<='D';c++){
            if(c=='A') na--;else if(c=='B') nb--; else if(c=='C') nc--;else nd--;
            cur[index]=c;
            if(na>=0 && nb>=0 &&nc>=0 &&nd>=0 && func(index)){
                if(index==11) ret++;
                else dfs(index+1);
            }
            if(c=='A') na++;else if(c=='B') nb++; else if(c=='C') nc++;else nd++;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        na=in.nextInt();nb=in.nextInt();nc=in.nextInt();nd=in.nextInt();
        m=new HashMap<>();
        int t=in.nextInt();
        for(int i=0;i<t;i++){
            int x=in.nextInt(),y=in.nextInt();
            if(y>x){//确保x是比较大的
                int tem=x;
                x=y;y=tem;
            }
            if(m.containsKey(x-1)) m.get(x-1).add(y-1);
            else{
                ArrayList<Integer> list=new ArrayList<>();
                list.add(y-1);
                m.put(x-1,list);
            }
        }
        dfs(0);
        System.out.println(ret);
    }
}
