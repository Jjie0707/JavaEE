package _1107;
import _1107.T2.*;
public class T3 {
    public ListNode reverseList(ListNode head) {
        if(head==null) return null;ListNode cur=head;
        ListNode tem=head;
        while(tem.next!=null) tem=tem.next;
        func(head);
        cur.next=null;
        return tem;
    }
    public ListNode func(ListNode head){
        if(head.next==null) return head;
        ListNode ret=func(head.next);ret.next=head;
        ret=ret.next;
        return ret;
    }
}
