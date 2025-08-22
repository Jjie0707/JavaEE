package test0;
class ListNode{
    ListNode next;
    int val;
}
public class Q1 {
    public ListNode reverseKGroup(ListNode head, int k) {
        int gap=1;
        ListNode pre=null,start=head,end=head;
        ListNode ret=null;
        ListNode last=head;
        while(last.next!=null){
            last=last.next;
        }
        while(end.next!=null){
            if(gap==k){

                if(start==head){
                    ret=end;
                }

                ListNode tem=end.next;
                reverse(start,end);
                if(pre!=null) pre.next=end;
                start.next=tem;
                pre=start;
                end=tem;
                start=tem;
                gap=1;
                continue;
            }
            end=end.next;
            gap++;
        }
        if(gap==k){
            if(start==head){
                ret=end;
            }
            ListNode tem=end.next;
            reverse(start,end);
            if(pre!=null) pre.next=end;
            start.next=tem;
        }
        return ret;
    }

    public static void reverse(ListNode start,ListNode end){

        if(start==null || start.next==null) return;

        ListNode pre=start,cur=start.next;
        ListNode tem=cur.next;
        cur.next=pre;
        pre=cur;
        cur=tem;
        while(pre!=end){
            tem=cur.next;
            cur.next=pre;
            pre=cur;
            cur=tem;
        }
    }
}
