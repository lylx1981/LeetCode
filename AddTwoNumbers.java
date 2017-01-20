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
     * //因为数组本身就是倒排的，最低位在前，那么就从两个链表（两个都不为空时）开始一位一位加就可以了（第一个While循环），注意求进位carry保存，和当前位的值用到求余操作。对于当前位的值，用一个新的ListNode表示即可。
    如果其中一个链表已经走到头后，就只需要遍历另一个链表即可，主要是关注carry操作. 如果两个链表最后遍历完了Carry还不为0，说明要还要继续进位，所以再生成一个ListNode保存这个Carry，作为最后结果的最高位。因为每次计算完后，都是在当前生成链表后直接添加新node的，所以这个生成链表最终也是反向的，直接返回即可。
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null) {
            return null;
        }
            
        ListNode head = new ListNode(0);
        ListNode point = head;
        int carry = 0;
        while(l1 != null && l2!=null){
            int sum = carry + l1.val + l2.val;
            point.next = new ListNode(sum % 10);
            carry = sum / 10;
            l1 = l1.next;
            l2 = l2.next;
            point = point.next;
        }
        
        while(l1 != null) {
            int sum =  carry + l1.val;
            point.next = new ListNode(sum % 10);
            carry = sum /10;
            l1 = l1.next;
            point = point.next;
        }
        
        while(l2 != null) {
            int sum =  carry + l2.val;
            point.next = new ListNode(sum % 10);
            carry = sum /10;
            l2 = l2.next;
            point = point.next;
        }
        
        if (carry != 0) {
            point.next = new ListNode(carry);
        }
        return head.next;
    }
    
    
    //下面的代码和上面思想是一样的，但是写的更简洁，直接写成一个While就行了，完全覆盖了上面三个While的情况。推荐使用。
 /**
     * Math.
     * Check the two input list first. If one is null, return the other.
     * Now the two heads are not null.
     * Create a dummy node and a current pointer start from dummy.
     * Create an integer to store the carry.
     * While l1 is not null or l2 is not null or carry is not zero:
     * | If l1 is not null:
     * |   Add its value to carry. Move l1 to next.
     * | If l2 is not null:
     * |   Add its value to carry. Move l2 to next.
     * | Create a new node with value carry % 10.
     * | Append new node after current. Move current to next.
     * | Update carry.
     * Return dummy.next.
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            if (l1 != null) {
                carry += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                carry += l2.val;
                l2 = l2.next;
            }
            ListNode node = new ListNode(carry % 10);
            cur.next = node;
            cur = cur.next;
            carry /=10;
        }
        return dummy.next;
    }


}
