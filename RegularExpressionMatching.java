/**
 * 思路：DP，dp[i][j]表示s的前i个字符，是不是可以用p的前j个字符匹配。循环从s字符的头开始向后遍历，每次根据遇到的字符，依据各种不同的情况对d[i][*]进行计算，可以看下面的不变式推导，很清晰。
 * 
 * 
Here are some conditions to figure out, then the logic can be very straightforward.

1, If p.charAt(j) == s.charAt(i) :  dp[i][j] = dp[i-1][j-1]; //当前s[i]和p[j]是同一个字符的时候，dp[i][j]直接取决于dp[i-1][j-1]是不是True，也就是表示s的前i-1个字符，是不是可以用p的前j-1个字符匹配
2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1]; 如何遇见是“.”,那么同样dp[i][j] = dp[i-1][j-1]，因为“.”可以匹配任何字符
3, If p.charAt(j) == '*': 如果遇见的是“*”，共有如下三种情况 
   here are two sub conditions:
               1   if p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2]  //in this case, a* only counts as empty，因为p的当前位是*，那么看看*之前是什么字符，也就是p.charAt(j-1)，如果这个字符和当前s.charAt(i)不一样，那么这种情况下只有让*匹配0个字符“才有可能”保证s的前i个字符可以用p的前j个字符匹配，同时有可能是指这时候dp[i][j] 取决于dp[i][j-2]，也就是取决于s的前i个字符是不是可以用p的前j-2个字符匹配 
               2   if p.charAt(i-1) == s.charAt(i) or p.charAt(i-1) == '.': //如果*之前是什么字符（比如a）和s.charAt(i)一样，那么有如下情况
                              dp[i][j] = dp[i-1][j]    //in this case, a* counts as multiple a ，如果我决定把*a解析为多个a,那么这就表示这个*a也匹配了s[i]之前的字符，比如s[i-1]（注意这里如何表达*a Count as multiple a， 非常重要，只需要让它也用于匹配前一步的字符即可，因为递归关系，这也就表达了*匹配多个字符的这个Feature，非常非常重要！！！！）.这个情况下实际上 dp[i][j]和 dp[i-1][j]是一样的，因为s的前i个字符，是不是可以用p的前j个字符匹配和s的前i-1个字符是不是可以用p的前j个字符匹配的结果是一样的，因为*现在解析成为多个a。 
                           or dp[i][j] = dp[i][j-1]   // in this case, a* counts as single a，如果*a只解析为一个字符，那么相当于不要当前j位的*号的那个字符串，也就是用p的前j-1个字符匹配来匹配s的前i个字符，其结果和s的前i个字符是不是可以用p的前j个字符匹配是一致的。所以，在这种情况下，也就得出dp[i][j]和dp[i][j-1]是一样的。这一步也比较难想！！！！非常Tricky！！！
                           or dp[i][j] = dp[i][j-2]   // in this case, a* counts as empty这个和上面的解释一样
                           
                注意，上面的三种情况，只要有一个为True，情况2）就为True

 */

Here is the solution

public boolean isMatch(String s, String p) {

    if (s == null || p == null) {
        return false;
    }
    boolean[][] dp = new boolean[s.length()+1][p.length()+1]; //用length+1来确定Size，第0位默认为True即可
    dp[0][0] = true;
    for (int i = 0; i < p.length(); i++) {
        if (p.charAt(i) == '*' && dp[0][i-1]) {
            dp[0][i+1] = true; //如果S的第i是*，且dp[0][i-1]是True，说明之需要把当前的*解析为a single char就行了（和上面的解释一样），所以Anyway，在这种情况下 dp[0][i+1]肯定是True
        }
    }
    for (int i = 0 ; i < s.length(); i++) {
        for (int j = 0; j < p.length(); j++) {
            if (p.charAt(j) == '.') {
                dp[i+1][j+1] = dp[i][j];
            }
            if (p.charAt(j) == s.charAt(i)) {
                dp[i+1][j+1] = dp[i][j];
            }
            if (p.charAt(j) == '*') {
                if (p.charAt(j-1) != s.charAt(i) && p.charAt(j-1) != '.') {
                    dp[i+1][j+1] = dp[i+1][j-1];
                } else {
                    dp[i+1][j+1] = (dp[i+1][j] || dp[i][j+1] || dp[i+1][j-1]);//三者取其一True即为True
                }
            }
        }
    }
    return dp[s.length()][p.length()];
}
