public class InorderSuccessorInBST {

    /** 方法一：递归。根据BST的性质来找，如果P小于Root的值，则P的后继一定在Root的左子树里，或者要不就是Root本身。如果P大于等于Root的值，P的后继一定在Root的右子树里。


     * Tree, recursive.
     * In BST, root is the largest of left subtree, smallest of right subtree.
     * In-order successor is the smallest of all node's larger than p.
     * Then we can do a binary search like recurse to search the result.
     * <p>
     * If p's val < root's , p's inorder successor can be in the left subtree or is root.
     * | If left subtree recursive call returns null, return root.
     * | Otherwise return the left subtree result.
     * If p's >= root's val, p's inorder successor is in the right subtree.
     * Base case:
     * If root == null, root is null or reach a null node, just return null.
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) {
            return null;
        }
        if (p.val < root.val) {
            TreeNode succ = inorderSuccessor(root.left, p);
            // If not found in left subtree, it's root.
            return succ == null ? root : succ;
        } else {
            return inorderSuccessor(root.right, p);
        }
    }

    /**方法二：非递归，同样的思想，但是值得看一下非递归的实现方法，以免面试中要求不能使用递归实现。
     * Tree, iterative.
     * If p's val < root's, go to the left subtree and update root as a candidate result.
     * If p's val >= root's, go to the right subtree.
     */
    public TreeNode inorderSuccessorB(TreeNode root, TreeNode p) {
        if (p.right != null) { // Pruning, if p has right subtree.
            for (TreeNode cur = p.right; cur != null; cur = cur.left) {
                p = cur; // Successor will be leftmost leaf of right subtree.，这一个特点很重要
            }
            return p;
        }
        TreeNode succ = null;
        while (root != null) {
            if (p.val < root.val) {
                succ = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return succ;
    }

}
