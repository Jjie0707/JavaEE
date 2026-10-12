package _1011;

import java.util.LinkedList;
import java.util.List;

public class T2 {
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

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ret=new LinkedList<>();if(root==null) return ret;

        LinkedList<TreeNode> q=new LinkedList<>();q.addLast(root);
        boolean flag=true;
        while(!q.isEmpty()){
            int count=q.size();
            List<Integer> tem=new LinkedList<>();
            LinkedList<TreeNode> level=new LinkedList<>();
            for(;count>0;count--){
                TreeNode cur=q.removeFirst();
                if(flag) {
                    if(cur.left!=null) level.addLast(cur.left);
                    if(cur.right!=null) level.addLast(cur.right);
                }else {
                    if(cur.right!=null) level.addLast(cur.right);
                    if(cur.left!=null) level.addLast(cur.left);
                }
                tem.addLast(cur.val);
            }
            ret.addLast(tem);
            q=level.reversed();
            flag=!flag;
        }
        return ret;
    }
}
