package jjie.pracpart.daily;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class _0523 {

    public static void main(String[] args) {
        ListNode a=new ListNode(4,null);
        ListNode b=new ListNode(3,a);
        ListNode c=new ListNode(2,b);
        ListNode d=new ListNode(1,c);
        reorderList(d);
    }
    public static  void reorderList(ListNode head) {
        ListNode cur=head.next;   int len=0;
        ListNode head1=null,head2=null,cur1=null,cur2=null;
        while(cur!=null){
            cur=cur.next;
            len++;
        }//统计完了需要操作的链表长度  (cur==null)
        if(len==0 || len==1) return;
        else len=(len+1)/2;

        cur=head.next;  //(cur!=null)
        head.next=null;  //断开原来链表
        while(cur!=null){
            ListNode next=cur.next;
            if(len>0){
                if(cur1==null){
                    cur1=cur;
                    head1=cur1; //head1,cur1 !=null
                }else{
                    cur1.next=cur;
                    cur1=cur1.next;
                }//  尾插法
                cur1.next=null;
                len--;
            }else{
                if(head2==null){
                    cur2=cur;
                    cur2.next=null;
                    head2=cur2;
                }else{
                    cur2=cur;
                    cur2.next=head2;
                    head2=cur2;
                }
            }
            cur=next;
        }
        cur=head;
        while(head2!=null && head1!=null){
            cur.next=head2;
            head2=head2.next; cur=cur.next;
            cur.next=head1;
            head1=head1.next; cur=cur.next;
        }
        if(head1!=null) cur.next=head1;
    }

    public ListNode swapPairs(ListNode head) {
        return _swapPairs(head);
    }
    public ListNode _swapPairs(ListNode head) {
        if(head==null || head.next==null) return head;
        ListNode cur=head,next=cur.next;
        cur.next=_swapPairs(next.next);
        next.next=cur;
        return next;
    }
    public static  ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode cur1=l1,cur2=l2;
        ListNode pre=new ListNode();
        ListNode tail=null;
        int target=0;
        while(cur1!=null && cur2!=null){
            int num=cur1.val+cur2.val;
            if(target>0) {
                num++;
                target--;
            }
            ListNode tem=new ListNode(num%10);
            if(num>=10) target++;
            if(tail==null){
                tail=tem;
                pre.next=tail;
            }else{
                tail.next=tem;
                tail=tem;
            }
            cur1=cur1.next;cur2=cur2.next;
        }
        if(cur1!=null){
            while(cur1!=null){
                int num = cur1.val;
                if (target >= 0) {
                    num++;
                    target--;
                }
                ListNode tem = new ListNode(num % 10);
                if (num > 10) target++;
                if (tail == null) {
                    tail = tem;
                    pre.next = tail;
                } else {
                    tail.next = tem;
                    tail = tem;
                }
                cur1 = cur1.next;
            }
        }else if(cur2!=null){
            while(cur2!=null){
                int num = cur2.val;
                if (target > 0) {
                    num++;
                    target--;
                }
                ListNode tem = new ListNode(num % 10);
                if (num >= 10) target++;
                if (tail == null) {
                    tail = tem;
                    pre.next = tail;
                } else {
                    tail.next = tem;
                    tail = tem;
                }
                cur2 = cur2.next;
            }
        }
        return pre.next;
    }

}
