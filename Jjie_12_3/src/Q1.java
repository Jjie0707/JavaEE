public class Q1 {

    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val) {
          this.val = val;
        }
    }

    private int ret;

    public int maxPathSum (TreeNode root) {
        // write code here
        ret=Integer.MIN_VALUE;
        func(root);
        return ret;
    }
    private int func(TreeNode root){
        if(root==null) return 0;
        int l=Math.max(func(root.left),0);
        int r=Math.max(func(root.right),0);
        ret=Math.max(root.val+l+r,ret);
        //这一步是便利了所有的可能的路径 所以尝试更新答案
        return Math.max(ret+l,ret+r);
        //这一步是向上返回最大的一条单链便于根节点统计最大路径和
    }
}
