public class Solution {
    /*     
        快慢指针，快指针一次跳2步，慢指针一次一步，如果有环，总会有机会快指针会绕一圈再次赶上慢指针的。
        
        相关证明：在有环的情况下，最终快慢指针一定都走在环内，加入第i次遍历时快指针还需要k步才能追上慢指针，由于快指针比慢指针每次多走一步。那么每遍历一次快慢指针间 的间距都会减少1，直至最终相遇。故快慢指针相遇一定能确定该链表有环。    
        
            
    */
    
    public Boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode fast, slow;
        fast = head.next;
        slow = head;
        while (fast != slow) {
            if(fast==null || fast.next==null)
                return false;
            fast = fast.next.next;
            slow = slow.next;
            
        } 
        return true;
    }
   
}
