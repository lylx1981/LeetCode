/*
思路：注意图中点的权值有可能为负，这是刚开始没有考虑到的情况。
另外，这道题主要思路是递归尤其是分治。对于一个考察节点，其左右子树的两个相关数据对当前考察节点有用：
1。从左或者右孩子为起点的在各自子树里的最大和路径（可能是0），代码中用singlePath表示。这个路径是潜在的可以延长到当前考察节点的路径，叫a, b。
2。在左右子树中各自的可以从任意点开始的最大和路径，代码中用maxPath表示。这个路径虽然不能延长至当前考察节点，但是需要在当前考察节点处进行比较判断,叫c,d

所以当判断当前考察节点的时候，也需要判断上面的1，2。 也就是1）以当前考察节点为起点的最大和路径，2）以当前考察节点为根节点的树中可以以任意节点开始的最大和路径

为了计算当前节点的1，它会用到a, b, 也就是在a,b, （同时加上一个0） 里面选最大的一个即可，让其延伸到当前节点（见----代码1处）
为了计算当前节点的2，它会用到c,d以及a+b+root.val(也就是把a,当前节点，b整个连起来的路径)， 见---代码2处

在递归调用中对每个节点进行计算1，2。

另外，注意这一题再次应用了如何在递归调用中需要2个信息元素的情况（直接定义了一个类）。


singlePath表示以该点为root的子树从root出发能够取的最大path，maxPath表示以该点为root的子树中任意一点到任意一点能取的最大path。+

对于每一个node，以该点为root的子树的最大path有两种可能：第一种为不经过该root的path，第二种为经过该root的path。对于第一种情况，最大path为root左边或者右边的最大path，第二种为左边的singlePath + 右边的singlePath＋root值。


*/

public class Solution {
    private class ResultType {
        // singlePath: 从root往下走到任意点的最大路径，这条路径可以不包含任何点
        // maxPath: 从树中任意到任意点的最大路径，这条路径至少包含一个点
        int singlePath, maxPath; 
        
        ResultType(int singlePath, int maxPath) {
            this.singlePath = singlePath;
            this.maxPath = maxPath;
        }
    }

    private ResultType helper(TreeNode root) {
        if (root == null) {
            return new ResultType(0, Integer.MIN_VALUE);
        }
        // Divide
        ResultType left = helper(root.left);
        ResultType right = helper(root.right);

        // Conquer
        int singlePath = Math.max(left.singlePath, right.singlePath) + root.val; //--------------------1
        singlePath = Math.max(singlePath, 0);

        int maxPath = Math.max(left.maxPath, right.maxPath);
        maxPath = Math.max(maxPath, left.singlePath + right.singlePath + root.val); //-----------------2

        return new ResultType(singlePath, maxPath);
    }

    public int maxPathSum(TreeNode root) {
        ResultType result = helper(root);
        return result.maxPath;
    }
}
