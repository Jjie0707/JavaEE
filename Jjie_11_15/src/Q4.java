import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Q4 {
    static void main() {
        int[][] mat={{1,2,3,1,8},{2,3,4,5,6}};
        System.out.println(Arrays.toString(findPeakGrid(mat)));
    }
    static public int[] findPeakGrid(int[][] mat) {
        int m=mat.length,n=mat[0].length;
        HashSet<int[]> ret=new HashSet<>();
        for(int i=0;i<m;i++){
            int left=0,right=n-1;
            while(left<right){
                if(mat[i][left]>mat[i][left+1]) ret.add(new int[] {i,left});
                int mid=left+(right-left)/2;
                if(mat[i][mid]<mat[i][mid+1]) left=mid+1;
                else right=mid;
            }
            ret.add(new int[] {i,left});
        }
        //走到这里是不是能找到所有可能的峰值？

        for(int[] arr:ret){
            int x=arr[0],y=arr[1];
            if(x==0){
                if(x+1<m && mat[x][y]>mat[x+1][y]) return arr;
                else if(x+1>=m) return arr;
            }else if(x==m-1){
                if(mat[x][y]>mat[x-1][y]) return arr;
            }else{
                if(mat[x][y]>mat[x-1][y] && mat[x][y]>mat[x+1][y]) return arr;
            }
        }
        return new int[0];
    }
}
