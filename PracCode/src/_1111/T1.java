package _1111;

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

    public boolean evaluateTree(TreeNode root) {
        return dfs(root);
    }
    public boolean dfs(TreeNode root){
        if(root.val==1 || root.val==0)
            return root.val==1;
        boolean left=dfs(root.left);
        //剪枝
        if(!left && root.val==3) return false;
        if(left && root.val==2) return true;
        boolean right=dfs(root.right);

        if(root.val==2) return left || right;
        else return left && right;
    }
}
