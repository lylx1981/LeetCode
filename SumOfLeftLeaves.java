public class SumOfLeftLeaves {

    /** 方法一：递归 非常简单的题，只是对于左孩子要判断的多一点，左孩子分为是一个叶子或非叶子节点的情况，右孩子只考虑是非叶子节点的情况就可以了。
     * DFS. Recursive.
     * Just change the visit of DFS.
     * Recurrence relation:
     * Current left leaf's value + sum of left subtree's leaves + sum to right subtree's leaves.
     * When visit, if current node's left child is not null, and it is a leaf.
     * Add its value to result.
     * Then call sum recursively on right subtree.
     * Base case:
     * If root is null, return 0.
     */
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int sum = 0;
        if (root.left != null && root.left.left == null && root.left.right == null) { // root.left is a leave.
            sum += root.left.val; // Add directly to result.
        } else { // root.left is a subtree, recurse.
            sum += sumOfLeftLeaves(root.left);
        }
        sum += sumOfLeftLeaves(root.right);
        return sum;
    }

    /**
     * DFS. Recursive.
     * Pass another boolean to indication whether the call is from left.
     */
    public int sumOfLeftLeavesB(TreeNode root) {
        return dfs(root, false);
    }

    /**
     * DFS.
     * Two base cases:
     * 1) root is null, return 0.
     * 2) root not null, root is leaf. If from left, return value. Otherwise return 0.
     * The result is the left leaves sum of left subtree and right subtree.
     */
    private int dfs(TreeNode root, boolean isLeft) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return isLeft ? root.val : 0;
        }
        return dfs(root.left, true) + dfs(root.right, false);
    }

}
Contact GitHub API Training Shop Blog About
© 2017 GitHub, Inc. Terms Privacy 
