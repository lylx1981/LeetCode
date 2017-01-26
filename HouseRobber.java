/*
思路：DP，注意刚开始把问题想的太简单了，以为就是奇树层相加，偶数层相加取两个中的最大就行了。其实是允许2行或多行都不偷的，所以是一个DP的问题。具体是涉及DP子结构，对于一个考察节点，以其为根的树的最大可能偷的钱取决于其各自子树的情况，同时各自子树的情况又取决于子树根到底被不被偷，因为子树的根如何被偷了的话，当前考察节点就不能被偷了。所以定义dp（i）函数返回的是一个含2个元素的数组A，A[0]表示以i为根的子树不偷根节点能获得的最高价值，A[1]表示以i为根的子树偷根节点能获得的最高价值
*/

public class Solution {
    public int rob(TreeNode root) {
        int[] ans = dp(root);
        return Math.max(ans[0], ans[1]);
    }
    public int[] dp(TreeNode root) {
        if (root == null) {
            int[] now = new int[]{0, 0};
            return now;
        }
        int[] left = dp(root.left);
        int[] right = dp(root.right);
        int[] now = new int[2];//对当前考察节点也定一个now数组变量
        now[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]); //now[0]代表不偷当前节点，既然不偷当前节点了，那么左右子树的根节点，也就是其左右孩子无所谓偷与不偷都可以，所以都取其左右孩子结果中最大的那个就行了。
        now[1] = left[0] + right[0] + root.val;//now[1]是说当前节点如果要被偷的情况，那么其孩子节点就不能被偷了，所以对于其孩子节点的返回结果，只能取其不被偷的结果。
        return now;
    }
}
