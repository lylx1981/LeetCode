public class Solution {
    /* two pointer, 快慢指针，
    这道题重点是使用dummy节点，这个技巧要学会在别的地方用
    因为要从链表最后向前数第n个元素，所以使用2个指针，先让头指针向前走n-1步，这时候，尾指针在dummy node上，那么头指针走过n-1步后，两者就相距n步，然后接下来两者一直相当于滑动窗口一样，一起向前挪，直到头指针达到最后。
    
    先用一个快指针移动到第k个元素 然后再用慢指针从头走 那么快指针走到头的时候 慢指针就是倒数第k个
    Dummy节点的用处，比如，1->2->NULL, n =2; 这时，要删除的就是头节点。这种情况下因为有Dummy节点，就方便多了。
    
    */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (n <= 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode preDelete = dummy;
        for (int i = 0; i < n; i++) {
            if (head == null) {
                return null;
            }
            head = head.next;
        }
        while (head != null) {
            head = head.next;
            preDelete = preDelete.next;
        }
        preDelete.next = preDelete.next.next;
        return dummy.next;
    }
}
