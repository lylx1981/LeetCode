

/*

思路：这道题感觉比较迷糊，很多状态分不清，主要是因为加了各种不同的限制，比如必须有cooldown day, 这样的话相对于前面的题，就不允许同一天买卖了。这就导致了另外一个限制，就是每次卖必须是要有已经买的股票，而不能没有买过股票而直接去卖。另外一个就是不允许同一天卖之后，对于每一天来说，会有各种不同可能，比如当前我已经买了股票但是我不卖，第二种是我卖。另外，我如果在休息日，那么我可以继续休息，也可以买股票。另外，如果我昨天刚卖完，那么今天我一直是到Rest day,我一定不能买。对最后一天也是如此，所以如果要求最大可能的Profit的话，最后一天也有有多种可能，比如最后一天卖，或者最后一天Rest。所以这道题最清楚的就是状态机的方法。


s0[i] is the state that you had the cooldown on day i;
s1[i] is the state that record the price of the last stock you bought;
s2[i] is the state that you sell the stock on day i;

so when you want the max profit on day i, just compare s0 and s2.

There are three states, according to the action that you can take.

Hence, from there, you can now the profit at a state at time i as:

s0[i] = max(s0[i - 1], s2[i - 1]); // Stay at s0, or rest from s2
s1[i] = max(s1[i - 1], s0[i - 1] - prices[i]); // Stay at s1, or buy from s0
s2[i] = s1[i - 1] + prices[i]; // Only one way from s1
Then, you just find the maximum of s0[n] and s2[n], since they will be the maximum profit we need (No one can buy stock and left with more profit that sell right :) )

Define base case:

s0[0] = 0; // At the start, you don't have any stock if you just rest
s1[0] = -prices[0]; // After buy, you should have -prices[0] profit. 如果你在第0天买，当前就投入了本，因为现在1分钱还没有挣，所以投入的钱都是暂时算作赔的。只要是买的时候都是“-”相当于投入（也就是对应于利润减少了），当在卖的时候（也就是s2的状态时）会加上当天卖的价格（相当于回收回来多少利润），两者因为都算进去了，所以其之差也就是真正的利润了。
s2[0] = INT_MIN; // Lower base case

最后只需要需要在s0,s2之间取最大就行，因为不可能s1会给出最大值，因为S1报名最后还有股票放在手里没有卖，这绝不可能会出现最大值，因为其在买的时候已经会减去一部分利润了。

*/

class Solution {
public:
	int maxProfit(vector<int>& prices){
		if (prices.size() <= 1) return 0;
		vector<int> s0(prices.size(), 0);
		vector<int> s1(prices.size(), 0);
		vector<int> s2(prices.size(), 0);
		s1[0] = -prices[0];
		s0[0] = 0;
		s2[0] = INT_MIN;
		for (int i = 1; i < prices.size(); i++) {
			s0[i] = max(s0[i - 1], s2[i - 1]);
			s1[i] = max(s1[i - 1], s0[i - 1] - prices[i]);
			s2[i] = s1[i - 1] + prices[i];
		}
		return max(s0[prices.size() - 1], s2[prices.size() - 1]);
	}
}


//进一步优化到O(1)的代码，推荐！！！！


    
public int maxProfit(int[] prices) {
    if (prices == null || prices.length < 2) return 0;
    int buy = -prices[0], sell = Integer.MIN_VALUE, rest = 0;
    for (int i = 1; i < prices.length; i++) {
        int tmp = buy;
        buy = Math.max(buy, rest - prices[i]);
        rest = Math.max(rest, sell);
        sell = tmp + prices[i];
    }
    return Math.max(buy, Math.max(sell, rest));
}
