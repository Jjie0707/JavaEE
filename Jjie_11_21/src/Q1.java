import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[] nums=new int[n+1];
        for(int i=1;i<=n;i++) nums[i]=in.nextInt();
        boolean[] st=new boolean[n+1];
        HashMap<Integer,Integer> m=new HashMap<>();
        for(int i=1;i<=n;i++){
            if(!st[i]){
                if(nums[i]==i){
                    m.put(i,i);
                    st[i]=true;
                }else{
                    int next=nums[i];
                    st[i]=true;
                    if(st[next]){
                        int key=next;
                        int end=m.get(key);
                        m.remove(key);
                        m.put(i,end);
                    }else{
                        while(!st[next]){
                            if(nums[next]==next){
                                m.put(i,next);st[next]=true;
                                break;
                            }else{
                                st[next]=true;
                                next=nums[next];
                            }
                        }
                    }

                }
            }
        }
        int ret=1;
        int size=m.size();
        for(int i=1;i<=size;i++){
            ret=ret*i;
        }
        System.out.println(ret);
    }
}
