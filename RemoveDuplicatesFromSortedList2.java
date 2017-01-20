public class Solution {
    /*
    还是用到了dummy node, head一直指向最终结果数组的最后一个元素，然后对head.next, head.next.next就是head对应后面的两个节点判断其是不是dupplicate
    如果是，就一直while循环Skip直到碰到下一个不同值的节点。
    */
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null)
            return head;
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        head = dummy;

        while (head.next != null && head.next.next != null) {
		//只有当head后面直接相连的两个节点值值相等时，才开始去重操做，否则，head就一直向后移就行了，因为没有出现Duplicate节点
            if (head.next.val == head.next.next.val) {
                int val = head.next.val;
		//head.next“整体”作为一个变量，一直存放下一个要判断的点，看是不是需要去重的节点	
                while (head.next != null && head.next.val == val) {
		//注意这一步有点绕，每次这个循环不是改变head,而是改变head.next指针所指的元素
		//最后效果就是head.next最终指向的就是当前while清除完duplicate的下一个元素
                    head.next = head.next.next;
                }            
            } else {
                head = head.next;
            }
        }
        
        return dummy.next;
    }
}
