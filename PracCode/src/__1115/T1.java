package __1115;

public class T1 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    int ret;
    public int sumNumbers(TreeNode root) {
        ret=0;
        dfs(root,0);
        return ret;
    }

    private void dfs(TreeNode root,int pre) {
        if(root.left==null && root.right==null) {
            ret=ret+pre;
            return;
        }
        int cur=root.val;
        int tem=pre*10+cur;
        if(root.left!=null) dfs(root.left,tem);
        if(root.right!=null) dfs(root.right,tem);
    }
//    private int fun(int num){
//        int ret=1;
//        while(num>=10){
//            num/=10;ret++;
//        }
//        return ret;
//    }
}
