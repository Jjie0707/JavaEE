package _1011;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class T1 {
    class Node {
        public int val;
        public List<Node> children;
        public Node() {}
        public Node(int _val) {
            val = _val;
        }
        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> ret=new LinkedList<>();
        Queue<Node> Q=new LinkedList<>();Q.add(root);
        while(!Q.isEmpty()){
            int count=Q.size();
            List<Integer> tem=new ArrayList<>();
            for(;count>0;count--){
                Node cur=Q.poll();
                for(Node node:cur.children){
                    if(node!=null) Q.add(node);
                }
                tem.addLast(cur.val);
            }
            ret.addLast(tem);
        }
        return ret;
    }
}
