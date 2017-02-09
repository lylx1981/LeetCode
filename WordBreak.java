/*
思路：动态规划，比较简单。
自己第一次想的是top-down的递归，但是时间超时了，应该用DP. Bottom-up这种。要注意一下。

public class WordBreak {

    private WordBreak w;
    private Set<String> breakable = new HashSet<>();

    /**
     * DP. Bottom-up.
     * Build a boolean array of size n+1 for break results at different lengths.
     * A valid string consists of two parts, divided by a cut point:
     * 1) The string before the point can be divided.
     * 2) The string after is a word in dictionary.
     * Base case:
     * String with 0 length is true, since there is no need to decompose.
     * Implementation:
     * Build the dp result from bottom up
     * For each length i, check from 0 to i whether there is both an already divided previous string
     * And a substring in dictionary.
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        
        
        int len = s.length();
        boolean[] dp = new boolean[len + 1];
        dp[0] = true;
        for (int i = 1; i <= len; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) { // dp[j] is s[0..j-1]. The rest is s[j..i-1].
                    dp[i] = true;
                    break; // Pruning. Found one is enough.
                }
            }
        }
        return dp[len];
    }

    /** 这一种超时了！！！
     * 递归, top-down, TLE.
     * If a String s is breakable, it consists of one word from the wordDict.
     * And the rest of the String is also breakable.
     * When a String is breakable, store it in a set to avoid function calling again. //其实这里breakable已经也是一种优化了，但是还是超时了
     */
    public boolean wordBreakB(String s, List<String> wordDict) {
        if (s == null || wordDict == null) {
            return false;
        }
        if (s.equals("") || wordDict.contains(s)) {
            breakable.add(s);
            return true;
        }

        for (int i = 1; i <= s.length(); i++) {
            String pre = s.substring(0, i);
//            System.out.println("pre: " + pre);
            if (wordDict.contains(pre)) {
                String post = s.substring(i);
//                System.out.println("post: " + post);
                if (breakable.contains(post) || wordBreak(post, wordDict)) { //其实这里是递归了！！
                    breakable.add(post);
                    return true;
                }
            }
        }
        return false;
    }
