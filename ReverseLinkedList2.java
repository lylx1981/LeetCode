/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */


下面的Letcode方法最清晰：推荐！！！！

public ListNode reverseBetween(ListNode head, int m, int n) {
    if(head == null) return null;
    ListNode dummy = new ListNode(0); // create a dummy node to mark the head of this list
    dummy.next = head;
    ListNode pre = dummy; // make a pointer pre as a marker for the node before reversing
    for(int i = 0; i<m-1; i++) pre = pre.next;
    
    ListNode start = pre.next; // a pointer to the beginning of a sub-list that will be reversed
    ListNode then = start.next; // a pointer to a node that will be reversed
    
    // 1 - 2 -3 - 4 - 5 ; m=2; n =4 ---> pre = 1, start = 2, then = 3
    // dummy-> 1 -> 2 -> 3 -> 4 -> 5
    
    for(int i=0; i<n-m; i++)
    {
        start.next = then.next;
        then.next = pre.next;
        pre.next = then;
        then = start.next;
    }
    
    // first reversing : dummy->1 - 3 - 2 - 4 - 5; pre = 1, start = 2, then = 4
    // second reversing: dummy->1 - 4 - 3 - 2 - 5; pre = 1, start = 2, then = 5 (finish)
    
    return dummy.next;
    
}


public class Solution {
    /*
            反转整个链表的变种，指定了起点和终点。由于m=1时会变动头节点，所以加入一个dummy头节点
        
        1. 找到原链表中第m-1个节点start
        
        
        2. 从m位置开始，将长度为L = n-m+1的部分链表反转。
                    
         
        3. 最后两头接回
        
    用到了三个指针      
            ListNode startpoint = newhead;//startpoint指向需要开始reverse的前一个
            初始node1, node2两个指针设置为startpoint后续两个元素
            node1 = startpoint.next; 
            node2 = node1.next;
        
        //注意node1其实在整个置换过程中一直没变，一直指向置换后的链表的最后一个元素。而每次只是node2变了，老的node2挪到最前面后（也就是让startpoint直接指向的元素），新的node2就往后检查当前未置换链表的下一个元素。

    */
   public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode newhead = new ListNode(-1);//其实这就是一个dummy
        newhead.next = head;
        
        if(head==null||head.next==null)
            return newhead.next;
            
        ListNode startpoint = newhead;//startpoint指向需要开始reverse的前一个
        ListNode node1 = null;
        ListNode node2 = null;
        
        for (int i = 0; i < n; i++) {
            if (i < m-1){
                startpoint = startpoint.next;//找真正的startpoint，并记下startpoint
            } else if (i == m-1) {//开始第一轮置换
                node1 = startpoint.next; 
                node2 = node1.next;//
            }else {
                node1.next = node2.next;//node1交换到node2的后面
                node2.next = startpoint.next;//node2交换到最开始（因为现在node2是第一个，所以它的next应该是当前startpoint指向的next节点）
                startpoint.next = node2;//node2作为新的点，让startpoint现在指向node2
                node2 = node1.next;//node2回归到node1的下一个，继续遍历
		//注意node1其实在整个置换过程中一直没变，一直是置换后的链表的最后一个元素。而每次只是node2变了，老的node2挪到最前面后，新的node2就往后检查当前未置换链表的下一个元素。由于第一句的设置，所以新的node2的所指节点还是和node1相连的，就是紧挨着node1的下一个元素。
            }
        }
        return newhead.next;
    }
}
