public class Solution {
    /*     这道题可以仍然使用dummy节点，这个技巧要学会在别的地方用
      head节点一直指向当前已完成处理后的链表的最后一个节点，初始值其指向dummy节点
    
    */
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        head = dummy;
        while (head.next != null && head.next.next != null) {
            ListNode n1 = head.next, n2 = head.next.next;
            //注意这个方法比较好，如果考虑一时想不清楚Swap的代码细节，就直接先把要处理的节点直接定义为两个变量（n1, n2），画个如下的图，然后就显而易见多了。------------------这个技术重要！！！！
            // head->n1->n2->...
            // => head->n2->n1->...
            //以下三行代码体现了如何Swap
            head.next = n2;
            n1.next = n2.next;
            n2.next = n1;
            
            // move to next pair
            head = n1; //因为现在n1是已完成处理后的链表的最后一个节点
        }
        
        return dummy.next;
    }
    
    /* 
       递归方法非常简单，再次学历了递归在链表题中的应用。但是题目要求Consitant Space，而这个递归算法其实是O（n）的复杂度
    */
    public ListNode swapPairs(ListNode head) {
        if ((head == null)||(head.next == null))
            return head;
        ListNode n = head.next;
        head.next = swapPairs(head.next.next);
        n.next = head;
        return n;
    }

}
