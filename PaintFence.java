/**
 * 思路：DP, 这道题非常有意思，是最多可以有两个连续的Post是一样的颜色，而之前有一题是不能有两个连续是一样的颜色，这就造成了题目要求的不能，第一次做题的时候没有看清楚这个区别，审题要仔细。
 * 
 * 那么对于DP[i], 也就是喷到第i个的时候，到底有多少中方法，那么它取决于i-1, i-2的喷法，i-1和i-2喷的不一样的颜色的话，i可以和i-1喷的一样也可以不一样，但是当i-1和i-2喷的一样的话，i只能和i-1喷不一样的颜色。
 * 
 * 所以这道题是另外一种新的DP形式，同时记录了两种DP变量。可以看看下面的这个分析，讲的非常清楚。
 * 
 * 
 
 We divided it into two cases.

the last two posts have the same color, the number of ways to paint in this case is sameColorCounts.

the last two posts have different colors, and the number of ways in this case is diffColorCounts.

//最后的解就是这两种不同方案的所有解的个数的和。

The reason why we have these two cases is that we can easily compute both of them, and that is all I do. When adding a new post, we can use the same color as the last one (if allowed) or different color. If we use different color, there're k-1 options, and the outcomes shoule belong to the diffColorCounts category. If we use same color, there's only one option, and we can only do this when the last two have different colors (which is the diffColorCounts). There we have our induction step.

Here is an example, let's say we have 3 posts and 3 colors. The first two posts we have 9 ways to do them, (1,1), (1,2), (1,3), (2,1), (2,2), (2,3), (3,1), (3,2), (3,3). Now we know that

diffColorCounts = 6;
And

sameColorCounts = 3;
Now for the third post, we can compute these two variables like this:

If we use different colors than the last one (the second one), these ways can be added into diffColorCounts, so if the last one is 3, we can use 1 or 2, if it's 1, we can use 2 or 3, etc. Apparently there are (diffColorCounts + sameColorCounts) * (k-1) possible ways. 因为当为当前post选定了一种颜色后，i-1那里有k-1个颜色可选。所以要乘以k-1.

If we use the same color as the last one, we would trigger a violation in these three cases (1,1,1), (2,2,2) and (3,3,3). This is because they already used the same color for the last two posts. So is there a count that rules out these kind of cases? YES, the diffColorCounts. So in cases within diffColorCounts, we can use the same color as the last one without worrying about triggering the violation. And now as we append a same-color post to them, the former diffColorCounts becomes the current sameColorCounts.

Then we can keep going until we reach the n. And finally just sum up these two variables as result.

Hope this would be clearer.

*/

public int numWays(int n, int k) {
    if(n == 0) return 0;
    else if(n == 1) return k;
    int diffColorCounts = k*(k-1); //初始化的时候，先把两个Post的情况先直接计算了。
    int sameColorCounts = k; //初始化的时候，先把两个Post的情况先直接计算了。
    //循环从第3个post开始即可，也就是从i=2开始
    for(int i=2; i<n; i++) {
        int temp = diffColorCounts;
        diffColorCounts = (diffColorCounts + sameColorCounts) * (k-1);
        sameColorCounts = temp;
    }
    return diffColorCounts + sameColorCounts; //最后的解就是这两种不同方案的所有解的个数的和。
}
