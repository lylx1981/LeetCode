/** 也就是说考虑划分问题的维度以“用还是不用”为最重要的角度，而不是先考虑S【j】是不是和T[i]相等的角度来划分问题。
     * DP, 2d array as table, Time O(mn), Space O(mn)
     * We keep a m*n matrix and scanning through string S, while
     * m = T.length() + 1 and n = S.length() + 1
     * and each cell in matrix dp[i][j] means the number of distinct
     * subsequences of T.substr(1...i) in S(1...j)
     * 
     * Initialization, dp[0][0] = 1, 
     * dp[0][j] = 1, means T is empty, and there is always 1 substring
     * and dp[i][0] = 0, means S is empty
     * 
     * dp[i][j] = dp[i][j-1]        (from S[1...j - 1] no S[j]) -- 不管S[j]是什么，我根本不用它 
     *           + (dp[i-1][j-1]    (另外 ，如果 S[j] == T[i] and we are going to use S[j])如果 S[j] == T[i]， 我可以选择 现在要用S[j]。因为我要用S[j]，所以现在只要求j-1步只需要匹配i-1个字符就行了，这样才可以使我 j的字符用上，所以就是再加上dp[i-1][j-1]的情况 。这一步自己在考虑的时候，总是考虑用了j之后，前面应该如何选择，想的太细了，没有以递归的方法来考虑问题，所以没有做出推导公式。
     *              
     */
    public int numDistinct(String s, String t) {
        if (s == null || t == null) return 0;
        int m = t.length();
        int n = s.length();
        if (m > n) return 0;
        
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= n; i++) dp[0][i] = 1;
        
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                dp[i][j] = dp[i][j - 1] + (t.charAt(i - 1) == s.charAt(j - 1) ? dp[i - 1][j - 1] : 0);
        
        return dp[m][n];
    }
    
    /**
     * Space optimized, 1D array, build row by row
     */
    public int numDistinctOptimal(String s, String t) {
        if (s == null || t == null) return 0;
        int m = t.length();
        int n = s.length();
        if (m > n) return 0;
        
        int[] dp = new int[m + 1];
        dp[0] = 1;
        
        for (int i = 1; i <= n; i++)
            for (int j = m; j >= 1; j--)
                // same: path[i] = path[i] + (T[i-1] == S[j-1] ? path[i-1] : 0);
                if (t.charAt(j - 1) == s.charAt(i - 1)) dp[j] += dp[j - 1];
        return dp[m];
    }
}
