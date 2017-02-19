


/*
思路： Leetcode上评论最多的方法，但是感觉细节很多，不是太容易看出来。主要就是发现几个规则：
1. 如果是偶数，直接对折
2. 如果是奇数，比较各自+-一位的数，看谁有更少的1就选谁。如果大家拥有1个数一样，则总是选+1（3除外，3是个全局最特殊的例子）

进一步的优化是不用每次都对整个二进制表示都计算1的个数，而只需看倒数第二位是0还是1就行了，这个比较细节，可以看英文描述

个人感觉还是DP的方法更好理解！！！

I really think it should be tagged medium because there are many subtleties and good understanding of binary arithmetic is required.

The first step towards solution is to realize that you're allowed to remove the LSB only if it's zero. And to reach the target as fast as possible, removing digits is the best way to go. Hence, even numbers are better than odd. This is quite obvious.

What is not so obvious is what to do with odd numbers. One may think that you just need to remove as many 1's as possible to increase the evenness of the number. Wrong! Look at this example:

111011 -> 111010 -> 11101 -> 11100 -> 1110 -> 111 -> 1000 -> 100 -> 10 -> 1
And yet, this is not the best way because

111011 -> 111100 -> 11110 -> 1111 -> 10000 -> 1000 -> 100 -> 10 -> 1
See? Both 111011 -> 111010 and 111011 -> 111100 remove the same number of 1's, but the second way is better.

So, we just need to remove as many 1's as possible, doing +1 in case of a tie? Not quite. The infamous test with n=3 fails for that strategy because 11 -> 10 -> 1 is better than 11 -> 100 -> 10 -> 1. Fortunately, that's the only exception (or at least I can't think of any other, and there are none in the tests).

So the logic is:

If n is even, halve it.
If n=3 or n-1 has less 1's than n+1, decrement n.
Otherwise, increment n.
Here is an example of such a solution in Java:
*/

public int integerReplacement(int n) {
    int c = 0;
    while (n != 1) {
        if ((n & 1) == 0) {
            n >>>= 1;
        } else if (n == 3 || Integer.bitCount(n + 1) > Integer.bitCount(n - 1)) {
            --n;
        } else {
            ++n;
        }
        ++c;
    }
    return c;
}
/*
Of course, doing bitCount on every iteration is not the best way. It is enough to examine the last two digits to figure out whether incrementing or decrementing will give more 1's. Indeed, if a number ends with 01, then certainly decrementing is the way to go（因为如果是01，那么减1的话，01就变成00了，那么应该选择-1的那个数，因为+1的话01就变成10， 1的个数并没有减少）. Otherwise, if it ends with 11, then certainly incrementing is at least as good as decrementing (*011 -> *010 / *100) or even better (if there are three or more 1's). This leads to the following solution（也就是11的话，选+1的那个数肯定至少不比选-1的那个数差）:
*/

public int integerReplacement(int n) {
    int c = 0;
    while (n != 1) {
        if ((n & 1) == 0) {
            n >>>= 1;
        } else if (n == 3 || ((n >>> 1) & 1) == 0) {
            --n;
        } else {
            ++n;
        }
        ++c;
    }
    return c;
}




/* 个人觉得这道题的DP题解很重要，不是针对这道题，而且对DP问题的理解！！！！！
思路：DP方法，比较直观，自己想到的是这个方法，但是貌似不是Leetcode上最好的办法，而且可能会有TLE的问题。但是这里要体会的，重要的不是这道题用DP解，而且对DP问题的一个理解：

DP的主要思想在于是不是能从i前面的步骤推倒i步。很多题都有相应数据结构已经保存了中间结果，也就是保存了i之前的结果，所以我们没有太意识到这个问题。但有些题即使写出了DP推演模式，也可能直接就是一直依赖递归在做很多重复计算，这样的情况下，DP要继续使用Memorization的方法把中间结果都保存下来。看一下397题对这个问题的理解。


下面是具体这道题DP的解法描述：

如果i是偶数， DP[i] = 1+  DP[i/2]
如果i是奇数， DP[i] = 1+min(dp[i - 1], dp[i+1]), 因为dp[i+1]还没有算出来，但是因为它是偶数，所以dp[i+1] = 1 + dp[（i+1）/ 2])= 1+dp[i / 2 + 1])，这是因为（i+1）/ 2由于是Int的值操作，（i+1）/ 2 最后也就等于dp[i / 2 + 1]， 同样dp[i-1]现在也是偶数了，它也可以写成 dp[i-1] = 1 + dp[（i-1）/ 2])= 1+dp[i / 2])

所以上面的式子可以写成  DP[i] = 1+min(dp[i - 1], dp[i+1])= 2+min(dp[i/2], dp[i/2+1]),，也就是把min里面每个人的1都提到外面来，所以外面的值变为2.

*/

int integerReplacement(int n) {
        int dp[n + 1]; memset(dp, 0, sizeof(dp));
        for (int i = 2; i <= n; i++) {
            dp[i] = 1 + (i & 1 == 0 ? dp[i / 2] : min(dp[i - 1], 1 + dp[i / 2 + 1]));
        }
        return dp[n];
}

//上面的DP会导致重复计算很多遍，所以这里再继续用memorizaton的方法把中间结果都记录下来。

class Solution {
private:
    unordered_map<int, int> visited; //保存中间结果，加快速度！！！

public:
    int integerReplacement(int n) {        
        if (n == 1) { return 0; }
        if (visited.count(n) == 0) {
            if (n & 1 == 1) {
                visited[n] = 2 + min(integerReplacement(n / 2), integerReplacement(n / 2 + 1));
            } else {
                visited[n] = 1 + integerReplacement(n / 2);
            }
        }
        return visited[n];
    }
};
