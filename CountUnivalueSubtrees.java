
/*
    思路：递归，同时设置count用来计数，这里用一个count[]数组类型来充当全局变量，是一个好的方法，因为Count实际传递的是指针同样达到了全局变量的作用，避免了定义全局变量。
    helper函数验证一个给定的树是不是uni-tree,同时里面也计算相应的count. 这是一个非常重要的技巧(也就是递归函数完成两种功能，一种是判断功能，同时也做计算功能，同时计算的结果保存在count数组里，因为数组传递的是指针，那么达到了全局变量的效果) 。
*/
     


public class Solution {
    public int countUnivalSubtrees(TreeNode root) {
        int[] count = new int[1];
        helper(root, count);
        return count[0];
    }
    
    private boolean helper(TreeNode node, int[] count) {
        if (node == null) {
            //如果node是空，则自动返回True，因为一个空树可以看作是一个uni-tree
            return true;
        }
        boolean left = helper(node.left, count);
        boolean right = helper(node.right, count);
        if (left && right) { //左右子树都不为空的时候判断他们是否和当前node.val相等，如果相等，则以node为根的树就是一个新的uni-tree 
            if (node.left != null && node.val != node.left.val) { //这里要继续判断node.left != null是因为上面node.left为空的时候，其对应的返回的left也是True
                return false;
            }
            if (node.right != null && node.val != node.right.val) {
                return false;
            }
            count[0]++;
            return true; //count++, 同时返回True，表示以当前node为根的树是一个uni-tree
        }
        return false; //如果只要不满足上述情况，则说明以当前node为根的树肯定不是uni-tree
    }
}
