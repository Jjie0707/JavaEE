import java.util.ArrayList;

public class Q2 {
    class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }
    public  ListNode func(ListNode e1, ListNode e2) {
        ListNode head = new ListNode(-1);
        ListNode cur=head;
        while (e1 != null && e2 != null) {
            if (e1.val <= e2.val) {
                head.next = new ListNode(e1.val);
                e1 = e1.next;
            } else {
                head.next = new ListNode(e2.val);
                e2 = e2.next;
            }
            head=head.next;
        }
        while (e1 != null) {
            head.next = new ListNode(e1.val);
            e1 = e1.next;
            head=head.next;
        }
        while (e2 != null) {
            head.next = new ListNode(e2.val);
            e2 = e2.next;
            head=head.next;
        }
        return cur.next;
    }
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        // write code here
        ListNode pre = null;
        int size = lists.size();
        for (int i = 0; i < size; i++) {
            ListNode tem = lists.get(i);
            pre = func(pre, tem);
        }
        return pre;
    }
}
