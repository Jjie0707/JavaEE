import java.util.HashMap;
import java.util.Scanner;

public class Q1 {
    private static int func(int n){
        int ret=1;
        for(int i=1;i<=n;i++){
            ret*=i;
        }
        return ret;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[] nums=new int[n+1];
        for(int i=1;i<=n;i++) nums[i]=in.nextInt();
        boolean[] st=new boolean[n+1];
        HashMap<Integer,Integer> m=new HashMap<>();
        for(int i=1;i<=n;i++){
            if(!st[i]){//如果这个数没有被访问过才继续

                if(nums[i]==i){//没有诉求的情况
                    st[i]=true;
                    m.put(i,1);
                }else{
                    int len=0;
                    int cur=i;
                    while(!st[cur]){
                        st[cur]=true;
                        len++;
                        if(nums[cur]==cur){
                            break;
                        }else{
                            cur=nums[cur];
                        }
                    }
                    //走到这里有个诉求被使用的情况
                    if(m.containsKey(cur)){
                        int count=m.get(cur);
                        m.remove(cur);
                        len+=count;
                    }
                    m.put(i,len);
                }
            }
        }
        int ret=func(n);
        for(int tem:m.values()){
            ret=ret/func(tem);
        }
        System.out.println(ret);
    }
}
