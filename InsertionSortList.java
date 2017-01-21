
public class Solution {
    
    /**
     * 这道题跟Sort List类似，要求在链表上实现一种排序算法，这道题是指定实现插入排序。插入排序是一种O(n^2)复杂度的算法，基本想法相信大家都比较了解，就是每次循环找到一个元素在当前排好的结果中相对应的位置，然后插进去，经过n次迭代之后就得到排好序的结果了。了解了思路之后就是链表的基本操作了，搜索并进行相应的插入。时间复杂度是排序算法的O(n^2)，空间复杂度是O(1)。
     * 
     * 思路：
            拿出原list的头节点，用一个指针扫描新list直到找到插入位置，并插入。注意点：
            1. 用dummy head来简化新list的头节点操作。
            2. 由于插入节点需要依赖插入位置的前一节点。所以查找新list节点时，始终用node.next来和要插入的节点比较, 这样node最后一定指向要插入位置的前一节点（因为这个节点以及它的next指针都是要用到的），这个技巧非常重要！！！！。
            3. 注意当节点需要插入新list尾部的情况。
     * 
     */
     
    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(0);
        // 这个dummy的作用是，把head开头的链表一个个的插入到dummy开头的链表里
        // 所以这里不需要dummy.next = head;
        
        //dummy一直存放新链表，head从老链表的头开始，一直从头到尾判断老链表里面的元素

        while (head != null) {
            ListNode node = dummy;
            //注意这里一直是对新的链表也就是以dummy开头的新链表进行遍历来找需要插入的位置
            //而且一直用node.next来判断插入位置。
            while (node.next != null && node.next.val < head.val) {
                node = node.next;
            }
            
            ListNode temp = head.next; //记录一下原链表中的下一个要判断的元素
            head.next = node.next; // 当前要插入元素的next指针指向node.next元素，因为head要直接插在node后面，所以它的next指针要指向原来node的下一个元素
            node.next = head; //同样的，node现在也要指向head,因为head现在插入到node后面了
            head = temp; //最后head向后挪一位，继续判断原链表的下一个元素
        }

        return dummy.next;
    }
}
