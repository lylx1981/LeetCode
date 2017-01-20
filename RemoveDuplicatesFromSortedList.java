public class Solution {
    /*     
        方法一：题目比较简单，只要遇到两个相当元素，就删除后面那个，也就是让第一个直接指向第三个即可，然后继续判断
        
        
        方法二：另外一种方法也可以是两个While循环嵌套，只要遇到重复元素，就一直Skip，直接遇到下一个不一样的元素，然后进行一次性处理
        
        while (cur.next != null && cur.val == cur.next.val) {
                cur.next = cur.next.next; // skip next node
            }
            cur = cur.next; // to next node
            
        
            
    */

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode node = head;
        while (node.next != null) {
            if (node.val == node.next.val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }
        return head;
    }
    
    
    //方法三：仍然是用递归方法，真是太简单了！！！
    
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null)return head;
        head.next = deleteDuplicates(head.next);
        return head.val == head.next.val ? head.next : head; //最后一步只需判断head和head.next的情况即可
    }


}
