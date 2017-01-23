public class BinaryTreeLevelOrderTraversal {

    /** 思路：用Quene，一层一层加
     * BFS.
     * Instead of regular BFS, visit one level at each iteration.
     * By getting the size of the queue, we know how many nodes in current level.
     */
    private List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        List<Integer> curLevel = new LinkedList<>();

        while (!queue.isEmpty()) {
            for (int i = queue.size(); i > 0; i--) {
                TreeNode n = queue.poll();
                curLevel.add(n.val);
                if (n.left != null) {
                    queue.add(n.left);
                }
                if (n.right != null) {
                    queue.add(n.right);
                }
            }
            res.add(new ArrayList<>(curLevel));
            curLevel.clear();
        }

        return res;
    }

    /** 思路，DFS，每到一层，把那一层碰到的点加到其对应那一层的list中
     * DFS.
     * Root is level 0, pass level + 1 to its children during DFS.
     * Add the node to its level list in the result.
     * Stop when reach null node.
     */
    public List<List<Integer>> levelOrderB(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(root, 0, res);
        return res;
    }

    public void dfs(TreeNode root, int level, List<List<Integer>> res) {
        if (root == null) {
            return;
        }
        // Visit.
        if (res.size() <= level) { //如果第一次到这一层，而且还没有该层的list,就生成一个新的。
            res.add(new ArrayList<>());
        }
        res.get(level).add(root.val);
        // Recurse to left and right child.对于Child，先加左边，再加右边，这样可以保证顺序最后是从左向右排的
        dfs(root.left, level + 1, res);
        dfs(root.right, level + 1, res);
    }
}
