import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode(int cal,TreeNode left,TreeNode right){
        this.val=cal;
        this.left=left;
        this.right=right;
    }
}

public class Q1 {

    public List<Integer> rightSideView(TreeNode root) {
        ArrayList<Integer> ret=new ArrayList<>();
        if(root==null) return ret;

        Deque<TreeNode> queue=new ArrayDeque<>();
        queue.addLast(root);
        int count=1;
        while(!queue.isEmpty()){
            for(int i=0;i<count;i++){
                TreeNode tem=queue.getFirst();
                if(tem.left!=null) queue.addLast(tem.left);
                if(tem.right!=null) queue.addLast(tem.right);
                if(i==count-1) ret.add(tem.val);
            }
            count=queue.size();
        }
        return ret;
    }



    public int kthSmallest(TreeNode root, int k) {
        if(root.left==null && root.right==null) return root.val;
        else{
            ArrayList<Integer> list=new ArrayList<>();
            order(root,list);
            return list.get(k-1);
        }
    }

    public void order(TreeNode root,ArrayList<Integer> list){
        if(root==null) return;
        order(root.left,list);
        list.add(root.val);
        order(root.right,list);
    }


    public static void main(String[] args) {
        Q1 test=new Q1();
//        -2147483648,null,2147483647,-2147483648
        TreeNode a=new TreeNode(-2147483648,null,null);
        TreeNode b=new TreeNode(2147483647,a,null);
        TreeNode c=new TreeNode(-2147483648,null,b);
        System.out.println(test.isValidBST(c));
    }
    public boolean isValidBST(TreeNode root) {

        if(root==null || (root.left==null && root.right==null)) return true;
        TreeNode left=root.left,right=root.right;
        if(left==null && right==null) return true;
        else if(right==null){
            if(left.val>=root.val) return false;
            ArrayList<Integer> list=new ArrayList<>();
            order(root,list);
            return isSameTree(list);
        }else if(left==null){
            if(right.val<=root.val) return false;
            ArrayList<Integer> list=new ArrayList<>();
            order(root,list);
            return isSameTree(list);
        }else{
            if(left.val>=root.val || right.val<=root.val) return false;
            ArrayList<Integer> list=new ArrayList<>();
            order(root,list);
            return isSameTree(list);
        }
    }
    public boolean isSameTree(ArrayList<Integer> list){
        int max=list.get(0);
        int length=list.size();
        for(int i=1;i<length;i++){
            int tem=list.get(i);
            if(tem<=max) return false;
            max=tem;
        }
        return true;
    }

}
