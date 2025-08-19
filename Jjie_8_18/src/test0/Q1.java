package test0;
class ListNode{
    public ListNode next;
    public int val;

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
public class Q1 {
    public static void main(String[] args) {
        ListNode e = new ListNode(1, null);
        ListNode d = new ListNode(2, e);
        ListNode c = new ListNode(3, d);
        ListNode b = new ListNode(2, c);
        ListNode a = new ListNode(1, b);

        ListNode head = a;
        ListNode cur = head;
//        System.out.println(isPalindrome(head));
//        System.out.println(hasCycle(head));
        
    }
    public static ListNode detectCycle(ListNode head) {
        ListNode fast=head;
        ListNode slow=head;
        while(true){
            if(fast==null || fast.next==null) return null;
            if(slow==null || slow.next==null) return null;
            slow=slow.next;
            fast=fast.next;
            fast=fast.next;
            if(slow==fast) return fast;
        }
    }
    public static boolean hasCycle(ListNode head) {
        ListNode fast=head;
        ListNode slow=head;
        while(true){
            if(fast==null || fast.next==null) return false;
            if(slow==null || slow.next==null) return false;
            slow=slow.next;
            fast=fast.next;
            fast=fast.next;
            if(slow==fast) return true;
        }
    }



    public static boolean isPalindrome(ListNode head) {
        int len=0;
        if(head==null || head.next==null) return true;
        ListNode cur=head;
        while(cur!=null){
            len++;
            cur=cur.next;
        }//走到这里统计了整个链表的长度
        int tem=len/2;
        tem--;
        cur=head;
        while(tem>0){
            cur=cur.next;
            tem--;
        }
        if(len%2!=0) cur.next=cur.next.next;//处理了链表长度为奇数的情况
        ListNode tail=cur.next;
        tail=reverseList(tail);
        cur=head;
        while(cur.next!=tail){
            if(cur.val!=tail.val) return false;
            cur=cur.next;
            tail=tail.next;
        }
        return true;
    }
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode cur = head.next;
        head.next = null;
        while (cur != null) {
            ListNode tem=cur.next;
            cur.next=head;
            head=cur;
            cur=tem;
        }
        return head;
    }
}
