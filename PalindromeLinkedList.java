/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
   
    /**先用快慢指针找到链表中点，然后对于后一半链表调用之前的Reverse链表方法把其反转后，再与前一半链表比较即可。
     * Two Pointers.
     * Find the middle node, reverse the right half list, then check each node.
     * Use two pointers, one slow pointer s, one fast pointer f.
     * Move s to the head of the right half list.
     * If there are odd number of nodes, move s one step further.
     * Reverse the right half of the list starting from s.
     * Then compare each node's value while s is not null.
     * If diff, return false. Else continue.
     * After all nodes checked, return true.
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
       // 注意初始设置,fast slow都是从同一位置出发的。
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) { //注意这里是如何找链表中点的方法，要记住，快指针每次都两步
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast != null) { // Make sure slow is always the head of right half.
        //可以纸上面画画就知道了，如果是偶数的情况，最后停止的时候，fast是个空指针。
        /*而如果链表个数是奇数的时候，循环停止的时候，fast是指向链表最后一个元素的，所以fast自己本身不是一个null.所以在这种情况下slow指针正指向链表最中间的那个元素，所以还要再把slow元素再往后挪一步。*/

            slow = slow.next;
        }
        slow = reverseList(slow); // Reverse the right half of the list.
        ListNode cur = head;
        while (slow != null) {
            if (cur.val != slow.val) {
                return false;
            }
            cur = cur.next;
            slow = slow.next;
        }
        return true;
    }

    /**
     * Iterative.
     * Create a new head as null, which will be the tail of the reversed list.
     * While head is not null:
     * | Store the next node.
     * | Reverse head.
     * | Update new head.
     * | Move to next.
     * Return new head.
     */
    private ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
        return newHead;
    }

    @Before
    public void setUp() {
        p = new PalindromeLinkedList();
    }

    @Test
    public void testExamples() {
        ListNode h = Utils.buildLinkedList(new int[]{1, 2});
        Assert.assertFalse(p.isPalindrome(h));
    }

    @After
    public void tearDown() {
        p = null;
    }

    
}
