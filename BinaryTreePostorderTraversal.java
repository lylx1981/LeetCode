/*
题解3 - Iterative 非常巧的方法！！！！！

要想得到『左右根』的后序遍历结果，我们发现只需将『根右左』的结果Reverse即可，而先序遍历通常为『根左右』，故改变『左右』的顺序即可（也就是稍微改变原先的先序遍历算法即可），所以如此一来后序遍历的非递归实现起来就非常简单了。


*/

public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Postorder in ArrayList which contains node values.
     */
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (root == null) return result;

        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            //只需将原来的先序便利的下面两行代码互换即可 --- 先就是先塞左，后塞右，因为是Stack，那么就是右边先访问，左边后访问，就达到了“根右左”的效果
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        Collections.reverse(result); //注意用这个函数可以直接将Result反转！！！！

        return result;
    }
}
