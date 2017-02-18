/*
思路：比较明显的动态规划，
comb[i]表示可以达到i的组合数目。comb[target]的数目其实等于comb[target] = sum(comb[target - nums[i]]), where 0 <= i < nums.length, and target >= nums[i]（也就是仅对小于等于target的这些num[i]判断即可！！！）. 也就是target减去一个nums数组里面的每一个比target 小的nums[i]的值所对应的那个新的target所对应的组合数，再求和。

对于原题中的例子，nums = [1, 2, 3]，假设Target=10,其实就是comb[10]=comb[10-1] + comb[10-2] + comb[10-3]，注意这里不要再琢磨3可以由3个1组合成的情况，因为现在我们只考虑在原来解的情况下只再加1个元素的情况，所以3个1的情况，其实已经被comb[10-1]包含了。

另外，这道题很好的反应了DP和递归的关系，DP可以存储很多中间结果。看看这道题怎么从递归变为DP的。非常好的感受！！！

Think about the recurrence relation first. How does the # of combinations of the target related to the # of combinations of numbers that are smaller than the target?

So we know that target is the sum of numbers in the array. Imagine we only need one more number to reach target, this number can be any one in the array, right? So the # of combinations of target, comb[target] = sum(comb[target - nums[i]]), where 0 <= i < nums.length, and target >= nums[i].

In the example given, we can actually find the # of combinations of 4 with the # of combinations of 3(4 - 1), 2(4- 2) and 1(4 - 3). As a result, comb[4] = comb[4-1] + comb[4-2] + comb[4-3] = comb[3] + comb[2] + comb[1].

Then think about the base case. Since if the target is 0, there is only one way to get zero, which is using 0, we can set comb[0] = 1.

EDIT: The problem says that target is a positive integer that makes me feel it's unclear to put it in the above way. Since target == 0 only happens when in the previous call, target = nums[i], we know that this is the only combination in this case, so we return 1.

Now we can come up with at least a recursive solution.

*/

public int combinationSum4(int[] nums, int target) {
    if (target == 0) {
        return 1;
    }
    int res = 0;
    for (int i = 0; i < nums.length; i++) {
        if (target >= nums[i]) { //仅对小于等于target的这些num[i]判断即可！！！
            res += combinationSum4(nums, target - nums[i]); //这里只是递归没有任何DP
        }
    }
    return res;
}

/*
在原来的基础上改变成DP，也就是存储中间结果。
Now for a DP solution, we just need to figure out a way to store the intermediate results, to avoid the same combination sum being calculated many times. We can use an array to save those results, and check if there is already a result before calculation. We can fill the array with -1 to indicate that the result hasn't been calculated yet. 0 is not a good choice because it means there is no combination sum for the target.

*/

private int[] dp;

public int combinationSum4(int[] nums, int target) {
    dp = new int[target + 1]; //存储中间结果！！！ DP！！
    Arrays.fill(dp, -1);
    dp[0] = 1;
    return helper(nums, target);
}

private int helper(int[] nums, int target) {
    if (dp[target] != -1) { //如果已经之前计算过，那么直接使用！
        return dp[target];
    }
    int res = 0;
    for (int i = 0; i < nums.length; i++) {
        if (target >= nums[i]) { 
            res += helper(nums, target - nums[i]);
        }
    }
    dp[target] = res;
    return res;
}

//EDIT: The above solution is top-down. How about a bottom-up one?

public int combinationSum4(int[] nums, int target) {
    int[] comb = new int[target + 1];
    comb[0] = 1;
    for (int i = 1; i < comb.length; i++) {
        for (int j = 0; j < nums.length; j++) {
            if (i - nums[j] >= 0) {
                comb[i] += comb[i - nums[j]];
            }
        }
    }
    return comb[target];
}

