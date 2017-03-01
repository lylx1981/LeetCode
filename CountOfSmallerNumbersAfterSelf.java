/*
思路一: 从右往左遍历数组，并且将搜索过的数有序的放到一个数组中，然后每次从这个维护的数组中找到第一个大于当前元素的位置即可直到右边有多少个数比他小了．这种方式的时间复杂度是O(n^2)，因为数组插入元素的时间复杂度是O(n)（因为需要移位）．简单粗暴．这种方法比朴素的O(n^2)有所优化（也就是遍历每个元素，然后对元素元素再遍历它后面所有元素）。有人评论说这个解法和前面说的Brute Force本质上还是一样的, 不认为这个方法可以满足要求.

Traverse from the back to the beginning of the array, maintain an sorted array of numbers have been visited. Use findIndex() to find the first element in the sorted array which is larger or equal to target number. For example, [5,2,3,6,1], when we reach 2, we have a sorted array[1,3,6], findIndex() returns 1（因为现在3是第一个比2大的数字，3的数组下标是1，所以返回1）, which is the index where 2 should be inserted and is also the number smaller than 2. Then we insert 2 into the sorted array to form [1,2,3,6].

here the insertion operation of ArrayList is O(n), thus the worse case here would be O(n^2)

Due to the O(n) complexity of ArrayList insertion, the total runtime complexity is not very fast, but anyway it got AC for around 53ms.*/


public List<Integer> countSmaller(int[] nums) {
    Integer[] ans = new Integer[nums.length];
    List<Integer> sorted = new ArrayList<Integer>();
    for (int i = nums.length - 1; i >= 0; i--) {
        int index = findIndex(sorted, nums[i]);
        ans[i] = index;
        sorted.add(index, nums[i]);
    }
    return Arrays.asList(ans);
}
private int findIndex(List<Integer> sorted, int target) { //二分法在排序好的数组里找第一个比当前Target大的元素，找到的那个索引其实就是在Target右侧比起小的元素个数
    if (sorted.size() == 0) return 0;
    int start = 0;
    int end = sorted.size() - 1;
    if (sorted.get(end) < target) return end + 1;
    if (sorted.get(start) >= target) return 0;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (sorted.get(mid) < target) {
            start = mid + 1;
        } else {
            end = mid;
        }
    }
    if (sorted.get(start) >= target) return start;
    return end;
}


/*
思路二： 从后往前遍历数组，同时对数组Build一个BST，对每个树节点保留相应的信息，属于一种扩展的Build BST的过程。这个方法是Leetcode上评论最多的，而且达到了最优。但是这个方法很难自己想到。另外有人推荐使用BIT来做。

Every node will maintain a val sum recording the total of number on it's left bottom side, dup counts the duplication （这一句最重要，说明了树节点的数据结构，也就是每个节点分别存储两个信息，sum存储在其左侧的所有节点个数，Dup存储当前节点出现了多少次Duplicate）. For example, [3, 2, 2, 6, 1], from back to beginning,we would have:

                1(0, 1)
                     \
                     6(3, 1)
                     /
                   2(0, 2)
                       \
                        3(0, 1)
When we try to insert a number, the total number of smaller number would be adding dup and sum of the nodes where we turn right （这一句最重要，只加那些在那里往右拐的节点的相应值）.
for example, if we insert 5, it should be inserted on the way down to the right of 3, the nodes where we turn right is 1(0,1), 2,(0,2), 3(0,1), so the answer should be (0 + 1)+(0 + 2)+ (0 + 1) = 4

if we insert 7, the right-turning nodes are 1(0,1), 6(3,1), so answer should be (0 + 1) + (3 + 1) = 5*/

//以下是递归版本
public class Solution {
    class Node {
        Node left, right;
        int val, sum, dup = 1;
        public Node(int v, int s) {
            val = v;
            sum = s;
        }
    }
    public List<Integer> countSmaller(int[] nums) {
        Integer[] ans = new Integer[nums.length];
        Node root = null;
        for (int i = nums.length - 1; i >= 0; i--) {
            root = insert(nums[i], root, ans, i, 0);
        }
        return Arrays.asList(ans);
    }
    private Node insert(int num, Node node, Integer[] ans, int i, int preSum) {
        if (node == null) {
            node = new Node(num, 0); //空的话，直接建立一个新的节点
            ans[i] = preSum;
        } else if (node.val == num) {
            node.dup++; //如果和当前节点一样，则更新其dup字段
            ans[i] = preSum + node.sum; //同时ans[i]就是当前节点的sum 加上上一层递归送过来的迄今为止的和PreSum
        } else if (node.val > num) {
            node.sum++; //如果当前节点比num大，则说明需要往左走，那就简单的对node.sum进行更新即可。
            node.left = insert(num, node.left, ans, i, preSum);
        } else {
		// 这里说明要往右走，则需要更新Presum，进入下一层循环，PreSum变为preSum + node.dup + node.sum
            node.right = insert(num, node.right, ans, i, preSum + node.dup + node.sum);
        }
        return node;
    }
}
