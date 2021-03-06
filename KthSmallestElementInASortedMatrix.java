/*
思路：就是用Heap，把第一行的所有元素先进最小堆，然后开始往外Pop出K个即可，每Pop出一个，如果该元素的同列下一行元素还存在的话，就把那个元素继续加入最小堆即可。如果这样做的话感觉没有同时使用每行每列都是有序的这个性质。

Solution 1 : Heap
Here is the step of my solution:

Build a minHeap of elements from the first row.
Do the following operations k-1 times :
Every time when you poll out the root(Top Element in Heap), you need to know the row number and column number of that element(so we can create a tuple class here), replace that root with the next element from the same column.
After you finish this problem, thinks more :

For this question, you can also build a min Heap from the first column, and do the similar operations as above.(Replace the root with the next element from the same row)
What is more, this problem is exact the same with Leetcode373 Find K Pairs with Smallest Sums, I use the same code which beats 96.42%, after you solve this problem, you can check with this link:
https://discuss.leetcode.com/topic/52953/share-my-solution-which-beat-96-42*/


public class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>();
        for(int j = 0; j <= n-1; j++) pq.offer(new Tuple(0, j, matrix[0][j]));
        for(int i = 0; i < k-1; i++) {
            Tuple t = pq.poll();
            if(t.x == n-1) continue;
            pq.offer(new Tuple(t.x+1, t.y, matrix[t.x+1][t.y]));
        }
        return pq.poll().val;
    }
}

class Tuple implements Comparable<Tuple> {
    int x, y, val;
    public Tuple (int x, int y, int val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }
    
    @Override
    public int compareTo (Tuple that) {
        return this.val - that.val;
    }
}
