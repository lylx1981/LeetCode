public class NimGame {

    /** 数学方法： 发现只要是4的倍数的话，都不可能赢，只要不是4的倍数，都可以赢。这个看出来了，代码就是一行。
   
        Proof:
    
    the base case: when n = 4, as suggested by the hint from the
    problem, no matter which number that that first player, the second
    player would always be able to pick the remaining number.
    
    For 1* 4 < n < 2 * 4, (n = 5, 6, 7), the first player can reduce the
    initial number into 4 accordingly, which will leave the death number
    4 to the second player. i.e. The numbers 5, 6, 7 are winning numbers for any player who got it first.
    
    Now to the beginning of the next cycle, n = 8, no matter which
    number that the first player picks, it would always leave the
    winning numbers (5, 6, 7) to the second player. Therefore, 8 % 4 ==
    0, again is a death number.
    
    Following the second case, for numbers between (2*4 = 8) and
    (3*4=12), which are 9, 10, 11, are winning numbers for the first
    player again, because the first player can always reduce the number
    into the death number 8.
     */
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }
    
    
    /**
     * DP解法，第i步能不能赢，取决于三种方案，也就是取决于拿走一个，2个，还是3个，其每个又对应一个dp[i-1],dp[i-2],dp[i-3]的结果，所以DP很简单，但是过不了Leetcode，超时，所以需要分析问题本质，直接用更简单的方法，也就是直接看是不是4的倍数即可。
     */
     
     
    public boolean canWinNim(int n) {
    if(n <= 0)
        throw new IllegalArgumentException();
    if(n < 4)
        return true;
    boolean[] res = new boolean[n + 1];
    res[0] = true;
    res[1] = true;
    res[2] = true;
    res[3] = true;
    for(int i = 4 ; i <= n ; i++)
        res[i] = !(res[i - 1] && res[i - 2] && res[i - 3]);
    return res[n];
}


}
