
    /**  
     * 思路：
     * 有好多种方法解这个问题，需要好好看看每种方法，是一个很好学习的过程。了解各自方法的复杂度，基本思想，已经各种数据结构的应用
     * 
     */
     
// 方法一：Divide & Conquer 把所有list分成2组，分别排序后，再合并，调用了递归
// version 1: Divide & Conquer
/**
 /**底下是另外一个程序的英文描述，大体思想是一样的，可以看看英文是怎么描述的
     * Divide and conquer. O(nlogn) Time.
     * Merge k sorted lists can be divided, suppose we have k lists,
     * 1) Merge the first k / 2 lists
     * 2) Merge k / 2 + 1 to k lists
     * Then just implement merge two lists.
     * Base cases:
     * 1) If start > end, return null;
     * 2) If start == end, there is only 1 list, return the head of that list;
     * 3) If start == end - 1, there are 2 lists, return the merged list.
     
 
 */ 
public class Solution {
    /**
     * @param lists: a list of ListNode
     * @return: The head of one sorted list.
     */
    public ListNode mergeKLists(List<ListNode> lists) {
        if (lists.size() == 0) {
            return null;
        }
        return mergeHelper(lists, 0, lists.size() - 1);
    }
    
    private ListNode mergeHelper(List<ListNode> lists, int start, int end) {
        if (start == end) {
            return lists.get(start); //Start==end的时候，说明只有一个链表要合并，直接返回start即可
        }
        
        int mid = start + (end - start) / 2; //将List分为2组
        ListNode left = mergeHelper(lists, start, mid); //每组单独合并，调用了递归
        ListNode right = mergeHelper(lists, mid + 1, end);
        return mergeTwoLists(left, right); //最后将其2者再合并即可
    }
    
    //这个函数其实是之前一道题的题解
    private ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                tail.next = list1;
                tail = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                tail = list2;
                list2 = list2.next;
            }
        }
        if (list1 != null) {
            tail.next = list1;
        } else {
            tail.next = list2;
        }
        
        return dummy.next;
    }
}

// 方法二：用 Heap
//注意学习和复习heap的概念，思想是把原来List中所有节点都放到一个最小heap,然后每次从最小堆的top的元素就是下一个要插入最终排序list的元素
// 重点要看这个方法，如何用最小堆，如果定义comparator,以及JAVA里面的heap其实就是优先级队列
//好好看看这个方法，复习heap

/**  底下是另外一个程序的英文描述，大体思想是一样的，可以看看英文是怎么描述的。
     * Heap. O(k) + O(n * log(k)) Time, O(k) Space.
     * Keep track of all heads in a min heap, so that we know the next value to be inserted in O(log(k)) time.
     * Create a min heap of ListNode.
     * Add all non-null heads to the heap.
     * Create a dummy head and a current pointer c from dummy.
     * While heap is not empty:
     * | Set c.next to the node get from heap top.
     * | Move c to c.next.
     * | Now c.next is the new head of that list.
     * | If c.next is not null, add it to heap.
     * Return dummy.next, which is the merged head.
     */
     
     

public class Solution {
    //定义ListNodeComparator
    private Comparator<ListNode> ListNodeComparator = new Comparator<ListNode>() {
        public int compare(ListNode left, ListNode right) {
            if (left == null) {
                return 1;
            } else if (right == null) {
                return -1;
            }
            return left.val - right.val;
        }
    };
    
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        if (lists == null || lists.size() == 0) {
            return null;
        }
        
        //PriorityQueue就是一个heap
        Queue<ListNode> heap = new PriorityQueue<ListNode>(lists.size(), ListNodeComparator);
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i) != null) {
                heap.add(lists.get(i));
            }
        }
        
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (!heap.isEmpty()) {
            ListNode head = heap.poll(); //每次这个操作，就是从heap的top取一个元素
            tail.next = head;
            tail = head;
            if (head.next != null) {
                heap.add(head.next);
            }
        }
        return dummy.next;
    }
}

// 方法三是以此每两个list合并，然后把每次的结果加入到一个new_lists里,然后下一轮再继续对new_lists中的链表再继续进行每两个合并的操作，直到最终那一轮得到的new_lists只有一个元素，那就是最终结果。

// Version 3: merge two by two
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
public class Solution {
    /**
     * @param lists: a list of ListNode
     * @return: The head of one sorted list.
     */
    public ListNode mergeKLists(List<ListNode> lists) {  
        if (lists == null || lists.size() == 0) {
            return null;
        }
        
        while (lists.size() > 1) {
            List<ListNode> new_lists = new ArrayList<ListNode>();
            for (int i = 0; i + 1 < lists.size(); i += 2) {
                ListNode merged_list = merge(lists.get(i), lists.get(i+1));
                new_lists.add(merged_list);
            }
            if (lists.size() % 2 == 1) {
                new_lists.add(lists.get(lists.size() - 1));
            }
            lists = new_lists;
        }
        
        return lists.get(0);
    }
    
    private ListNode merge(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (a != null && b != null) {
            if (a.val < b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        
        if (a != null) {
            tail.next = a;
        } else {
            tail.next = b;
        }
        
        return dummy.next;
    }
}
