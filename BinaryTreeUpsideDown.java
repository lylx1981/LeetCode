public class Solution {
    
    /*
 主要思想： 递归的方法比较简单，假设以左孩子为根的树已经变换好了（newRoot就是整个变换好的树的根，而原先root.left这个左孩子就在这个树的某个位置，而root.left就是指针一直是指向这个节点的）。现在要做的就是把Root的右孩子变为Root的左孩子节点的左孩子，而root自己变为Root左孩子节点的右孩子，同时，设置root所对应节点的左右指针为空。

这道题学习到的地方是，root.left只是一个指针，其所指向的节点可能早就埋没在以（newRoot为新root的树里了。体会指针的作用，其一直指向一个节点。

    */
    
    
  public class BinaryTreeUpsideDown {

    /**
     * Recursive.
     * Observations:
     * Leftmost child becomes the new root, suppose it's parent is p.
     * p's right child becomes the new root's left.
     * p itself becomes the new root's right.
     * Then set p to a leaf.
     * Return new root.
     * <p>
     * Implementation:
     * Recurse down to the leftmost leaf, which is the new root.
     * From its parent p, we can set new root's left to p's right.
     * Then new root's right to p.
     * Finally disconnect p from both its children.
     * Move on to the previous parent.
     */
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        TreeNode newRoot = upsideDownBinaryTree(root.left);
        root.left.left = root.right;
        root.left.right = root;
        root.left = null;
        root.right = null;
        return newRoot;
    }

    /**
     * Iterative.
     * All right subtrees only have 1 node.
     * Move down along the left children.
     * While current node is not null:
     * | Store the next left child.
     * | Set current node's left to previous right.
     * | Update right to current right.
     * | Set current node's right to previous.
     * | Move on by updating prev to curr, curr to next.
     * Return prev.
     */
    public TreeNode upsideDownBinaryTreeB(TreeNode root) {
        TreeNode curr = root;
        TreeNode prev = null; // Previous root.
        TreeNode next = null; // Next node to flip.
        TreeNode right = null; // Previous right child.

        while (curr != null) {
            // Store next.
            next = curr.left;
            // Swap nodes.
            curr.left = right; // Current left is previous right.
            right = curr.right; // Update right.
            curr.right = prev; // Current right is previous root.
            // Move on.
            prev = curr;
            curr = next;
        }
        return prev;
    }

}
