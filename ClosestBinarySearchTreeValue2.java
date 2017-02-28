/* 这道题好像自己做了2次，这个是第二次写的笔记，其中思路二的方法已经在第一次做过来，可以看看对比对比。

思路：方法一： 用2个Stack，把树中cloest node to the target的节点的前驱和后继都找出来，然后类似归并排序，逐个挑出K个即可。

这里重要的一点还是应用了树的遍历，尤其是中序遍历，特别是如何从中序遍历找出前驱和后继List，也就是：inorder traversal gives us sorted predecessors, whereas reverse-inorder traversal gives us sorted successors. 

用Stack的意义在于当找出前驱和后继List后，越是后进Stack的，越是里Target近的，所以越先被Pop出来，加入Res里。

另外，这一题要继续注意递归中序遍历的写法，再次体会！


The idea is to compare the predecessors and successors of the closest node to the target, we can use two stacks to track the predecessors and successors, then like what we do in merge sort, we compare and pick the closest one to the target and put it to the result list.

As we know, inorder traversal gives us sorted predecessors, whereas reverse-inorder traversal gives us sorted successors.

We can use iterative inorder traversal rather than recursion, but to keep the code clean, here is the recursion version.

*/

public List<Integer> closestKValues(TreeNode root, double target, int k) {
  List<Integer> res = new ArrayList<>();

  Stack<Integer> s1 = new Stack<>(); // predecessors
  Stack<Integer> s2 = new Stack<>(); // successors

  inorder(root, target, false, s1);
  inorder(root, target, true, s2);
  
  while (k-- > 0) { //只挑K个就行了。
    if (s1.isEmpty())
      res.add(s2.pop());
    else if (s2.isEmpty())
      res.add(s1.pop());
    else if (Math.abs(s1.peek() - target) < Math.abs(s2.peek() - target)) //类似于Merge排序，取离Target最小的。
      res.add(s1.pop());
    else
      res.add(s2.pop());
  }
  
  return res;
}

// inorder traversal
void inorder(TreeNode root, double target, boolean reverse, Stack<Integer> stack) {
  if (root == null) return;

  inorder(reverse ? root.right : root.left, target, reverse, stack); //用Reverse来判断是要中序遍历，还是反中序遍历。
  // early terminate, no need to traverse the whole tree
  //这一句很重要，如果是中序遍历的话，因为是BST，遍历的数的顺序就是从小到大有序的，但是现在要找的是cloest node to the target的节点的前驱，当当前Root已经逐渐增大到大于Target的时候，就不用再继续考察了，要考察的前驱节点到此为止，需要考察的都已经在Stack里了。注意这里会导致其实stack里会放入很多前驱，但是后面从Stack Pop出来的时候，仅会用到有限个而已，而且Pop的时候，也是从离Target最近的元素开始Pop。
  //对Reverse的情况，是一样的意思。Reverse是在找后继，后继的话是递减排列下来的，但当当前Root已经减到比当前Target还小的时候，也就不用继续判断了，要考察的后继节点也已经都保存在Stack里了。
  if ((reverse && root.val <= target) || (!reverse && root.val > target)) return;
  // track the value of current node
  stack.push(root.val); //将当前Root入Stack，说明当前Root还是一个带考察的前驱或者后继序列上的节点
  inorder(reverse ? root.left : root.right, target, reverse, stack); //可以看到中序遍历的特点，在上面一个inorder()之间的部分嵌入对root结点的考察，以体现了中序遍历的思想
}


/* 方法二：这个方法其实更简单，而且更巧，而且更加值得学习！ 仔细学习代码，如何在用递归中序遍历的代码中实现“维持一个K的窗口”这样的需求，非常值得学习。这个解法的基本思想就是中序遍历数组，同时维持一个K元素的窗口，不断更新该窗口，使得窗口中都是K个离Target最近的元素，非常巧！



your solution is O(n). And you have made simple things complex. For a O(n) solution, we don't need two stack. We also dont need two scan : in order and reverse in order. We just do a single in order transverse, and maintain a window size K.

We should find a O(K) solution for this problem.


I think it can be a simpler solution O(N) with early termination. Basic ideas is same as yueyub mentioned, use window size k + in order traversal.*/

public class Solution {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        LinkedList<Integer> res = new LinkedList<>();
        collect(root, target, k, res);
        return res;
    }

    public void collect(TreeNode root, double target, int k, LinkedList<Integer> res) {
        if (root == null) return;
        collect(root.left, target, k, res);
        //只有当当前Res的大小为K的时候，对当前Root的值进行判断，如果比当前Res的第一个元素比较，其与Target更近，则就删除当前Res里面的第一个元素，这其实就是实现了窗口的滑动，非常巧！！
        if (res.size() == k) {
            //if size k, add curent and remove head if it's optimal, otherwise return
            if (Math.abs(target - root.val) < Math.abs(target - res.peekFirst())) 
                res.removeFirst();
            else return;
        }
        res.add(root.val); //每次都将当前Root元素加入Res，但是没关系，在上面的代码里，每次都会对Res进行窗口更新。
        collect(root.right, target, k, res); //再次，可以看到在，两个collect递归函数之间，写上对Root的处理，再次体现了用递归写中序遍历的代码是如何写的。
    }
}
