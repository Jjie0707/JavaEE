package _1011;

import java.security.KeyPair;
import java.util.LinkedList;
import java.util.Map;

public class T3 {
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

//    public static void main(String[] args) {
////        TreeNode t9=new TreeNode(9,null,null);
//        TreeNode t2=new TreeNode(2,null,null);
//        TreeNode t5=new TreeNode(5,null,null);
//        TreeNode t3=new TreeNode(3,t5,null);
////        TreeNode t4=new TreeNode(4,t5,t3);
//        TreeNode t1=new TreeNode(1,t3,t2);
//        System.out.println(widthOfBinaryTree(t1));
//    }

    class Pair{
        TreeNode treeNode;
        Integer val;
        Pair(TreeNode treeNode,Integer val) {
            this.treeNode=treeNode;
            this.val=val;
        }

        public TreeNode getTreeNode() {
            return treeNode;
        }

        public void setTreeNode(TreeNode treeNode) {
            this.treeNode = treeNode;
        }

        public Integer getVal() {
            return val;
        }

        public void setVal(Integer val) {
            this.val = val;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
        int depth=getDepth(root);int ret=1;
        LinkedList<Pair> q=new LinkedList<>();q.addLast(new Pair(root,0));
        if(root==null) return 0;
        for(int i=0;i<depth;i++){
            int count=q.size();
            int s=q.getFirst().val,e=q.getLast().val;
            ret=Math.max(ret,e-s+1);
            for(;count>0;count--){
                Pair p=q.removeFirst();TreeNode cur=p.getTreeNode();
                int pos=p.getVal();
                if(cur.left!=null) q.addLast(new Pair(cur.left,2*pos+1));
                if(cur.right!=null) q.addLast(new Pair(cur.right,2*pos+2));
            }
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
