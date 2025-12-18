import java.util.HashMap;
import java.util.PriorityQueue;

public class Q2 {

    static void main() {
        int[][] map={{2,0,0,0},{0,-1,-1,0},{0,-1,1,0},{0,0,0,0}};
        System.out.println(countPath(map,4,4));
    }
//    static void main() {
//        int[][] map={{0,1,0},{2,0,0}};
//        System.out.println(countPath(map,2,3));
//    }
    public static int[][] pos =new int[][]{{-1,0},{1,0},{0,-1},{0,1} };

    public static int dfs(boolean[][] visit,int[][] dis,int[][] map,int x,int y){
        if(dis[x][y]!=0) return dis[x][y];
        if(map[x][y]==2) return dis[x][y]=0;
        int xi,yi;
        visit[x][y]=true;
        int min=Integer.MAX_VALUE;
        for(int i=0;i<4;i++){
            xi=x+pos[i][0];yi=y+pos[i][1];
            if(0<=xi && xi<map.length && 0<=yi && yi<map[0].length
                    && map[xi][yi]!=-1 && !visit[xi][yi]){
                min=Math.min(dfs(visit,dis,map,xi,yi),min);
            }
        }
        visit[x][y]=false;
        if(min!=Integer.MAX_VALUE){
            dis[x][y]=1+min;
        }else{
            dis[x][y]=min;
        }
        return dis[x][y];
    }
    public static  int countPath (int[][] CityMap, int n, int m) {
        // write code here
        int x1=-1,y1=-1;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(CityMap[i][j]==1){
                    x1=i;y1=j;
                }
            }
        }//上面找到了经理和商家的位置
        boolean[][] visit=new boolean[n][m];
        int[][] dis=new int[n][m];
        dfs(visit,dis,CityMap,x1,y1);
        int xi,yi;
        PriorityQueue<Integer> heap=new PriorityQueue<>();
        for(int i=0;i<4;i++){
            xi=x1+pos[i][0];yi=y1+pos[i][1];
            if(0<=xi && xi<CityMap.length && 0<=yi && yi<CityMap[0].length
                    && CityMap[xi][yi]!=-1){
                heap.offer(dis[xi][yi]);
            }
        }
        int min=Integer.MAX_VALUE,count=0;
        if(!heap.isEmpty()) {
            min = heap.poll();
            count++;
        }
        while(!heap.isEmpty() && heap.peek()==min){
            heap.poll();
            count++;
        }
        return count;
    }
}
