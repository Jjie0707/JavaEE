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

//    public static void main(String[] args) {
//        ListNode e=new ListNode(5,null);
//        ListNode d=new ListNode(4,e);
//        ListNode c=new ListNode(3,d);
//        ListNode b=new ListNode(2,c);
//        ListNode a=new ListNode(1,b);
//        ListNode head = a;
//        ListNode cur=head;
////        while(cur!=null){
////            System.out.print(cur.val+" ");
////            cur=cur.next;
////        }
//        head = reverseList(head);
//        cur = head;
//        while (cur != null) {
//            System.out.print(cur.val + " ");
//            cur = cur.next;
//        }
//    }

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

    public static ListNode reverseList_(ListNode head) {
        ListNode cur=head;
        while(cur.next!=null) cur=cur.next;
        reverse(head,null);
        return cur;
    }


    public static void reverse(ListNode head,ListNode pre){
        if(head.next==null) head.next=pre;
        else {
            reverse(head.next,head);
            head.next=pre;
        }
    }



    public static void main(String[] args) {
        ListNode e = new ListNode(5, null);
        ListNode d = new ListNode(4, e);
        ListNode c = new ListNode(3, d);
        ListNode b = new ListNode(2, c);
        ListNode a = new ListNode(1, b);
        ListNode head = a;
        ListNode cur = head;

    }

    public static boolean isPalindrome(ListNode head) {
        if(head==null || head.next.next==null) return true;
        int len=0;ListNode cur=head;
        while(cur!=null){
            cur=cur.next;
            len++;
        }//走完统计了整个链表的长度

    }
}
