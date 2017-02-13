/*  思路： DP。自己刚开始id思想史DP[n] = min [DP[n-x] + DP [x]]  x从1开始一个一个试。但是发现Leetcode上面只试验n-x其中x是完美平方数的情况就够了（还是不太能想清楚）.感觉有点贪心算法的感觉，每次只需要检验那些只让比当前某个dp[n-x]增长1的情况（也就是只需要检验x是完美平方数），就够了。 

dp[n] indicates that the perfect squares count of the given n, and we have:

dp[0] = 0 
dp[1] = dp[0]+1 = 1
dp[2] = dp[1]+1 = 2
dp[3] = dp[2]+1 = 3
dp[4] = Min{ dp[4-1*1]+1, dp[4-2*2]+1 } 
      = Min{ dp[3]+1, dp[0]+1 } 
      = 1				
dp[5] = Min{ dp[5-1*1]+1, dp[5-2*2]+1 } 
      = Min{ dp[4]+1, dp[1]+1 } 
      = 2
						.
						.
						.
dp[13] = Min{ dp[13-1*1]+1, dp[13-2*2]+1, dp[13-3*3]+1 } 
       = Min{ dp[12]+1, dp[9]+1, dp[4]+1 } 
       = 2
						.
						.
						.
dp[n] = Min{ dp[n - i*i] + 1 },  n - i*i >=0 && i >= 1*/


  /**
     * DP, bottom-up
     */
    public int numSquares2(int n) {
        int[] res = new int[n + 1];
        Arrays.fill(res, Integer.MAX_VALUE);
        res[0] = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                res[i] = Math.min(res[i], res[i - j * j] + 1); //这里面已经包含了如果j*j正好增长到i的时候，也就是i本身就是平方数，那么此时因为res[i]默认值为0，所以结果就是res[0]+1 = 0+1 =1. 
            }
        }
        return res[n];
    }


//这道题就是用我自己最初的方法来解的

public class Solution {
        public int numSquares(int n) {
            if(n == 1) return 1;
            if(Math.pow((int)Math.sqrt(n), 2) == n) return 1;
            int[] num = new int[n + 1];
            num[1] = 1;
            for(int i = 2; i <= n; i++){
                if(Math.pow((int)Math.sqrt(i), 2) == i){
                  num[i] = 1; //如果这个数本身就是一个平方数，那么答案就是1
                  continue;
                } 
                int min = i;
                for(int j = 1; j <= (i + 1) / 2; j++){
                    min = Math.min(num[j] + num[i - j], min) ; //对每一个比i 小的数都进行测试，一直测试到j <= (i + 1)这个位置。然后再与当前min再取min.
                }
                num[i] = min;
            }
            return num[n];
        }
    }

