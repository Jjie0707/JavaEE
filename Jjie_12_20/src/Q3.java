public class Q3 {
    static void main() {
        int[] nums={4,5,1,3,2};
        System.out.println(maxWater(nums));
    }
    static public long maxWater (int[] arr) {
        // write code here
        int ret=0;
        int[] left=new int[arr.length],right=new int[arr.length];
        int max=Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            max=Math.max(max,arr[i]);
            left[i]=max;
        }
        max=Integer.MIN_VALUE;
        for(int i=arr.length-1;i>=0;i--){
            max=Math.max(max,arr[i]);
            right[i]=max;
        }
        for(int i=0;i<arr.length;i++){
            ret=ret+Math.min(left[i],right[i])-arr[i];
        }
        return ret;
    }
}
