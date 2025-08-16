package test0;

public class Q2 {
    public static void main(String[] args) {
//        int[][] matrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};int  target = 5;
        int[][] matrix={{-5}};int target=-5;
        System.out.println(searchMatrix(matrix,target));
    }
//    public static boolean searchMatrix(int[][] matrix, int target) {
//        int l=matrix.length,w=matrix[0].length;
//        if(target<matrix[0][0] || target>matrix[l-1][w-1]) return false;
//        for(int i=0;i<matrix.length;i++){
//            if(target>=matrix[i][0] && target<=matrix[i][w-1]){
//                int left=0,right=w-1;
//                while (left <= right) {
//                    int mid = left + (right - left) / 2;
//                    if (matrix[i][mid] == target) {
//                        return true;
//                    } else if (matrix[i][mid] < target) {
//                        left = mid + 1;
//                    } else {
//                        right = mid - 1;
//                    }
//                }
//            }
//        }
//        return false;
//    }
    public static boolean searchMatrix(int[][] matrix, int target) {
        int l=matrix.length,w=matrix[0].length;
        if(target<matrix[0][0] || target>matrix[l-1][w-1]) return false;
        int x=w-1,y=0;
        while(y<l && x>=0){
            if(matrix[x][y]==target) return true;
            else if(matrix[x][y]>target) x--;
            else y++;
        }
        return false;
    }
}
