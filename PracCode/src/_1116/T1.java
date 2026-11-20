package _1116;


public class T1 {
    class TreeNode {
        int val;
        TreeNode left;
        T1.TreeNode right;

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
    public TreeNode pruneTree(TreeNode root) {
        if(!dfs(root)) return null;
        return root;
    }

    public boolean dfs(TreeNode root){
        if(root.left==null && root.right==null){
            return root.val==1;
        }
        boolean left=false,right=false;
        if(root.left!=null) {
            left = dfs(root.left);
            if(!left) root.left=null;
        }
        if(root.right!=null) {
            right = dfs(root.right);
            if(!right) root.right=null;
        }
        if(root.val==0 && !left && !right) return false;
        return true;
    }
}
