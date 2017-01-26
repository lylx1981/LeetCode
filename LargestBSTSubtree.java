/*这道题主要还是用递归，但是需要同时完成两个不同的功能，也就是不仅判断一个树是不是BST树，同时统计这个数的个数，这两个东西都要在递归里面使用。方法是定义一个类(supernode)来存储信息，每次对当前点，计算当前点对应的Supernode。

要好好体会如果递归里面需要涉及到两个信息的递归使用的话，该如何设计。这个技巧可以应用到其他题。*/
 
public class Solution {
    public class SuperNode {
        int ans;
        int small, large;
        boolean isBST;
        public SuperNode() {
            ans = 0; //存储对于当前点来讲，以其为根的树的对应题目的答案是什么
            isBST = true; //以其为根的树是不是BST
            small = Integer.MAX_VALUE;//以其为根的最小值是什么
            large = -Integer.MAX_VALUE; //以其为根的最大值
        }
    }
    public int largestBSTSubtree(TreeNode root) {
        return dfs(root).ans;
    }
    public SuperNode dfs(TreeNode node) {
        if (node == null) {
            return new SuperNode();
        }
        SuperNode now = new SuperNode();//生成对于当前点对应的一个新的Supernode，然后通过对其左右孩子返回的Supernode来计算当前点对应的Supernode
        SuperNode left = dfs(node.left);
        SuperNode right = dfs(node.right);
        if (left.small < node.val) {
            now.small = left.small;
        } else {
            now.small = node.val;
        }
        now.large = Math.max(right.large,node.val);//上面的If循环可以直接写成Math.min的形式也
	//只有当以下情况发生，说明左右子树都是BST，且包含当前节点后，整合起来的树仍然是BST
        if (left.isBST && right.isBST && left.large <= node.val && right.small >= node.val) {
            now.ans = left.ans + right.ans +1;
            now.isBST = true;
        } else { //说明整个起来的以当前点为根的树不再是BST了，isBST设置为False，同时，该节点对应的Ans的值就是其左右子树对应Supernode里面最大的那个。
            now.ans=Math.max(left.ans,right.ans);
            now.isBST = false;
        }
        return now;
    }
}
