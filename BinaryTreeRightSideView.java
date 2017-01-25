/**方法一： BFS最好理解，一层一层加进Queue，然后每层最后一个Queue里的元素就是该层需要的结果。注意该题也可以转化为Left-View。
     * BFS.
     * Do a level order traversal with queue.
     * For each level, traverse each node and add children to the queue.
     * If it's at the end of current level, add node's value to result.
     * <p>
     * Easy to understand, but queue.poll() and queue.offer() can be slow.
     */
     
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res; //这里稍微改动了一下，不然没法通过Leetcode，原来是return null.
        }
        
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                if (i == size - 1) {
                    res.add(node.val);
                }
            }
        }
        return res;
    }
    
    
    /**方法二：DFS，性能更好。每次只会加该层第一个检查的元素，而且由于首先检查的是右孩子，所以保证了每次加的都是该层最右侧的那个孩子。很巧的方法。
     * DFS. Backtracking.
     * Base case: if current node is null, just return.
     * Visit: if depth equals result size, means that it's at the correct level, add current node's value to result.
     * Next: recurse down next level with right node first and depth + 1. Then left node.
     * <p>
     * Faster than BFS since there is no enqueue or dequeue.
     *    /**
     * 
     * Neat algorithm... Just want to add 3 points:
    (1) the traverse of the tree is NOT standard pre-order traverse. It checks the RIGHT node first and then the LEFT
    (2) the line to check currDepth == result.size() makes sure the first element of that level will be added to the result list
    (3) if reverse the visit order, that is first LEFT and then RIGHT, it will return the left view of the tree..
     */
    
     
    public List<Integer> rightSideViewDFS(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(res, root, 0);
        return res;
    }

    private void helper(List<Integer> res, TreeNode node, int depth) {
        if (node == null) {
            return;
        }
        if (depth == res.size()) {
            res.add(node.val);
        }
        helper(res, node.right, depth + 1); // Recurse right node first
        helper(res, node.left, depth + 1);
    }

 


