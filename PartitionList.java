/**
 * 思路：两种方法都可以。一种就是遍历原来的链表， 用两个链表分表存储比X小的以及比X大的，最后串起来。
 * 另一种方法就是遍历原来链表，遇见一个比X小的时候，就挪到前面去，注意保持相对顺序即可。
 */

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode partition(ListNode head, int x) {
        if (head == null) {
            return null;
        }
        
        ListNode leftDummy = new ListNode(0);
        ListNode rightDummy = new ListNode(0);
        ListNode left = leftDummy, right = rightDummy;
        
        while (head != null) {
            if (head.val < x) {
                left.next = head;
                left = head;
            } else {
                right.next = head;
                right = head;
            }
            head = head.next;
        }
        
        right.next = null;
        left.next = rightDummy.next;
        return leftDummy.next;
    }
}

 /**
     * Move greater and equal value nodes to tail
     */
    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) return head; // too short
        ListNode dummy = new ListNode(0); // create a dummy node
        dummy.next = head;
        ListNode p = dummy;
        ListNode start = dummy;
        while (p != null && p.next != null){
            if (p.next.val >= x) p = p.next;
            else { // move smaller nodes to start
                if (p == start){  // don't forget the edge cases when p == start
                    start = start.next;
                    p = p.next;
                } else {
                    ListNode tmp = p.next; // move to start
                    p.next = tmp.next;
                    tmp.next = start.next;
                    start.next = tmp;
                    start = tmp; // don't forget to move start.
                }
            }
        }
        return dummy.next;
    }
    
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
