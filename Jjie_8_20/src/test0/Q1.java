package test0;
class ListNode{
    ListNode next;
}
public class Q1 {

//    输入：head = [1,2,3,4]
//    输出：[2,1,4,3]
    public static ListNode swapPairs(ListNode head) {
        if(head==null || head.next==null) return head;
        ListNode ret=head.next;
        ListNode cur=head;
        ListNode pre=null;
        while(cur!=null && cur.next!=null){
            ListNode next=cur.next,tem=next.next;
            cur.next=tem;
            next.next=cur;
            if(pre!=null){
                pre.next=next;
            }
            pre=cur;
            cur=tem;
        }
        return ret;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pre=null,fast=head,slow=head;
        while(n>0){
            fast=fast.next;
            n--;
        }
        while(fast!=null){
            pre=slow;
            fast=fast.next;
            slow=slow.next;
        }
        if(slow==null) return null;
        else {
            if(pre==null) return head.next;
            pre.next = slow.next;
        }
        return head;
    }
}
