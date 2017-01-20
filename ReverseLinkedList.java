/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
   
    public class ReverseLinkedList {

    /**
     * Recursive. 递归的方法相当简单，学习！！！ 
     * Divide the list into 2 parts - head and the rest starts from head.next.
     * Reverse the rest of the linked list.
     * Append head to the tail of reversed linked list, which is head's next.
     * Return newHead of the reversed linked list.
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) { // Empty list or just 1 node.
            return head;
        }
        ListNode newHead = reverseList(head.next); // Similar to post-order. //对当前head.next开始的连接递归调用revese方法
        head.next.next = head; //这是因为head一直被设置过， 所以head.next现在其实就是当前newHead里面的最后一个元素（也就是当初的第二个元素），这个元素要的后面再继续把head元素排在后面，因为head指向的元素才是原来链表的第一个元素，以及最终结果链表的最后一个元素
        head.next = null; //现在head就是neaHeadd的链表里的最后一个元素，其next指针设置为空即可。
        return newHead;
    }

    /**
     * Iterative.
     * Get one node each time and make it the new head of the reversed list.
     * Create a head of the linked list as null.
     * Use the original head as a pointer to iterate the list.
     * While the original head:
     * | First store the next head.
     * | Then set head.next to newHead.
     * | Move newHead to head.
     * | Move head to its stored next.
     */
     public ListNode reverseList2(ListNode head) {
        ListNode newHead = null;
     //下面的思想是，head指向原链表的当前需要判断元素，newHead指向Reverse链表的第一个元素，所以要做的步骤是：
        while (head != null) {
            ListNode next = head.next;
            //1. head节点的next指针应该指向Reserve链表的当前的头，因为head节点将是新的Reverse链表的头
            head.next = newHead; 
            newHead = head;//2. 让newHead指针指向head,也就是指向新的Reverse的链表头
            head = next;//3. 将head指针再往后继续挪一步，继续判断下一个元素。
        }
        return newHead;
    }

    
}
