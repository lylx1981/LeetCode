

    /**
     * 
     * 解法: DP, 自己基本想到了，但还是没写出来DP表达式。DP[i，j]主要只需总结出关键的Case就行。比如这里就是假设dp[i - 1][j]或者dp[i][j - 1]已经是True的情况下，如何使得DP[i，j]为True的计算公式。但是注意这里要把所有能让DP[i，j]为True的Case都写出来。另外还想明白的事，如果DP[i-1，j]，或者DP[i，j-1]是False的话，DP[i，j]也不可能为True。所以上面已经总结出了所有能使DP[i，j]为True的Case。
     * 
     * 
     * DP table represents if s3 is interleaving at (i+j)th position when s1 is at ith position, and s2 is at jth position. 0th position means empty string.

        So if both s1 and s2 is currently empty, s3 is empty too, and it is considered interleaving. If only s1 is empty, then if previous s2 position is interleaving and current s2 position char is equal to s3 current position char, it is considered interleaving. similar idea applies to when s2 is empty. when both s1 and s2 is not empty, then if we arrive i, j from i-1, j, then if i-1,j is already interleaving and i and current s3 position equal, it s interleaving. If we arrive i,j from i, j-1, then if i, j-1 is already interleaving and j and current s3 position equal. it is interleaving.
     */

    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null) {
            return false;
        }
        int a = s1.length();
        int b = s2.length();
        if (s3.length() != a + b) {
            return false;
        }
        boolean[][] dp = new boolean[a + 1][b + 1];
        for (int i = 0; i <= a; i++) {
            for (int j = 0; j <= b; j++) {
                if (i == 0 && j == 0) {
                    dp[0][0] = true;
                } else if (i == 0) {
                    dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
                } else if (j == 0) {
                    dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
                } else {
                    dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                               || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
                }
            }
        }
        return dp[a][b];
    }
    
    
    /** 更优化的方法，只适用一维的dp[]
     * DP, space optimized, O(m)
     * space could optimize since optimal[i+1][] only depends on optimal[i][],
     */
    public boolean isInterleaveOptimal(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null) {
            return false;
        }
        int a = s1.length();
        int b = s2.length();
        if (s3.length() != a + b) {
            return false;
        }
        boolean[] dp = new boolean[b + 1];

        /*i == 0 && j == 0*/
        dp[0] = true;
        // s1 empty, s2 matches s3
        for (int j = 0; j < b; j++) {
            if (dp[j] && s2.charAt(j) == s3.charAt(j)) {
                dp[j + 1] = true;
            }
        }
        for (int i = 0; i < a; i++) { // from
            /*nothing from s2*/
            if (dp[0] && s1.charAt(i) == s3.charAt(i)) {
                dp[0] = true;
            } else {
                dp[0] = false;
            }
            for (int j = 0; j < b; j++) { // select jth char
                /*from s1 or from s2*/
                if (dp[j + 1] && (s1.charAt(i) == s3.charAt(i + j + 1)) ||
                    (dp[j] && s2.charAt(j) == s3.charAt(i + j
                                                        + 1))) { // dp[j+1] means dp[i][j+1], result of last row, dp[j] means dp[i+1][j], result of this row last col
                    dp[j + 1] = true;
                } else {
                    dp[j + 1] = false;
                }
            }
        }
        return dp[b];
    }
