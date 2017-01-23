
public class MaximumDepthOfBinaryTree {

    /** 方法一：递归最简单容易懂
     * Recursive. DFS. O(n) Time.
     * Base case:
     * If root is null, return 0.
     * Recurrence relation:
     * Depth of current node is the maximum of left depth and right depth + 1.
     */
    private int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }

    /**方法二：DFS用Stack，而且保持两个Stack，一个存节点，一个存对应的节点层数（因为DFS过程中会在各个层周游），两个Stack保持时刻对应节点的同步，
     * Iterative. DFS. Stack.
     * One stack for DFS. Another for depth.
     * Push root onto stack to start.
     * While stack is not empty:
     * | Pop node from stack. Pop depth from the other stack.
     * | Update max value.
     * | Add left and right children to stack.
     * Return max depth.
     */
    private int maxDepthB(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Integer> depths = new ArrayDeque<>();
        stack.push(root);
        depths.push(1);
        int maxDepth = 0;
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            int d = depths.pop();
            maxDepth = Math.max(d, maxDepth);
            if (node.left != null) {
                stack.push(node.left);
                depths.push(d + 1);
            }
            if (node.right != null) {
                stack.push(node.right);
                depths.push(d + 1);
            }
        }
        return maxDepth;
    }

    /**方法三：BFS，一层一层加就行了，用Queue，因为最后总归会加到最后一层，那么直接返回但前Depth就可以了，就一定是最大depth了
     * BFS. Level Order Traversal.
     * Increase depth for each level.
     */
    public int maxDepthC(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            depth++;
        }
        return depth;
    }
}
