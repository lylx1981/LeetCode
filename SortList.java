 /**
     * 解题报告：就是对一个链表进行归并排序。
    主要考察3个知识点，
    知识点1：归并排序的整体思想
    知识点2：找到一个链表的中间节点的方法
    知识点3：合并两个已排好序的链表为一个新的有序链表
    
    归并排序的基本思想是：找到链表的middle节点，然后递归对前半部分和后半部分分别进行归并排序，最后对两个以排好序的链表进行Merge。
    
    另一个的分析也不错：
    链表的排序操作，对于常用的排序算法，能达到 O(nlogn)的复杂度有快速排序(平均情况)，归并排序，堆排序。快速排序不一定能保证其时间复杂度一定满足要求， 归并排序和堆排序都能满足复杂度的要求。在数组排序中，归并排序通常需要使用 O(n) 的额外空间，也有原地归并的实现，代码写起来略微麻烦一点。但是对于链表这种 非随机访问数据结构，所谓的「排序」不过是指针next值的变化而已，主要通过指针操作，故仅需要常数级别的额外空间，满足题意。堆排序通常需要构建二叉树，在这 道题中不太适合。

        既然确定使用归并排序，我们就来思考归并排序实现的几个要素。
        
        按长度等分链表，归并虽然不严格要求等分，但是等分能保证线性对数的时间复杂度。由于链表不能随机访问，故可以先对链表进行遍历求得其长度。
        合并链表，细节已在 Merge Two Sorted Lists | Data Structure and Algorithm 中详述。
        在按长度等分链表时进行「后序归并」——先求得左半部分链表的表头，再求得右半部分链表的表头，最后进行归并操作。
        
     *
     /** 英文分析，描述的很清晰：
     * Cut into two halves
     * Sort left half first, move to right half's pre head and sort right
     * Merge two halves
     * Insert node in latter part if its smaller than current node
     */


//九章算法给了归并排序，也给了快速排序的代码，感觉快速排序代码相对就比较复杂，可以作为了解
// version 1: Merge Sort
public class Solution {            
    private ListNode findMiddle(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }    

    private ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                tail.next = head1;
                head1 = head1.next;
            } else {
                tail.next = head2;
                head2 = head2.next;
            }
            tail = tail.next;
        }
        if (head1 != null) {
            tail.next = head1;
        } else {
            tail.next = head2;
        }

        return dummy.next;
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode mid = findMiddle(head);

        ListNode right = sortList(mid.next);
        mid.next = null;
        ListNode left = sortList(head);

        return merge(left, right);
    }
}

// version 2: Quick Sort 1
public class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode mid = findMedian(head); // O(n)
        
        ListNode leftDummy = new ListNode(0), leftTail = leftDummy;
        ListNode rightDummy = new ListNode(0), rightTail = rightDummy;
        ListNode middleDummy = new ListNode(0), middleTail = middleDummy;
        while (head != null) {
            if (head.val < mid.val) {
                leftTail.next = head;
                leftTail = head;
            } else if (head.val > mid.val) {
                rightTail.next = head;
                rightTail = head;
            } else {
                middleTail.next = head;
                middleTail = head;
            }
            head = head.next;
        }
        
        leftTail.next = null;
        middleTail.next = null;
        rightTail.next = null;
        
        ListNode left = sortList(leftDummy.next);
        ListNode right = sortList(rightDummy.next);
        
        return concat(left, middleDummy.next, right);
    }
    
    private ListNode findMedian(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    private ListNode concat(ListNode left, ListNode middle, ListNode right) {
        ListNode dummy = new ListNode(0), tail = dummy;
        
        tail.next = left; tail = getTail(tail);
        tail.next = middle; tail = getTail(tail);
        tail.next = right; tail = getTail(tail);
        return dummy.next;
    }
    
    private ListNode getTail(ListNode head) {
        if (head == null) {
           return null;
        } 
       
        while (head.next != null) {
            head = head.next;
        }
        return head;
    }
}

// version 3: Quick Sort 2
/**
 * Definition for ListNode.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int val) {
 *         this.val = val;
 *         this.next = null;
 *     }
 * }
 */ 
class Pair {
    public ListNode first, second; 
    public Pair(ListNode first, ListNode second) {
        this.first = first;
        this.second = second;
    }
}

public class Solution {
    /**
     * @param head: The head of linked list.
     * @return: You should return the head of the sorted linked list,
                    using constant space complexity.
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode mid = findMedian(head); // O(n)
        Pair pair = partition(head, mid.val); // O(n)
        
        ListNode left = sortList(pair.first);
        ListNode right = sortList(pair.second);
        
        getTail(left).next = right; // O(n)
        
        return left;
    }
    
    // 1->2->3 return 2
    // 1->2 return 1
    private ListNode findMedian(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    // < value in the left, > value in the right
    private Pair partition(ListNode head, int value) {
        ListNode leftDummy = new ListNode(0), leftTail = leftDummy;
        ListNode rightDummy = new ListNode(0), rightTail = rightDummy;
        ListNode equalDummy = new ListNode(0), equalTail = equalDummy;
        while (head != null) {
            if (head.val < value) {
                leftTail.next = head;
                leftTail = head;
            } else if (head.val > value) {
                rightTail.next = head;
                rightTail = head;
            } else {
                equalTail.next = head;
                equalTail = head;
            }
            head = head.next;
        }
        
        leftTail.next = null;
        rightTail.next = null;
        equalTail.next = null;
        
        if (leftDummy.next == null && rightDummy.next == null) {
            ListNode mid = findMedian(equalDummy.next);
            leftDummy.next = equalDummy.next;
            rightDummy.next = mid.next;
            mid.next = null;
        } else if (leftDummy.next == null) {
            leftTail.next = equalDummy.next;
        } else {
            rightTail.next = equalDummy.next;
        }
        
        return new Pair(leftDummy.next, rightDummy.next);
    }
    
    private ListNode getTail(ListNode head) {
        if (head == null) {
           return null;
        } 
       
        while (head.next != null) {
            head = head.next;
        }
        return head;
    }
}
