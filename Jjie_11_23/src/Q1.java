import java.util.LinkedList;
import java.util.Scanner;

public class Q1 {
    public static int n,m;
    public static int x1,y1,x2,y2;
    public static char[][] map;
    public static int[][] dis;
    public static int[] bx ={-1,1,0,0};
    public static int[] by ={0,0,-1,1};

    private static int bfs(){
        if(map[x2][y2]=='*') return -1;
        LinkedList<int[]> q=new LinkedList<>();
        q.addLast(new int[] {x1,y1});
        dis[x1][y1]=0;
        while(!q.isEmpty()){
            int[] tem=q.removeFirst();
            for(int i=0;i<4;i++){
                int x=tem[0],y=tem[1];
                int a=x+bx[i];
                int b=y+by[i];
                if(a>=0 && a<n && b>=0 && b<m && map[a][b]=='.' && dis[a][b]==-1){
                    q.addLast(new int[] {a,b});
                    dis[a][b]=dis[x][y]+1;
                    if(a==x2 && b==y2){
                        return dis[x2][y2];
                    }
                }
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        n=in.nextInt();m=in.nextInt();
        x1=in.nextInt()-1;y1=in.nextInt()-1;
        x2=in.nextInt()-1;y2=in.nextInt()-1;
        map=new char[n][];
        dis=new int[n][m];
        for(int i=0;i<n;i++){
            char[] tem=in.next().toCharArray();
            map[i]=tem;
        }
        for(int i=0;i<n;i++) for(int j=0;j<m;j++) dis[i][j]=-1;

        System.out.println(bfs());
    }
}
