/* 思路： 比较经典的DP，个人比较倾向于非递归的方法，和自己想的一样。DP[sum]就表示钱的总数为sum的时候的需要硬币的最少个数，那么就考察每个（sum-每个可能的硬币面额）=新的总数，如果这个总数对应非-1的解，就算做一个Candidate, 对这些Candidate的解+1后再与当前min比较，如果比当前min还小，就更新min.

denominations: 面额

*/


/*#Iterative Method:#
For the iterative solution, we think in bottom-up manner. Suppose we have already computed all the minimum counts up to sum, what would be the minimum count for sum+1?

Code in Java:*/

public class Solution {
    public int coinChange(int[] coins, int amount) {
        if(amount<1) return 0;
        int[] dp = new int[amount+1];
        int sum = 0;
        
    	while(++sum<=amount) { //对每个amount下面的每一个可能的值进行判断。
    		int min = -1;
        	for(int coin : coins) {
        		if(sum >= coin && dp[sum-coin]!=-1) { //如果当前的sumb比coin大，证明该coin现在可用，并且dp[sum-coin]是有解的
        			int temp = dp[sum-coin]+1;
        			min = min<0 ? temp : (temp < min ? temp : min);
        		}
        	}
        	dp[sum] = min; //注意，如果当前sum比每个coin都小，那就min=-1（因为上面for循环不会进），然后dp[sum]也就是-1，也就是无解了。
    	}
    	return dp[amount];
    }
}



/*#Recursive Method:#
The idea is very classic dynamic programming: think of the last step we take. Suppose we have already found out the best way to sum up to amount a, then for the last step, we can choose any coin type which gives us a remainder r where r = a-coins[i] for all i's. For every remainder, go through exactly the same process as before until either the remainder is 0 or less than 0 (meaning not a valid solution). With this idea, the only remaining detail is to store the minimum number of coins needed to sum up to r so that we don't need to recompute it over and over again.

Code in Java:*/

public class Solution {
    public int coinChange(int[] coins, int amount) {
        if(amount<1) return 0;
        return helper(coins, amount, new int[amount]);
    }
    
    private int helper(int[] coins, int rem, int[] count) { // rem: remaining coins after the last step; count[rem]: minimum number of coins to sum up to rem
        if(rem<0) return -1; // not valid
        if(rem==0) return 0; // completed
        if(count[rem-1] != 0) return count[rem-1]; // already computed, so reuse
        int min = Integer.MAX_VALUE;
        for(int coin : coins) {
            int res = helper(coins, rem-coin, count);
            if(res>=0 && res < min)
                min = 1+res;
        }
        count[rem-1] = (min==Integer.MAX_VALUE) ? -1 : min;
        return count[rem-1];
    }
}
