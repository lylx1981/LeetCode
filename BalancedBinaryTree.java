    /** 递归并改写原来的求maxDepth的方法(用Depth为-1来表示树不是平衡的).
     * 如果对于一个节点，如果 1）任何一个左右孩子不是平衡的，则这个节点对应的树肯定也不是平衡的。
     * 2）但如果两个左右孩子都是平衡的了，这时候要继续判断两个子树的height是不是差最多1，如果不是的话，那么这个节点对应的树也不是平衡的。
     * 
     * 特别要注意这道题目给出的平衡二叉树的定义：重要！ a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1。 注意是对“every node”都满足这个条件。

    //注意这里因为是用递归操作，所以会判断每个节点的，所以自动保证了“the depth of the two subtrees of every node never differ by more than 1”里面“every node”这个条件
     * 
     */
     
class BalancedBinaryTree {
    
    public boolean isBalanced(TreeNode root) {
        return maxDepth(root) != -1; //判断最大maxDepth是不是-1即可。
    }
    
    /**
     * Modification of max depth
     * If current node is null, return 0
     * Compare left depth with right depth
     * If the difference is bigger than 1, set isBalance false
     * Otherwise go on to the rest of the nodes
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) return -1; 
        return Math.max(left, right) + 1; //对应的节点其maxDepth就是就是左右子树立具有最大depth的那个depth+1
    }

   
}
