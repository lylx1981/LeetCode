public class LowestCommonAncestorOfBST {

    /**
     * Iterative. 方法一：非递归方法，根据BST性质一直继续在左边找，或者右边找，或者干脆当前root就是解。
     * In BST, the lca's value can only be [p, q].
     * And lca is the first from top to bottom that lies in range.
     * If the value is less than both p and q's values, move to right subtree.
     * If the value is more than both p and q's values, move to left subtree.
     * Otherwise, we found the node.
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else {
                return root; //如果不符合上面两种情况，那么一定是要找的2个点分别在左右两边，则当前Root就是解。
            }
        }
        return null; // Reach null and lca not found.
    }

    /** 方法二：递归
     * Recursion.
     * LCA's value must be between [p, q].
     * Statement:
     * Given root, p, q, find the LCA of p and q.
     * Recurrent relation:
     * If root's value < p's and q's, LCA is in the right subtree.
     * If root's value > p's and q's, LCA is in the left subtree.
     * Else, root's value in between, LCA is root.
     * Base case:
     * If root is null, return null.
     * Complete task:
     * Deal with base case. Search left, search right. Return result.
     */
    public TreeNode lowestCommonAncestorB(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestorB(root.right, p, q);
        } else if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestorB(root.left, p, q);
        }
        return root;
    }

}
