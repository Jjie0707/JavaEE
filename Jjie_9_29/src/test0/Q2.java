package test0;

import java.util.ArrayList;

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

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class Q2 {

    public boolean isValidBST(TreeNode root) {

        if(root==null || (root.left==null && root.right==null)) return true;
        TreeNode left=root.left,right=root.right;
        if(left==null && right==null) return true;
        else if(right==null){
            if(left.val>=root.val) return false;
            ArrayList<Integer> list=new ArrayList<>();
            order(left,list);
            return isSameTree(list);
        }else if(left==null){
            ArrayList<Integer> list=new ArrayList<>();
            order(right,list);
            return isSameTree(list);
        }else{
            ArrayList<Integer> list=new ArrayList<>();
            order(root,list);
            return isSameTree(list);
        }
    }
    public boolean isSameTree(ArrayList<Integer> list){
        ArrayList<Integer> tem=new ArrayList<>();
        tem.addAll(list);
        tem.sort((e1,e2)->e1-e2);
        for(int i=0;i<tem.size();i++){
            if(tem.get(i)!=list.get(i)) return false;
        }
        return true;
    }
    public void order(TreeNode root,ArrayList<Integer> list){
        if(root==null) return;
        order(root.left,list);
        list.add(root.val);
        order(root.right,list);

    }

//    public boolean isValidBST(TreeNode root) {
//        if(root==null || (root.left==null && root.right==null)) return true;
//        TreeNode left=root.left,right=root.right;
//        if(left==null && right==null) return true;
//        else if(right==null){
//            if(left.val>=root.val) return false;
//            return isValidBST(left);
//        }else if(left==null){
//            if(right.val<=root.val) return false;
//            return isValidBST(right);
//        }else{
//            if(left.val>=root.val || right.val<=root.val) return false;
//            return isValidBST(right) && isValidBST(left);
//        }
//    }


    public TreeNode sortedArrayToBST(int[] nums) {
        if(nums==null || nums.length==0) return null;
        int left=0,right=nums.length-1;

        return sortedArrayToBST_(nums,left,right);
    }

    public TreeNode sortedArrayToBST_(int[] nums,int left,int right){
        if(left<0 || right>=nums.length || left>right){
            return null;
        }
        int mid=left+(right-left)/2;
        TreeNode root=new TreeNode(nums[mid]);
        root.left=sortedArrayToBST_(nums,left,mid-1);
        root.right=sortedArrayToBST_(nums,mid+1,right);
        return root;
    }
}
