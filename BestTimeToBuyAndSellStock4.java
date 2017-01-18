     /* 思路：下面的分析非常好，建议好好读！
        这道题是Best Time to Buy and Sell Stock的扩展，现在我们最多可以进行两次交易。我们仍然使用动态规划来完成，事实上可以解决非常通用的情况，也就是最多进行k次交易的情况。
这里我们先解释最多可以进行k次交易的算法，然后最多进行两次我们只需要把k取成2即可。我们还是使用“局部最优和全局最优解法”。我们维护两种量，一个是当前到达第i天可以最多进行j次交易，最好的利润是多少（global[i][j]），另一个是当前到达第i天，最多可进行j次交易，并且最后一次交易在当天卖出的最好的利润是多少（local[i][j]）。下面我们来看递推式，全局的比较简单，
global[i][j]=max(local[i][j],global[i-1][j])，
也就是取当前局部最好的，和过往全局最好的中大的那个（因为最后一次交易如果包含当前天一定在局部最好的里面，否则一定在过往全局最优的里面）。
全局（到达第i天进行j次交易的最大收益） = max{局部（在第i天交易后，恰好满足j次交易），全局（到达第i-1天时已经满足j次交易）}

对于局部变量的维护，递推式是
local[i][j]=max(global[i-1][j-1]+max(diff,0),local[i-1][j]+diff)，
也就是看两个量，第一个是全局到i-1天进行j-1次交易，然后加上今天的交易，如果今天是赚钱的话（也就是前面只要j-1次交易，最后一次交易取当前天），第二个量则是取local第i-1天j次交易，然后加上今天的差值（这里因为local[i-1][j]比如包含第i-1天卖出的交易，所以现在变成第i天卖出，并不会增加交易次数，而且这里无论diff是不是大于0都一定要加上，因为否则就不满足local[i][j]必须在最后一天卖出的条件了）。
局部（在第i天交易后，总共交易了j次） =  max{情况2，情况1}
情况1：在第i-1天时，恰好已经交易了j次（local[i-1][j]），那么如果i-1天到i天再交易一次：即在第i-1天买入，第i天卖出（diff），则这不并不会增加交易次数！【例如我在第一天买入，第二天卖出；然后第二天又买入，第三天再卖出的行为  和   第一天买入，第三天卖出  的效果是一样的，其实只进行了一次交易！因为有连续性】
情况2：第i-1天后，共交易了j-1次（global[i-1][j-1]），因此为了满足“第i天过后共进行了j次交易，且第i天必须进行交易”的条件：我们可以选择1：在第i-1天买入，然后再第i天卖出（diff），或者选择在第i天买入，然后同样在第i天卖出（收益为0）。


上面的算法中对于天数需要一次扫描，而每次要对交易次数进行递推式求解，所以时间复杂度是O(n*k)，如果是最多进行两次交易，那么复杂度还是O(n)。空间上只需要维护当天数据皆可以，所以是O(k)，当k=2，则是O(1)。

     ---------- 下面的代码中mustsell就是上面说的local,globalbest就是上面说的global---------------
     */

   class Solution {
    /**
     * @param k: An integer
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int k, int[] prices) {
        // write your code here
        if (k == 0) {
            return 0;
        }
        if (k >= prices.length / 2) {
            /*当k >= prices.length / 2的时候，不管什么情况都足够用了，因为最差情况是股票一天升，一天降，也就是锯齿形的行情，这种情况，最多也就是需要prices.length / 2次交易。否则，如果股票可以连续升几天的话，中间更没有必要卖了。
           所以在这种情况下，问题就变成了Best to Buy and Sell II 的那个问题，所以只要把所有相邻天的大于0的profit都加起来就行了。
           */
            int profit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    profit += prices[i] - prices[i - 1];
                }
            }
            return profit;
        }
        int n = prices.length;
        int[][] mustsell = new int[n + 1][n + 1];   // mustSell[i][j] 表示前i天，至多进行j次交易，第i天必须sell的最大获益
        int[][] globalbest = new int[n + 1][n + 1];  // globalbest[i][j] 表示前i天，至多进行j次交易，第i天可以不sell的最大获益
        
        mustsell[0][0] = globalbest[0][0] = 0;
        for (int i = 1; i <= k; i++) {
            mustsell[0][i] = globalbest[0][i] = 0;
        }
        
        for (int i = 1; i < n; i++) {
            int gainorlose = prices[i] - prices[i - 1];
            mustsell[i][0] = 0;
            for (int j = 1; j <= k; j++) {
                //注意九章下面的代码和上面的共识不太一样，但是也能通过Leetcode OJ，感觉上面的公式（local[i][j]=max(global[i-1][j-1]+max(diff,0),local[i-1][j]+diff)，）才是对的，而且progamCreek 上面也是这样写的，也就是说，需要有max(diff,0)这一部分
                //另外，把下面九章的算法改为有max(diff,0)后，仍然可以通过测试，所以现在直接对九章代码进行了一下小的改动
                mustsell[i][j] = Math.max(globalbest[(i - 1)][j - 1] + Math. max(gainorlose,0),
                                            mustsell[(i - 1)][j] + gainorlose);
                globalbest[i][j] = Math.max(globalbest[(i - 1)][j], mustsell[i ][j]);
            }
        }
        return globalbest[(n - 1)][k];
    }
}
}
