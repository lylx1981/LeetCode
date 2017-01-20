public class Solution {
    /* 
    注意这道题有两种方法，一种还是用Dummy node,然后一个个比较当前每个链表的第一个元素，逐个合并就行了。
    另外还有一种递归的方法，更简单。递归的方法用来合并链表，还是第一次见到。要注意这个方法。
    。
    
    */
    //用非递归的方法，lastNode一直指向合并后链表的最后一个node
    
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode lastNode = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                lastNode.next = l1;
                l1 = l1.next;
            } else {
                lastNode.next = l2;
                l2 = l2.next;
            }
            lastNode = lastNode.next;
        }
        //While循环退出，说明至少有一个链表已经没有了。
        if (l1 != null) {
            lastNode.next = l1;
        } else {
            lastNode.next = l2;
        }
        
        return dummy.next;
    }
    
    /** 递归的方法，也需要掌握
     * Recursive.
     * Pick node with smaller value as the current head.
     * Concat head with the merged result of the rest of the two lists.
     * Base case:
     * If one of the node is null, return the other node.
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        // The node with smaller value is picked as current head.
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2); // Merge the rest.
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }
    
    
}
