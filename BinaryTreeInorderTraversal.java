
    /** 思路: 题目要求用非递归的方法，所以就用Stack，一直向最左边找并加入Stack，直到尽头后，从Stack里pop出来一个，加入结果集后，继续向其右子树进行遍历。
     * Stack solution, O(n) Space
     * Use a stack to store TreeNodes
     * Go to left most and add each node
     * Pop the node from stack, add its value, and try to go right
     * Stop if stack is empty or node is null
     */
    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        ArrayList<Integer> result = new ArrayList<Integer>();
        TreeNode curt = root;
        while (curt != null || !stack.empty()) {
            while (curt != null) { //一直向左找
                stack.add(curt);
                curt = curt.left;
            }
            curt = stack.peek();
            stack.pop();
            result.add(curt.val);
            curt = curt.right;
        }
        return result;
    }

    /** 优化方法，了解即可。
     * <strong>Morris Traversal</strong>
     * O(1) space
     * Use cur for current node, pre for predecessor of cur node
     * Check whether left subtree exists
     * If yes, find rightmost node in left subtree
     * Check whether rightmost node is connected with cur node
     * If connected, break the connection and visit and move to right subtree
     * Otherwise, connect and traverse left subtree
     * If no, visit cur node and traverse right subtree
     */
    public static List<Integer> inorderTraversalB(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root == null) {
            return res;
        }
        TreeNode cur = root;
        TreeNode pre = null;
        while (cur != null) {
            if (cur.left == null) {
                res.add(cur.val); // visit
                cur = cur.right; // move to right
            } else {
                pre = cur.left;
                while (pre.right != null && pre.right != cur) {
                    pre = pre.right;
                }
                if (pre.right == null) { // connect with cur
                    pre.right = cur;
                    cur = cur.left; // traverse left subtree
                } else { // left subtree is done
                    pre.right = null;
                    res.add(cur.val); // visit
                    cur = cur.right; // move to right
                }
            }
        }
        return res;
    }
}
