    /* 方法一：DFS即可，并且每次从左右子树开始，都要重新从sum开始判断一次.O(N^2)复杂度       
    Each time find all the path start from current node
    Then move start node to the child and repeat.
    Time Complexity should be O(N^2) for the worst case and O(NlogN) for balanced binary Tree.*/

public class Solution {
    public int pathSum(TreeNode root, int sum) {
        if(root == null)
            return 0;
        return findPath(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
        //注意上面调用的三个函数里，findpath是对root节点进行的处理（左右子数中Sum的值会减去当前root的值），而同时后面的2个函数是重新从左右节点开始，并且重新从sum值开始
    }
    
    public int findPath(TreeNode root, int sum){
        int res = 0;
        if(root == null)
            return res;
        if(sum == root.val)
            res++;
        res += findPath(root.left, sum - root.val);
        res += findPath(root.right, sum - root.val);
        return res;
    }
   
}
       
        /*另一个优化的方法，只有O（n）的复杂度，利用了preSum,前缀和，以及backtrack。可以看看下面的解释，值得借鉴。
        
        This is an excellent idea and really took me some time to figure out the logic behind.
        Hope my comment here could help others in understanding this solution.
        
        The prefix stores the sum from the root to the current node in the recursion
        The map stores <prefix sum, frequency> pairs before getting to the current node. We can imagine a path from the root to the current node. The sum from any node in the middle of the path to the current node = the difference between the sum from the root to the current node and the prefix sum of the node in the middle.
        We are looking for some consecutive nodes that sum up to the given target value, which means the difference discussed in 2. should equal to the target value. In addition, we need to know how many differences are equal to the target value.
        Here comes the map. The map stores the frequency of all possible sum in the path to the current node. If the difference between the current sum and the target value exists in the map, there must exist a node in the middle of the path, such that from this node to the current node, the sum is equal to the target value.
        Note that there might be multiple nodes in the middle that satisfy what is discussed in 4. The frequency in the map is used to help with this.
        Therefore, in each recursion, the map stores all information we need to calculate the number of ranges that sum up to target. Note that each range starts from a middle node, ended by the current node.
        To get the total number of path count, we add up the number of valid paths ended by EACH node in the tree.
        Each recursion returns the total count of valid paths in the subtree rooted at the current node. And this sum can be divided into three parts:
        - the total number of valid paths in the subtree rooted at the current node's left child
        - the total number of valid paths in the subtree rooted at the current node's right child
        - the number of valid paths ended by the current node
        The interesting part of this solution is that the prefix is counted from the top(root) to the bottom(leaves), and the result of total count is calculated from the bottom to the top :D
        
        The code below takes 16 ms which is super fast.*/

    public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        return findPathSum(root, 0, sum, map);  
    }
    private int findPathSum(TreeNode curr, int sum, int target, Map<Integer, Integer> map) {
        if (curr == null) {
            return 0;
        }
        // update the prefix sum by adding the current val
        sum += curr.val;
        // get the number of valid path, ended by the current node
        int numPathToCurr = map.getOrDefault(sum-target, 0); 
        // update the map with the current sum, so the map is good to be passed to the next recursion
        map.put(sum, map.getOrDefault(sum, 0) + 1);
        // add the 3 parts discussed in 8. together
        int res = numPathToCurr + findPathSum(curr.left, sum, target, map)
                                               + findPathSum(curr.right, sum, target, map);
       // restore the map, as the recursion goes from the bottom to the top
        map.put(sum, map.get(sum) - 1);
        return res;
    }
