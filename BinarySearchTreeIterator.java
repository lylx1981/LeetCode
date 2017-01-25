public class BSTIterator {

    Deque<TreeNode> stack;

    /**思路：主要就是仿真一个中序遍历，因为中序便利在BST里的效果就是按大小从小到大输出。这个题其实和95题非常相关，95题就是如何写中序遍历的。每次next（）函数调用都会从Stack里面拿出最上面的一个元素，那么同时立即把这个元素的右子树进行putAll操作，这个操作完毕后再从Stack里pop处的元素就是下一个需要取出的元素了。
     * Simulate in-order traversal.
     * Push all left children into a Stack to get prepared.
     */
    public BSTIterator(TreeNode root) {
        stack = new ArrayDeque<>();
        pushAll(root);
    }

    /**
     * If the stack is empty, there is no more node left.
     */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /**
     * Imagine all left subtree of a node is popped out.
     * The next will be itself.
     * And then the next will be its right subtree.
     * The right subtree repeats the pattern of pushing all left children into a stack.
     */
    public int next() {
        TreeNode n = stack.pop();
        pushAll(n.right); // Left subtree and root is done. Repeat on right subtree.
        return n.val;
    }

    private void pushAll(TreeNode root) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }
}
