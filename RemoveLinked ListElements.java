/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    /**
     * 仍然是利用dummy node, 先让head指向dummy,然后一个个向后判断即可，
     * @param head a ListNode
     * @param val an integer
     * @return a ListNode
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        head = dummy;
        
        while (head.next != null) {
            if (head.next.val == val) {
                head.next = head.next.next;
            } else {
                head = head.next;
            }
        }
        
        return dummy.next;
    }
    
    /**
     * 第二种仍然是利用递归的方法，非常简单！！！
     * Recursive version.
     * Base case: if head is null, just return null.
     * Relation: remove elements from current linked list can be divided into two parts:
     * 1. Remove elements from the rest of the list except head
     * 2. Check whether head should be removed
     * The resulted list should be the combination of them.
     */
    public ListNode removeElementsRecursive(ListNode head, int val) {
        if (head == null) return null;
        head.next = removeElementsRecursive(head.next, val);
        return head.val == val ? head.next : head;
    }
    
    
}
