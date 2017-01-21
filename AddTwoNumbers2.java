
    /**  
     * 思路：
     * 方法一：最简单的解决办法，就是将 add two numbers ii 问题，转化为 add two numbers 问题：先分别对每个number链表进行反转，然后进行相加，最后将所得链表再进行反转。

        但是这个方法会modify input. 
        
        方法二：用２个Stack, 将两个链表的值都先压进各自Ｓｔａｃｋ后，再从低位开始进行相加运算
     */
     
public class Solution {
    
    //方法一大程序，九章代码
    public ListNode addLists2(ListNode l1, ListNode l2) {
        l1 = reverse(l1);
        l2 = reverse(l2);
        
        return reverse(addList1(l1, l2));
    } 
    
    //方法二，Leetcode推荐程序，用2个Stack
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> s1 = new Stack<Integer>();
        Stack<Integer> s2 = new Stack<Integer>();
        
        while(l1 != null) {
            s1.push(l1.val);
            l1 = l1.next;
        };
        while(l2 != null) {
            s2.push(l2.val);
            l2 = l2.next;
        }
        
        int sum = 0;
        ListNode list = new ListNode(0);
        while (!s1.empty() || !s2.empty()) {
            if (!s1.empty()) sum += s1.pop();
            if (!s2.empty()) sum += s2.pop();
            list.val = sum % 10;
            ListNode head = new ListNode(sum / 10);
            head.next = list;
            list = head;
            sum /= 10;
        }
        
        return list.val == 0 ? list.next : list;
    }
}
  
