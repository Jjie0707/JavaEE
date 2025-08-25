package test0;

import org.w3c.dom.Node;

import java.util.*;

class LRUCache{
    private Deque<Integer> Queue;
    private HashMap<Integer, Node> m;
    private int size=0;
    private int capacity=0;

    public LRUCache(int capacity) {
        this.capacity=capacity;
        this.Queue =new LinkedList<>();
    }

    public int get(int key) {
        if(!m.containsKey(key)) return -1;

    }

    public void put(int key, int value) {

    }
}

public class Q1 {

    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length==0) return null;
        ListNode list1=lists[0],list2=null;
        for(int i=1;i<lists.length;i++){
            list2=lists[i];
            list1=mergeTwoLists(list1,list2);
        }

        return list1;
    }
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
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


    public ListNode sortList(ListNode head) {
        ListNode cur=head;
        List<Integer> list=new ArrayList<>();
        while(cur!=null){
            list.add(cur.val);
            cur=cur.next;
        }
        cur=head;
        list.sort((e1,e2)->e1-e2);
        int i=0;
        while(cur!=null){
            cur.val=list.get(i++);
            cur=cur.next;
        }
        return head;
    }
}
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}