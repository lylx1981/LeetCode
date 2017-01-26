public class KthSmallestElementInABst {

    private KthSmallestElementInABst k;

    private int res;
    private int count;

    
    /**
     * 中序便利，到达第k个元素，输出即可。非递归方法（用Stack）更好理解。
     * Iterasive solution with stack.
     */
    public int kthSmallestB(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        int count = k;
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                count--; //没从Stack里出来一个元素就Count--，因为就是按从小到大输出了下一个应该输出的元素。
                if (count == 0) {
                    return root.val; //Count减为0的时候，这就是答案。
                }
                root = root.right;
            }
        }
        return -1;
    }
    
    /**
     * 递归的方法比较难理解。
     * Recursive solution with in-order traversal helper.
     */
    public int kthSmallest(TreeNode root, int k) {
        count = k;
        traverse(root);
        return res;
    }

    private void traverse(TreeNode node) {
        if (node.left != null) {
            traverse(node.left);
        }
        count--; //刚开始的时候其实一直在进上面那个递归循环，根本不到这一步，只有到达最左侧的那个叶子节点，也就是最小值的节点的时候，才第一次开始count--
        if (count == 0) {
            res = node.val;
            return;
        }
        if (node.right != null) {
            traverse(node.right);
        }
    }

    

    /** 网上大家推荐的优化方法：也就是在每个节点上存储左子树的节点个数，那么其节点Ranking，就是左子树节点个数再+1
     * Binary search for left subtree node count.
     * * For BST, the # of nodes of left subtree plus one is actually the node's ranking.
     * 
     * 当有上述节点结构的时候，就可以进行二分查找了，比如1）k<=节点左子树个数，那么继续在左子树找，2）如果k等于节点左子树个数+1，则当前节点就是要找的点，3）如果k>当前左子树个数+1，说明要找的在右子树，且在右子树的ranking应该是k-(节点左子树个数+1) = k-节点左子树个数-1
     * 
     * 类似的Leetcode上的分析也拷贝在这里：
             * O(h) (h = height) time complexity by modify TreeNode structure and add left subtree node count and find kth smallest element base on (http://www.geeksforgeeks.org/find-k-th-smallest-element-in-bst-order-statistics-in-bst/)
        
        The idea is to maintain rank of each node. We can keep track of elements in a subtree of any node while building the tree. Since we need K-th smallest element, we can maintain number of elements of left subtree in every node.
        
        Assume that the root is having N nodes in its left subtree. If K = N + 1, root is K-th node. If K <= N, we will continue our search (recursion) for the Kth smallest element in the left subtree of root. If K > N + 1, we continue our search in the right subtree for the (K – N – 1)-th smallest element. Note that we need the count of elements in left subtree only.
     */
    public int kthSmallestC(TreeNode root, int k) {
        int count = countNodes(root.left);
        if (k <= count) {
            return kthSmallest(root.left, k);
        } else if (k > count + 1) {
            return kthSmallest(root.right, k - 1 - count); // 1 is counted as current node
        }
        return root.val;
    }
    
    /**
     * Count how many nodes in this subtree rooted from n.
     * If we can modify the data structure, we can save the count with each node.
     */
    private int countNodes(TreeNode n) {
        if (n == null) {
            return 0;
        }
        return 1 + countNodes(n.left) + countNodes(n.right);
    }
