public class Solution {
    
    /**思路：这道题非常重要，自己考虑的有点复杂了，直接用DFS做就可以了，同时每往下深入一层，就把当前已经计算好的值*10进位就可以了，然后把这个值继续传给下一层的DFS递归调用即可。

    通过此题，可以考虑几个不同的方法：
    1。Bracktracking Vs. DFS, 其实两者基本都是一样的，也就是DFS过程中可以进一步实现backtracking.但是如果只是计算最终结果，那么只要DFS就可以了，但如果还需要清晰的打印出来每个具体路径，那么就需要Backtracking操作。
    
    2。DFS vs. DP. 这道题刚开始想的能不能用类似DP，也就是Root的结果值，可以由Root左右孩子的结果值计算得来，也就是在计算Root孩子的结果值的时候（相当于第1，2，。。。n-1步）不用Root的(也就是第n步)的任何信息，结果发现这样行不通， 因为Root孩子计算出来的已经是个结果值了，没有办法确定这个值是由多少个路径和组成的，而这些路径现在头上都要加一个Root节点作为最高位才行，所以没法计算.所以DP的想法在这里行不通。而DFS的方法就可以，每一步都将当前Root计算好的值乘以10然后继续递归到DFS的下一层去。

     * Recursive, DFS
     * Build a helper function to pass cur result
     * If its leaf node, just return the val
     * Otherwise, goes to left root first then right root with current value
     */
    public static int sumNumbers(TreeNode root) {
        int res = 0;
        if (root == null) return res;
        return helper(root, 0);
    }
    
   
    public static int helper(TreeNode root, int x) {
        if (root.right == null && root.left == null) return 10 * x + root.val;
        
        int val = 0;
        if (root.left != null) val += helper(root.left, 10 * x + root.val);
        if (root.right != null) val += helper(root.right, 10 * x + root.val);
        return val;
    }
      
    

}
