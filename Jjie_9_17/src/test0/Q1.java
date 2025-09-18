package test0;

import java.util.ArrayList;
import java.util.List;

public class Q1 {
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     */
    class TreeNode{
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
    public boolean isSymmetric(TreeNode root){
        if(root==null) return true;
        else{
            if(root.left==null && root.right==null) return true;
            else if(root.left!=null && root.right==null) return false;
            else if(root.left==null) return false;
            else if(root.left.val!=root.right.val) return false;
            else {
                return isSymmetric_(root.left.left,root.right.right) && isSymmetric_(root.left.right,root.right.left);
            }
        }
    }
    public boolean isSymmetric_(TreeNode root1,TreeNode root2){
        if(root1==null && root2==null) return true;
        else if(root1==null && root2!=null) return false;
        else if(root1!=null && root2==null) return false;
        else if(root1.val != root2.val) return false;
        else {
            return isSymmetric_(root1.left,root2.right) && isSymmetric_(root1.right,root2.left);
        }
    }

//    输入：root = [4,2,7,1,3,6,9]
//    输出：[4,7,2,9,6,3,1]

    public TreeNode invertTree(TreeNode root) {
        if(root==null) return root;
        else if(root.left==null) {
            root.left = root.right;
            root.right=null;
            return root;
        }else if(root.right==null){
            root.right = root.left;
            root.left=null;
            return root;
        }
        //特殊情况特殊处理
        invertTree_(root);
        return root;
    }
    public void invertTree_(TreeNode root){
        if(root==null) return;
        invertTree(root.left);
        invertTree(root.right);
        TreeNode tem=root.left;
        root.left=root.right;
        root.right=tem;

    }

    public int maxDepth(TreeNode root) {
        if(root==null) return 0;
        int left=maxDepth(root.left);
        int right=maxDepth(root.right);

        return Math.max(left,right)+1;

    }


    class Solution {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> ret=new ArrayList<>();

            if(root==null) return ret;

            this.inorderTraversal(root,ret);

            return ret;
        }
        public void inorderTraversal(TreeNode root,List ret){
            if(root==null) return;

            int cur=root.val;

            inorderTraversal(root.left,ret);

            ret.add(cur);

            inorderTraversal(root.right,ret);
        }
    }
}
