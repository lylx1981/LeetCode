/*这道题给了我们一个二叉树，让我们返回其每层的叶节点，就像剥洋葱一样，将这个二叉树一层一层剥掉，最后一个剥掉根节点。那么题目中提示说要用DFS来做，思路是这样的，每一个节点从左子节点和右子节点分开走可以得到两个深度，由于成为叶节点的条件是左右子节点都为空，所以我们取左右子节点中较大值加1为当前节点的深度值，知道了深度值就可以将节点值加入到结果res中的正确位置了，。*/
 
public class FindLeavesOfBinaryTree {

    /**
     * Tree, DFS(Backtracking).
     * Make use of one property of tree node, its getHeight.
     * Height is the number of edges from the node to the deepest leaf.
     * So leaf node will have getHeight 0.
     * This problem is just aggregating all nodes with same getHeight into a list.
     */
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        getHeight(root, res);
        return res;
    }

    /**
     * Return the getHeight of a node.
     * Recurrence relation:
     * getHeight(node) = 1 + max(getHeight(node.left), getHeight(node.right))
     * Base case:
     * If node is null, it's getHeight is -1.
     */
    private int getHeight(TreeNode node, List<List<Integer>> res) {
        if (null == node) {
            return -1;
        }
        int height = 1 + Math.max(getHeight(node.left, res), getHeight(node.right, res));
        if (res.size() == height) { // Current height exceeds result list size.
            res.add(new ArrayList<>());
        }
        res.get(height).add(node.val);
        // root.left = root.right = null;
        return height;
    }
}
    
    /*方法二，C++ 版本 仅供参考
    下面这种DFS方法没有用计算深度的方法，而是使用了一层层剥离的方法，思路是遍历二叉树，找到叶节点，将其赋值为NULL，然后加入leaves数组中，这样一层层剥洋葱般的就可以得到最终结果了：
    性能应该不如上面那个
*/

class Solution {
public:
    vector<vector<int>> findLeaves(TreeNode* root) {
        vector<vector<int>> res;
        while (root) {
            vector<int> leaves;
            root = remove(root, leaves);
            res.push_back(leaves);
        }
        return res;
    }
    TreeNode* remove(TreeNode *node, vector<int> &leaves) {
        if (!node) return NULL;
        if (!node->left && !node->right) {
            leaves.push_back(node->val);
            return NULL;
        }
        node->left = remove(node->left, leaves);
        node->right = remove(node->right, leaves);
        return node;
    }
};
