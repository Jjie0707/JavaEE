package _1107;
import _1107.T2.*;
public class T4 {
    public ListNode swapPairs(ListNode head) {
        if(head==null ) return head;
        return func(head);
    }
    public ListNode func(ListNode head){
        if(head==null || head.next==null) return head;
        ListNode cur=head,next=head.next,tem=next.next;
        next.next=cur;cur.next=func(tem);
        return next;
    }
}
