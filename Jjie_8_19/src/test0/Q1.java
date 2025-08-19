package test0;

import java.util.List;

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
//        ListNode e = new ListNode(1, null);
        ListNode d = new ListNode(-4, null);
        ListNode c = new ListNode(3, d);
        ListNode b = new ListNode(2, c);
        ListNode a = new ListNode(1, b);



        ListNode head = a;

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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1==null) return l2;
        if(l2==null) return l1;
        ListNode ret=new ListNode((l1.val+l2.val)%10,null);
        ListNode cur=ret;
        int tem= l1.val+l2.val>=10 ? 1 : 0 ;
        l1=l1.next;
        l2=l2.next;
        while(l1!=null && l2!=null){
            int num1=l1.val,num2=l2.val;
            ListNode n=new ListNode((num1+num2+tem)%10,null);
            tem= num1+num2+tem>=10 ? 1 :0;
            cur.next=n;
            cur=cur.next;
            l1=l1.next;
            l2=l2.next;
        }

        while(l1!=null){
            ListNode n=new ListNode(l1.val+tem,null);
            tem=0;
            cur.next=n;
            cur=cur.next;
            l1=l1.next;
        }
        while(l2!=null){
            ListNode n=new ListNode(l2.val+tem,null);
            tem=0;
            cur.next=n;
            cur=cur.next;
            l2=l2.next;
        }
        return ret;
    }


    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1==null) return list2;
        if(list2==null) return list1;

        ListNode pre=new ListNode(-1,null);
        ListNode head=pre;
        while(list1!=null &&list2!=null){
            int l1=list1.val;
            int l2=list2.val;
            if(l1<=l2){
                head.next=list1;
                list1=list1.next;
            }else{
                head.next=list2;
                list2=list2.next;
            }
            head=head.next;
        }
        while(list1!=null){
            head.next=list1;
            list1=list1.next;
            head=head.next;
        }
        while(list2!=null){
            head.next=list2;
            list2=list2.next;
            head=head.next;
        }
        return pre.next;
    }


    public static ListNode detectCycle(ListNode head) {
        if(head==null || head.next==null) return null;
        ListNode fast=head,slow=head;
        while(true){
            if(fast==null || fast.next==null) return null;
            if(slow==null || slow.next==null) return null;
            slow=slow.next;
            fast=fast.next.next;
            if(slow==fast) break;
        }
        slow=head;
        while(slow!=fast){
            slow=slow.next;
            fast=fast.next;
        }
        return slow;
    }
}
