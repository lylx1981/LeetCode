class Solution {
        /*
         * 
         
    思路： 主要是分析如何能把一个环打开。
    
    变成环后，其实还是可以用动态规划来解。
    
    f(n) : 代表窃贼在前n个房子最多能拿到多少钱
    
    在变成环后，f(n)不能容许同时盗窃第1和第n个房子的情况。那么就分情况解决。
    
    绝对不偷第1个房子，只在其余房子中偷（其实也已经覆盖了第n个房子现在可以被偷也可以不被偷的情况）。也就是排除掉第1个房子，在剩余的第2->n个房子里偷；
    绝对不偷第n个房子（其实也已经覆盖了第1个房子现在可以被偷也可以不被偷的情况），那么其实就是求f(n - 1)
    
    两个都不盗窃的情况，已经被上面两种情况包含了，所以只需要计算上面两种情况即可。
    
    按照这2种情况计算出的f1、f2，求出max(f1，f2)，就是要得到的解。
    
    代码只需要把原来的Rob1的程序稍加改动，带上开始，结束索引即可，然后对其2次调用取最大值就行了。
    
    Leetcode评论最多的那个Post的解释其实不太好，应该是两面这种解释最清晰。   
         */
   
     /**
     * DP. More compact code.
     */
    public int rob1(int[] nums, int lo, int hi) {
        int preMax = 0;
        int max = 0;
        for (int i = lo; i <= hi; i++) {
            int curMax = Math.max(preMax + nums[i], max);
            preMax = max;
            max = curMax;
        }
        return max;
    }
    
    public int rob(int[] nums) {
        if (nums.length == 1) return nums[0];
        return Math.max(rob1(nums, 0, nums.length - 2), rob1(nums, 1, nums.length - 1));
    }
}
