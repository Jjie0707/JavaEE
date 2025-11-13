package test0;

import java.io.*;
import java.util.*;

public class Q1 {
    public static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static Read in = new Read();
    public static HashMap<Integer,Integer> cnt;  //用来统计入度的哈希表
    public static HashMap<Integer,ArrayList<Integer>> edges;  //用来建图
    public static int n,m;
    public static void main(String[] args) throws IOException {
        // 写代码
        cnt=new HashMap<>();
        edges=new HashMap<>();

        n=in.nextInt();m= in.nextInt();
        for(int i=0;i<m;i++){
            int a=in.nextInt(),b= in.nextInt();
            cnt.put(b,cnt.getOrDefault(b,0)+1);
            if(!edges.containsKey(a)) edges.put(a,new ArrayList<>());
            edges.get(a).add(b);
        }
        LinkedList<Integer> q=new LinkedList<>();
        for(int tem:edges.keySet()){
            if(!cnt.containsKey(tem)){
                q.addLast(tem);
            }
        }
        //走到这里建图完成
        int[] ret=new int[n];
        int pos=0;
        while(!q.isEmpty()){
            int tem=q.removeFirst();
            ret[pos++]=tem;
            for(int a:edges.get(tem)){
                cnt.put(a,cnt.get(a)-1);
                if(cnt.get(a)==0) {
                    q.addLast(a);
                    cnt.remove(a);
                    ret[pos++]=a;
                }
            }
        }
        if(pos==n){
            for(int i=0;i<n--;i++){
                System.out.print(ret[i]+" ");
            }
            System.out.print(ret[n-1]);
        }else{
            System.out.print(-1);
        }
        out.close();
    }
}


class Read // 自定义快速读入
{
    StringTokenizer st = new StringTokenizer("");
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    String next() throws IOException {
        while (!st.hasMoreTokens()) {
            st = new StringTokenizer(bf.readLine());
        }
        return st.nextToken();
    }

    String nextLine() throws IOException {
        return bf.readLine();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }
}
