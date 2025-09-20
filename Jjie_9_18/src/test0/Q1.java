package test0;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q1 {

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

    //  有序数组转化为平衡搜索二叉树
    public TreeNode sortedArrayToBST(int[] nums) {

    }



    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) return ret;

        LinkedList<TreeNode> deque = new LinkedList<>();

        deque.addLast(root);
        while (true) {
            int tem=deque.size();
            List<Integer> list=new ArrayList<>();
            for(;tem>0;tem--){
                TreeNode cur=deque.removeFirst();
                if(cur!=null){
                    list.add(cur.val);
                    deque.addLast(cur.left);
                    deque.addLast(cur.right);
                }
            }
            ret.add(list);
            if(deque.isEmpty()) break;
        }
        return ret;
    }

    private static int ret;

    public int diameterOfBinaryTree(TreeNode root){
        ret=0;
        if(root==null) return 0;
        diameterOfBinaryTree_(root);
        return ret;
    }
    public int diameterOfBinaryTree_(TreeNode root){
        if(root==null) return 0;
        int left = diameterOfBinaryTree_(root.left);
        int right = diameterOfBinaryTree_(root.right);

        if (left + right >= ret) ret = right + left;
        return 1 + Math.max(left, right);
    }
}
