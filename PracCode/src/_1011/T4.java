package _1011;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class T4 {
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
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> ret=new ArrayList<>();
        if(root==null) return ret;
        LinkedList<TreeNode> q=new LinkedList<>();q.addLast(root);
        int depth=getDepth(root);
        for(int i=0;i<depth;i++){
            int tem=Integer.MIN_VALUE;
            int count=q.size();
            for(;count>0;count--){
                TreeNode cur=q.removeFirst();
                if(cur.left!=null) q.addLast(cur.left);
                if(cur.right!=null) q.addLast(cur.right);
                tem=Math.max(tem,cur.val);
            }
            ret.addLast(tem);
        }
        return ret;
    }
    public int getDepth(TreeNode root){
        if(root==null) return 0;
        if(root.left==null && root.right==null) return 1;
        int left=getDepth(root.left);
        int right=getDepth(root.right);
        return 1+Math.max(left,right);
    }
}
