public class Solution {
    
     /** 这道题九章以及Leetcode都给出了很多方法，但是都很不容易理解，下面的这个Solution是最好理解的。
      * 思路：不断的判断当前Root（从树的真正Root开始），将其右子树剪接到左子树最右侧的那个节点上，再把Root的右子树设置为其原来的左子树，最后把Root的左子树设置为空。现在左子树已经完全没有了，Root只有右子树了（但整个右子树目前不一定是Flat的），然后继续从Root的右孩子开始，继续对它开始进行上面同样的操作，最终就获得了一个全局都flat的树。
      * 
     * addRecursive root's right subtree to left node's rightmost child
     * Then set that subtree as root's right subtree
     * And set root's left child to null
     * Move root to its right child and repeat
     */
    public void flatten(TreeNode root) {
        while (root != null) {
            if (root.left != null) { // check left child
                TreeNode n = root.left;
                while (n.right != null) n = n.right; // rightmost child of left
                n.right = root.right; // insert right subtree to its right
                root.right = root.left; // set left subtree as right subtree
                root.left = null; // set left to null
            }
            root = root.right; // move to right child
        }
    }

}
