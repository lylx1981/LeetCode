/*思路： 这道题非常绕，看了2个小时，还是不能完全想通。题目要求的是能够保证赢的最少的钱数。其实这道题是游戏理论中的minmax算法的应用。

网上看到的话：在1-n个数里面，我们任意猜一个数(设为i)，保证获胜所花的钱应该为 i + max(w(1 ,i-1), w(i+1 ,n))，这里w(x,y))表示猜范围在(x,y)的数保证能赢应花的钱，则我们依次遍历 1-n作为猜的数，求出其中的最小值即为答案，即最小的最大值问题
 
 My understand is that: you strategy is the best, but your luck is the worst. You only guess right when there is no possibilities to guess wrong （也就是说现在我有一个最好的策略，但是不幸的是我每次都猜错，也就是说不是每次我都可以随便猜的，而是我已经有一个最好的策略了，我就按照这个策略来猜，但是不幸的是，我每次都猜错，也就是每次都是Worst Case），题目就是要求的是在这种情况下最小需要多少钱。

Definition of dp[i][j]: minimum number of money to guarantee win for subproblem [i, j].

Target: dp[1][n]

Corner case: dp[i][i] = 0 (because the only element must be correct)

Equation: we can choose k (i<=k<=j) as our guess, and pay price k. After our guess, the problem is divided into two subproblems. Notice we do not need to pay the money for both subproblems. We only need to pay the worst case (because the system will tell us which side we should go，因为我不知道究竟会选哪边，但总会选一边，那么我就Cover最大的Cost就好) to guarantee win. So dp[i][j] = min (i<=k<=j) { k + max(dp[i][k-1], dp[k+1][j]) }，因为对于当前步，我自己可以决定我是不是要选择k,一旦选择了，因为dp[i][k-1], dp[k+1][j])这些我都已经计算过来，所以我可以知道选择哪个k是具有最小Cost的。

还是不太能绕过来弯...

另一个人的讲解：

在 374 Guess Number Higher or Lower 中，我们采用二分法来进行查找，但这题并不是用二分法。

这题要求我们在猜测数字y未知的情况下（1~n任意一个数），要我们在最坏情况下我们支付最少的钱。也就是说要考虑所有y的情况。

我们假定选择了一个错误的数x，（1<=x<=n && x!=y ）那么就知道接下来应该从[1,x-1 ] 或者[x+1,n]中进行查找。 假如我们已经解决了[1,x-1] 和 [x+1,n]计算问题，我们将其表示为solve(L,x-1) 和solve(x+1,n)，那么我们应该选择max(solve(L,x-1),solve(x+1,n)) 这样就是求最坏情况下的损失。总的损失就是 f(x) = x + max(solve(L,x-1),solve(x+1,n))

那么将x从1~n进行遍历，取使得 f(x) 达到最小，来确定最坏情况下最小的损失，也就是我们初始应该选择哪个数。



*/

public int getMoneyAmount(int n) {
        int[][] dp = new int[n + 2][n + 2];
        for (int len = 1; len < n; len++) {
            for (int from = 1, to = from + len; to <= n; from++, to++) {
                dp[from][to] = Integer.MAX_VALUE;
                for (int k = from; k <= to; k++)
                    dp[from][to] = Math.min(dp[from][to], k + Math.max(dp[from][k - 1], dp[k + 1][to]));
            }
        }
        return dp[1][n];
}
