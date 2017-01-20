
public class Solution {
   
    /**先求数组长度，然后计算出要真正挪几步，因为取决于n的值，需要求余操作，因为长度为5的链表移动5次的话又回到原来的模样了。 * 然后使用dummy node指向链表头，找到具体分割点的节点后（用快慢指针法寻找分割点），进行相关处理即可。
     * /**
     * Two pointers
     * Move fast pointer to the end of the list to get length
     * Move slow pointer to len - n % len to get the break point
     * Connect fast with head, update new head
     * Set slow.next to null to unlink the list
     *
     */
    private int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length ++;
            head = head.next;
        }
        return length;
    }
    
    public ListNode rotateRight(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        
        int length = getLength(head);
        n = n % length; //将n先求余
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        head = dummy;
        
        ListNode tail = dummy;
        for (int i = 0; i < n; i++) {
            head = head.next; //先让head往前挪n步
        }
        
        while (head.next != null) {
            tail = tail.next;//两个指针同时像移动窗口一样移动，head到最后时，tail正好指向分割点，tail将是最终Result链表的最后一个点
            head = head.next;
        }
        //比如1(dummy.next)，2，3(tail)，4，5(head)，（加入是要往右移动2步）可以体会下面的几行代码。 
        head.next = dummy.next;
        dummy.next = tail.next;
        tail.next = null;
        return dummy.next;
    }


}
