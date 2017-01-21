public class Solution {
    /*
    简而言之 如果从起点到环的起始点距离是a 环的长度是L 环的起点到指针相遇点长度是b
    那么相遇时  慢指针走了 a+b距离 快指针走了 a+n*L+b距离 由于快指针速度是慢指针的两倍 所以距离也是两倍 2(a+b) = a+n*L+b 可得到 a =n*L-b 也就是 a = (1+n-1)*L - b 就是
    a = L - b + (n-1)*L 也就是说 如果此时有一个指针位于起始点 另一个指针位于相遇点 然后他们同时每次一步往前走 他们相遇的地方 就是环的起点(因为一个走了a步  另一个走了n-1圈再加上 L-b步) L-b正好就是相遇点到环起点的距离
    也可以看成 一个走了距离a 一个从相遇点走了n圈 回到相遇点 然后往回退了距离b 正好就是环起点
    
    详细的证明解释分析，参考：http://blog.csdn.net/kenden23/article/details/13871699 非常好的解释和证明！

    */
    /**
     * Reset slow to head after cycle is detected
     * Then move until slow and fast meets
     * Each one step every time
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;
        //快慢指针法先判断是否有环
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                hasCycle = true; 
                break;
            }
        }
        if (!hasCycle) return null; //如果有环，那就继续找环的起点
        slow = head; //让slow从头开始, fast就从相遇的地方开始，当他们碰到的时候，就是环的起点
        while (slow != fast) { // move x steps further
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
    
}
