// 思路： DP，这道题基本上是遇到的最难的题，非常不容易想到是用DP， 具体就是一种思路的转变。假设(left...right)中以i，作为分割点。先破i。那么 (left, i) and (i, right) 并不是互相独立的。因为他们之间还可以交际。但是，如果假设，i是最后一个破裂的，那么， (left, i) and (i, right) 就是互相独立的了。记住这种reverse想法在DP中的应用。 不过需要注意的是，这个时候的过程是倒过来的，i不是最先爆的，而是最后爆的。

// 这道题当时是通过分析最后一次操作状态和前一次操作状态上的关系。这里我们可以从最后一次burst重新考虑这个题。如果i是最后burst的，那么在这之前0~i-1,i+1~n-1这两个区间的气球都已经被消除了，而且。。。最重要的是，由于i是最后被消除的，所以左右两个区间的过程是完全被分开的，也就是说，我们找到了子问题。这里我们看一下

// max(left) + left(左边最后被消除) * nums[i] * right(右边最后被消除) + max(right)

// 可以看看Leetcode下面的这个描述：


// Be Naive First

// When I first get this problem, it is far from dynamic programming to me. I started with the most naive idea the backtracking.

// We have n balloons to burst, which mean we have n steps in the game. In the i th step we have n-i balloons to burst, i = 0~n-1. Therefore we are looking at an algorithm of O(n!). Well, it is slow, probably works for n < 12 only.

// Of course this is not the point to implement it. We need to identify the redundant works we did in it and try to optimize.

// Well, we can find that for any balloons left the maxCoins does not depends on the balloons already bursted. This indicate that we can use memorization (top down) or dynamic programming (bottom up) for all the cases from small numbers of balloon until n balloons. How many cases are there? For k balloons there are C(n, k) cases and for each case it need to scan the k balloons to compare. The sum is quite big still. It is better than O(n!) but worse than O(2^n).

// Better idea

// We then think can we apply the divide and conquer technique? After all there seems to be many self similar sub problems from the previous analysis.

// Well, the nature way to divide the problem is burst one balloon and separate the balloons into 2 sub sections one on the left and one one the right. However, in this problem the left and right become adjacent and have effects on the maxCoins in the future.

// Then another interesting idea come up. Which is quite often seen in dp problem analysis. That is reverse thinking. Like I said the coins you get for a balloon does not depend on the balloons already burst. Therefore
// instead of divide the problem by the first balloon to burst, we divide the problem by the last balloon to burst.

// Why is that? Because only the first and last balloons we are sure of their adjacent balloons before hand!

// For the first we have nums[i-1]*nums[i]*nums[i+1] for the last we have nums[-1]*nums[i]*nums[n].

// OK. Think about n balloons if i is the last one to burst, what now?

// We can see that the balloons is again separated into 2 sections. But this time since the balloon i is the last balloon of all to burst, the left and right section now has well defined boundary and do not affect each other! Therefore we can do either recursive method with memoization or dp.

// Final

// Here comes the final solutions. Note that we put 2 balloons with 1 as boundaries and also burst all the zero balloons in the first round since they won't give any coins.
// The algorithm runs in O(n^3) which can be easily seen from the 3 loops in dp solution.




Java DP
// 看下面这个英文的解释：最清晰！！ 基本思想就是把所有可能的left,right都Check一遍，对于某个特定的left,right,对其计算dp[left][right],计算方法就是依据上述的原理，对left,right之间的每一个位置i,假设其为最后一个被戳破的气球，然后用递推公式计算，并且求最大值并更新dp[left][right]。特别注意下面代码里面对DP[i][j]定义: dp[i][j]表示打爆区间[i,j]中的所有气球能得到的最多金币。并且最重要的 Oh, I have just realised that you excluded the left, right from the region of (left,right) section. It should be very helpful if you can add some comments of that into the code. Nice solution! ---这是最重要的dp[i][j]不包括把i,j的气球戳破，这两个气球长期一直存在的，就像在原数组头尾增加一个1一样，那两个节点的气球是不去删除的，仅作为边界存在。


//这个对下面的代码总结非常好，看看： This dp works in this way: we scan the array from len 2 to len n with all possible start points and end points. For each combination, we will find the best way to burst balloons. dp[i][j] means we are looking at a combination with start point at index i and end point at index j with len of j - i. In this combination, we use the third loop to find the best way to burst. “nums[left] * nums[i] * nums[right]” means we burst all balloons from left to i and all ballons from i to right. So only balloons left, i and right exits in current combination therefore we can do this operation. “+ dp[left][i] + dp[i][right]” means add the value from best burst in range(left, i) and range(i, right).



public int maxCoins(int[] iNums) {
    int[] nums = new int[iNums.length + 2];
    int n = 1;
    for (int x : iNums) if (x > 0) nums[n++] = x;
    nums[0] = nums[n++] = 1; //两头补1，作为边界


    int[][] dp = new int[n][n];
    for (int k = 2; k < n; ++k) //设置不同的步长
        for (int left = 0; left < n - k; ++left) { //针对每个left起点，对每个当前的步长来确定right
            int right = left + k;
            for (int i = left + 1; i < right; ++i) //对当前特定的left,right开始进行考察其dp[i][j]
                dp[left][right] = Math.max(dp[left][right], 
                nums[left] * nums[i] * nums[right] + dp[left][i] + dp[i][right]);
/*因为dp[i][j]是说不包含i,j在内，也就是i,j位置的气球不参与的，只是当边界，所以左半部份最后肯定就剩下nums[left]，右半部份就剩下nums[right]，所以他们和nums[i]组成了最后的一个乘积。同时dp[left][i] 和 dp[i][right]分别是左右各部分的子问题的最大值，特别的，再次，dp[left][i]中是不包含把left,i位置上的气球弄破的，他们只作为边界，dp[i][right]也一样。*/
        }

    return dp[0][n - 1]; //同样的，这个其中也不包括把0和n-1位置上的气球戳破
}


//Java D&C with Memoization

public int maxCoins(int[] iNums) {
    int[] nums = new int[iNums.length + 2];
    int n = 1;
    for (int x : iNums) if (x > 0) nums[n++] = x;
    nums[0] = nums[n++] = 1;


    int[][] memo = new int[n][n];
    return burst(memo, nums, 0, n - 1);
}

public int burst(int[][] memo, int[] nums, int left, int right) {
    if (left + 1 == right) return 0;
    if (memo[left][right] > 0) return memo[left][right];
    int ans = 0;
    for (int i = left + 1; i < right; ++i)
        ans = Math.max(ans, nums[left] * nums[i] * nums[right] 
        + burst(memo, nums, left, i) + burst(memo, nums, i, right));
    memo[left][right] = ans;
    return ans;
}
