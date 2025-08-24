package test0;

import java.util.ArrayList;
import java.util.HashMap;

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
public class Q1 {

    public Node copyRandomList(Node head) {
        if(head==null) return head;
        Node cur=head;
        while(cur!=null){
            Node tem=cur.next;
            cur.next=new Node(cur.val);
            cur.next.next=tem;
            cur=tem;
        }//走到这里完成了间隔复制
        cur=head;
        Node ret=head.next;
        while(cur!=null){
            Node random=cur.random;
            if(random!=null) cur.next.random=new Node(random.val);
            else cur.next.random=null;
            cur=cur.next.next;
        }
        cur=head;
        while(cur!=null){
            Node copy=cur.next;
            Node next=copy.next;
            if(next!=null){
                Node next1=next;
                Node next2=next1.next;
                cur=next1;
            }else{
                cur.next=null;
                break;
            }

        }
        return ret;
    }

}
