package _1107;

public class T2 {
    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1==null) return list2;
        if(list2==null) return list1;
        ListNode head=func(list1,list2);
        return head;
    }
    public ListNode func(ListNode l1,ListNode l2){
        if(l1==null && l2==null) return null;
        else if(l2==null) return l1;
        else if(l1==null) return l2;
        ListNode next1=l1.next,next2=l2.next;

        ListNode ret;
        if(l1.val<=l2.val){
            ret=l1;
            ret.next=func(next1,l2);
        }else{
            ret=l2;
            ret.next=func(l1,next2);
        }

        return ret;
    }
}
