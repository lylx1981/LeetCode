
    /** 
    思路：递归，从头节点开始，每往下一层，只要下面的左或者右孩子和当前考察节点是连续的，也就是值增1，则积累的路径长度加1（同时有必要的话更新当前全局最长的路径变量），然后进入下一层的递归循环（把当前路径长度值也传给下一层循环）。否则的话，路经长度回归为1，然后进入下一层循环（因为连续的路断了，那么只能从头开始计算）。


     */
public class Solution {
    private int max = 0;
    public int longestConsecutive(TreeNode root) {
        if(root == null) return 0;
        helper(root, 0, root.val);
        return max;
    }
    
    public void helper(TreeNode root, int cur, int target){
        if(root == null) return;
        if(root.val == target) cur++;
        else cur = 1;
        max = Math.max(cur, max);
        helper(root.left, cur, root.val + 1);
        helper(root.right, cur, root.val + 1);
    }
}
