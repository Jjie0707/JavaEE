package _0804;

public class Main {
        static class ListNode {
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
        public static void main(String[] args) {
            ListNode e=new ListNode(5,null);
            ListNode d=new ListNode(4,e);
            ListNode c=new ListNode(3,d);
            ListNode b=new ListNode(2,c);
            ListNode a=new ListNode(1,b);
            ListNode cur=a;
            while(cur!=null){
                System.out.print(cur.val);
                cur=cur.next;
            }
            reorderList(a);
            cur=a;
            while(cur!=null){
                System.out.print(cur.val);
                cur=cur.next;
            }
        }
        public static void reorderList(ListNode head) {
            int n=-1;
            ListNode cur=head;
            while(cur!=null){
                cur=cur.next;n++;
            }
            if(n<2) return ;
            else{
                cur=head;
                //找到分界点
                for(int i=(n-(n/2));i>0;i--) cur=cur.next;
                //完全切断连接,让两段链表分离
                ListNode head2=cur.next;cur.next=null;cur=head;
                ListNode cur2=head2;
                //头插法倒序
                if(cur2 != null && cur2.next != null){
                    cur2=cur2.next;head2.next=null;
                    while (cur2 != null) {
                        ListNode next = cur2.next;
                        cur2.next = head2;
                        head2 = cur2;
                        cur2 = next;
                    }
                }
                cur2=head2;
                //cur2指向的就是倒序之后的开始,即Ln
                while(cur!=null && cur2!=null){
                    ListNode next=cur.next,next2=cur2.next;
                    cur.next=cur2;cur2.next=next;
                    cur2=next2;cur=next;
                }
            }
        }
}
