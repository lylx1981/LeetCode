
public class SymmetricTree {

    /** //方法一A：非递归方法，用Stack，DFS,代码有点长，而且这里是用DFS，是最树的最外围向内部一点点验证，
     * Iterative. Stack. DFS.
     * Skip root since it's definitely symmetric.
     * Push root's children onto a stack if they both exists.
     * While stack is not empty:
     * | Pop two nodes n1 and n2 from stack to compare.
     * | If they are both null, continue.
     * | If only one of them is null, or they are not the same value:
     * |   Not symmetric, return false.
     * | Push their children onto stack for the next iteration:
     * |   In a symmetric way, n1.left and n2.right, then n1.right and n2.left.
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left == null || root.right == null) {
            return root.left == root.right;
        }
        Deque<TreeNode> s = new ArrayDeque<>(); //这个类即可以用作一个Quene，也可以用作一个Stack。From Java Doc：This class is likely to be faster than Stack when used as a stack, and faster than LinkedList when used as a queue.
        s.push(root.left);
        s.push(root.right);
        while (!s.isEmpty()) {
            TreeNode n1 = s.pop();
            TreeNode n2 = s.pop();
            if (n1.val != n2.val) {
                return false;
            }
            if (n1.left != null && n2.right != null) { // Both are not null.
                s.push(n1.left); //加n1的左child
                s.push(n2.right);// n1的左child对称的应该是n2的右child,所以把这两个节点加进去。
            } else if (n1.left != null || n2.right != null) { // Only one of them is null.
                return false;
            }
            if (n1.right != null && n2.left != null) {
                s.push(n1.right);
                s.push(n2.left);
            } else if (n1.right != null || n2.left != null) {
                return false;
            }
        }

        return true;
    }
    /*
     方法一B：也可以用Quene实现上述非递归方法，这样的话，就是BFS了，基本思想就是一层一层向下考虑，并且每次把下一层应该对称的节点加进去，而不是从左往右加
    */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> queue = new LinkedList();
        queue.offer(root.left);
        queue.offer(root.right);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while(size > 0) {
                TreeNode node1 = queue.poll();
                TreeNode node2 = queue.poll();
                size -= 2;
                if (node1 == null && node2 == null) continue;
                if (node1 == null || node2 == null || node1.val != node2.val) return false;
                queue.offer(node1.left);
                queue.offer(node2.right);
                queue.offer(node1.right);
                queue.offer(node2.left);
            }
        }
        return true;
    }
    
    /**方法二：递归方法，更简单
     * Recursive. DFS.
     * Depth first search both subtrees, n1 and n2.
     * If at least one node is null:
     * | Return true if they are both null, otherwise false.
     * Then check both current nodes values.
     * Check n1.left and n2.right.
     * Check n1.right and n2.left.
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return dfs(root.left, root.right);
    }

    private boolean dfs(TreeNode n1, TreeNode n2) {
        if (n1 == null || n2 == null) {
            return n1 == n2; //其实这里直接返回false也可以，九章就是这样写的
        }
        return n1.val == n2.val && dfs(n1.left, n2.right) && dfs(n1.right, n2.left);
        //满足上面3个条件就是对称的，也就是n1,n2有相同的值，且n1的左Child节点和n2右Child节点对称，n1右节点和n2左节点对称。
    }

}
