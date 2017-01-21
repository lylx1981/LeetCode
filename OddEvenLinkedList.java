
public class Solution {
    
    /**
     * 题意：将单链表结构变为前半部分是奇数，后半部分是偶数，处理后数据的顺序不变

        解题思路：双指针，一个用于奇数，一个用于偶数，最终合并就好了。
     */
  public ListNode oddEvenList(ListNode head) {
    if(head == null || head.next == null){
        return head;
    }
    ListNode odd = head;
    ListNode even = head.next;
    ListNode even_head = head.next;
    while(even != null && even.next != null){
        odd.next = odd.next.next; //把当前odd的next指针直接设置为下一个奇数位置的那个元素
        even.next = even.next.next; //同理对于even
        //将odd even分别挪到他们下一个偶数位置或者奇数位置即可
        odd = odd.next;
        even = even.next;
    }
    odd.next = even_head;
    return head;
    }
}
