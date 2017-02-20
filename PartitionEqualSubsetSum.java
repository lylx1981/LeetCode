/* 思路： 思路：这道题没什么思路，也没有想到DP可以解决这个问题，一直总是把DP想成算数相关的推导，但是这道题很好的展示了即使是boolean类型的，也可以归纳为DP。特别的，由于这道题没有让具体返回实例，只让求False还是True,所以DP推导表达式并不难，这个特别出乎自己的意料。自己一直认为DP是没办法解这种题型的。尤其是判断每一步选与不选也可以写成DP的模式，非常值得学习。



This problem is essentially let us to find whether there are several numbers in a set which are able to sum to a specific value (in this problem, the value is sum/2).

Actually, this is a 0/1 knapsack problem, for each number, we can pick it or not. Let us assume dp[i][j] means whether the specific sum j can be gotten from the first i numbers. If we can pick such a series of numbers from 0-i whose sum is j, dp[i][j] is true, otherwise it is false.

Base case: dp[0][0] is true; (zero number consists of sum 0 is true)

Transition function: For each number, if we don't pick it, dp[i][j] = dp[i-1][j], which means if the first i-1 elements has made it to j, dp[i][j] would also make it to j (we can just ignore nums[i]). If we pick nums[i]. dp[i][j] = dp[i-1][j-nums[i-1]] （因为第i个数在nums里面的其实下标是i-1,所以是nums[i-1]！！！！）, which represents that j is composed of the current value nums[i] and the remaining composed of other previous numbers. Thus, the transition function is dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]]

talking is cheap:
*/


public boolean canPartition(int[] nums) {
    int sum = 0;
    
    for (int num : nums) {
        sum += num; //先求和
    }
    
    if ((sum & 1) == 1) {
        return false; //和如何使奇数，肯定不可能分割成2个和一样的Set，直接返回False。
    }
    sum /= 2;

    int n = nums.length;
    boolean[][] dp = new boolean[n+1][sum+1]; //注意这里的数组各个维度的大小
    for (int i = 0; i < dp.length; i++) {
        Arrays.fill(dp[i], false);
    }
    
    dp[0][0] = true;
    
    for (int i = 1; i < n+1; i++) {
        dp[i][0] = true; //一直都不取任何元素，所以一直Sum都是0，所以为True
    }
    for (int j = 1; j < sum+1; j++) {
        dp[0][j] = false;// 如果一个元素都不让取，那么一个j都不可能达到（对于j>=1）。
    }
    
    for (int i = 1; i < n+1; i++) {
        for (int j = 1; j < sum+1; j++) {
            dp[i][j] = dp[i-1][j]; //如果不取当前的i;注意第i个数在nums里其实下标是i-1,所以对应的数是nums[i-1]
		//如果当前要达到的j还大于nums[i-1],那么假如我现在  我在看看如果我当前pick第i个数的结果
            if (j >= nums[i-1]) {
                dp[i][j] = (dp[i][j] || dp[i-1][j-nums[i-1]]);
            }
        }
    }
   
    return dp[n][sum];
}

//更加优化空间复杂度的代码
public boolean canPartition(int[] nums) {
    int sum = 0;
    
    for (int num : nums) {
        sum += num;
    }
    
    if ((sum & 1) == 1) {
        return false;
    }
    sum /= 2;
    
    int n = nums.length;
    boolean[] dp = new boolean[sum+1];
    Arrays.fill(dp, false);
    dp[0] = true;
    
    for (int num : nums) {
        for (int i = sum; i > 0; i--) {
            if (i >= num) {
                dp[i] = dp[i] || dp[i-num];
            }
        }
    }
    
    return dp[sum];
}
