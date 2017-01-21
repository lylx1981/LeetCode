
public class Solution {
    /*
    简而言之 如果从起点到环的起始点距离是a 环的长度是L 环的起点到指针相遇点长度是b
    那么相遇时  慢指针走了 a+b距离 快指针走了 a+n*L+b距离 由于快指针速度是慢指针的两倍 所以距离也是两倍 2(a+b) = a+n*L+b 可得到 a =n*L-b 也就是 a = (1+n-1)*L - b 就是
    a = L - b + (n-1)*L 也就是说 如果此时有一个指针位于起始点 另一个指针位于相遇点 然后他们同时每次一步往前走 他们相遇的地方 就是环的起点(因为一个走了a步  另一个走了n-1圈再加上 L-b步) L-b正好就是相遇点到环起点的距离
    也可以看成 一个走了距离a 一个从相遇点走了n圈 回到相遇点 然后往回退了距离b 正好就是环起点
    
    详细的证明解释分析，参考：http://blog.csdn.net/kenden23/article/details/13871699 非常好的解释和证明！

    */
    /**
     * 这题比较简单，其实就是将链表的左右两边合并，只是合并的时候右半部分需要翻转一下。

        主要有三步：
        
        快慢指针找到切分链表
        翻转右半部分
        依次合并
        注意这个算法中的三个函数都可以在别的地方用到，要好好看看这个例子
     * Find mid point, then split list into 2 halves
     * Reverse latter half, then merge two lists
     */
     
    //给定一个头节点，如何把链表反转，重要！！！ 
   private ListNode reverse(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }
        return newHead;
    }
    
    //如何Merge两个链表
    private void merge(ListNode head1, ListNode head2) {
        int index = 0;
        ListNode dummy = new ListNode(0);
        while (head1 != null && head2 != null) {
            if (index % 2 == 0) {
                dummy.next = head1;
                head1 = head1.next;
            } else {
                dummy.next = head2;
                head2 = head2.next;
            }
            dummy = dummy.next;
            index ++;
        }
        //处理不等长的情况
        if (head1 != null) { 
            dummy.next = head1;
        } else {
            dummy.next = head2;
        }
    }
    
    //如何寻找链表中点，快慢指针的再一次体现
    private ListNode findMiddle(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        ListNode mid = findMiddle(head); //第一步找中点
        ListNode tail = reverse(mid.next); //从中点后面那一节点开始反转后半部分
        mid.next = null; //把当前链表从mid断开

        merge(head, tail); //合并前后两半部分
    }
    
}
