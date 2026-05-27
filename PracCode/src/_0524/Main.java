package _0524;

import javax.swing.*;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
public class Main {
//    [[1,4,5],[1,3,4],[2,6]]
    public static void main(String[] args) {
        ListNode a=new ListNode(5,null);
        ListNode b=new ListNode(4,a);
        ListNode c=new ListNode(1,b);

        ListNode d=new ListNode(4,null);
        ListNode e=new ListNode(3,d);
        ListNode f=new ListNode(1,e);

        ListNode g=new ListNode(6,null);
        ListNode h=new ListNode(2,g);

        ListNode[] lists={c,f,h};
        ListNode head=mergeKLists(lists);
        while(head!=null) {
            System.out.print(head.val+" ");
            head=head.next;
        }
    }
    public static ListNode mergeKLists(ListNode[] lists) {
        if(lists.length==0) return null;
        if(lists.length==1) return lists[0];
        ListNode ret=new ListNode();
        for(ListNode list:lists){
            ListNode cur=ret.next;
            ret.next=mergeLists(cur,list);
        }
        return ret.next;
    }
    public static ListNode mergeLists(ListNode head1,ListNode head2){
        ListNode ret=new ListNode(),cur=ret;
        while(head1!=null && head2!=null){
            ListNode next1=head1.next,
                    next2=head2.next;
            if(head1.val<=head2.val) {
                cur.next = head1;
                head1=next1;
            }
            else {
                cur.next = head2;
                head2=next2;
            }
            cur=cur.next;
        }
        while(head1!=null){
            cur.next=head1;cur=cur.next;
            head1=head1.next;
        }
        while(head2!=null){
            cur.next=head2;cur=cur.next;
            head2=head2.next;
        }
        return ret.next;
    }
}
