
/*
    思路
二叉搜索树先序遍历序列的特点是降序的部分一定是向左走的，一旦开始升序说明开始向右走了，则上一个降序的点则限定了后面的数的最小值。如果继续降序，说明又向左走了，这样等到下次向右走得时候也要再次更新最小值。

    10
   /  \
  5    12
 / \
2   6
如这个例子，我们在10的位置是没有最小值限定的，然后降序走到5，依然没有最小值，降序走到2，依然没有，然后开始升序了，遇到6，这时候之后的数字一定大于2，同时也大于5，所以最小值更新为之前遍历过的，且比当前数稍微小一点的那个数。这里我们可以用一个栈来暂存之前的路径，所以升序时就是将栈中元素不断pop出来直到栈顶大于当前数，而最小值就是最后一个pop出来的数，最后再把该数push进去。对于降序的时候，直接向里面push就行了。这样，序列无效的条件就是违反了这个最小值的限定。

同样的Leetcode 描述的思想：Kinda simulate the traversal, keeping a stack of nodes (just their values) of which we're still in the left subtree. If the next number is smaller than the last stack value, then we're still in the left subtree of all stack nodes, so just push the new one onto the stack. But before that, pop all smaller ancestor values, as we must now be in their right subtrees (or even further, in the right subtree of an ancestor). Also, use the popped values as a lower bound, since being in their right subtree means we must never come across a smaller number anymore.
*/
     
   public class Solution {
       //代码无法在Leetcode验证，但是基本和Leetcode参考答案一致
    public boolean verifyPreorder(int[] preorder) {
        Stack<Integer> stk = new Stack<Integer>();
        // 初始化最小值为最小整数
        int min = Integer.MIN_VALUE;
        for(int num : preorder){
            // 违反最小值限定则是无效的
            if(num < min) return false;
            // 将路径中所有小于当前的数pop出来并更新最小值
            while(!stk.isEmpty() && num > stk.peek()){
                min = stk.pop();
            }
            // 将当前值push进去
            stk.push(num);
        }
        return true;
    }
    
      /*  
        Solution 2 ... O(1) extra space
    
        Same as above, but abusing the given array for the stack.
        将数组从头位开始重复利用就可以了。
    */
    public boolean verifyPreorder(int[] preorder) {
        int low = Integer.MIN_VALUE, i = -1;
        for (int p : preorder) {
            if (p < low)
                return false;
            while (i >= 0 && p > preorder[i])
                low = preorder[i--];
            preorder[++i] = p;
        }
        return true;
    }
}
