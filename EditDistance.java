
    class EditDist {
    
    public static void main(String[] args) {
        
    }
    
    /** 非常重要的一题，学习动态规划！！！！
     * 
         * 思路： 比较经典的DP，刚开始想到DP，但是感觉没有推导Word1和Word2的中间词汇，所以觉得DP不能用，其实还是DP不熟，看这道题DP的定义，Define D(i,j): the edit distance between X[1..i] and Y[1..j]，也就是把每个Word1，Word2 分为更小的词，从这个角度来找子问题，瞬间就清晰了。
    
    最重要的推导过程公式是如下这个：
    对于D(i,j)，有三种可能的方案：
    	a.还让i-1和j-1配起来，这个时候，一种可能的解为D(i - 1, j - 1)+1或者加0，如果X(i) = Y(j)说明两者相等，就+0,因为我们不用做任何操作，如果X(i) ！= Y(j) ，说明两者不相等，则需要在这一位做Replacement操作让两者相同
    	b. 我让i-1和j配起来，那么其对应D(i - 1, j)，那么我需要一个删除操作删除第i位，所以另一种可能的解为D(i - 1, j) + 1	
    	c. 我让i和j-1配起来，那么其对应D(i, j - 1)，那么我需要一个删除操作删除第j位，所以另一种可能的解为D(i, j-1) + 1
    
    最后D(i, j)就是取上面三部分的最小值即可。
    
    写成下面代码中的简便格式就是如下这一行代码，分别包含了X(i) = Y(j)和X(i) ！= Y(j)的情况	
    1. D(i, j) = min(D(i - 1, j) + 1, D(i, j - 1) + 1, D(i - 1, j - 1) + 0 or 1), 0 is X(i) = Y(j), 1 if X(i) != Y(j)
    
    另外，代码可以继续优化，因为发现 f[i][j]（其实就是上面的 D(i,j)） only depends on f[i-1][j-1], f[i-1][j] and f[i][j-1], therefore we can reduce the space to O(n) by using only the (i-1)th array and previous updated element(f[i][j-1]).
    
    
    也就是看出来f[i-1][j-1], f[i-1][j] and f[i][j-1]可以只以j为坐标，这样 f[i-1][j-1], f[i-1][j]就变成了f[j-1], f[j]（这两个值相当于上一轮的值，也就是上一个i 对应的值，从下面代码可以看出来，i是最外层的循环）,而f[i][j-1]其实就相当于本轮i轮对应的上一个j值。 

     * DP, O(nm) Time, O(nm) Space
     * Searching for a path (sequence of edits) from the start string to the
     * final string
     * For two strings, X of length n, Y of length m
     * Define D(i,j): the edit distance between X[1..i] and Y[1..j]
     * The edit distance between X and Y is thus D(n,m)
     * 
     * Initialization: D(i,0) = i, D(0,j) = j
     * 1. D(i, j) = min(D(i - 1, j) + 1, D(i, j - 1) + 1, D(i - 1, j - 1) + 0
     * or 1), 0 is X(i) = Y(j), 1 if X(i) != Y(j)
     * D(N, M) is distance 
     * 
     * Note that f[i][j] only depends on f[i-1][j-1], f[i-1][j] and f[i][j-1],
     * therefore we can reduce the space to O(n) by using only the (i-1)th
     * array and previous updated element(f[i][j-1]).
     */
    public static int minDistance(String word1, String word2) {
        if (word1.equals(word2)) return 0;
        int m = word1.length();
        int n = word2.length();
        int[][] d = new int[m + 1][n + 1];
        d[0][0] = 0;
        for (int i = 1; i < m + 1; i++) d[i][0] = i;
        for (int j = 1; j < n + 1; j++) d[0][j] = j;
        
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                d[i][j] = Math.min(Math.min(d[i][j - 1] + 1, d[i - 1][j] + 1), word1.charAt(i - 1) == word2.charAt(j - 1) ? d[i - 1][j - 1] : d[i - 1][j - 1] + 1);
            }
        }
        
        return d[m][n];
    }
    
    /**
     * Optimal DP. Reduce table to a row. 
     */
    public static int minDistanceOptimal(String word1, String word2) {
        if (word1.equals(word2)) return 0;
        int m = word1.length();
        int n = word2.length();
        int[] d = new int[n + 1];
        d[0] = 0;
        for (int j = 1; j < n + 1; j++) d[j] = j;
        
        for (int i = 1; i < m + 1; i++) {
            int prev = d[0];
            d[0] += 1;
            for (int j = 1; j < n + 1; j++) {
                int temp = d[j];
                d[j] = Math.min(Math.min(d[j - 1] + 1, d[j] + 1), word1.charAt(i - 1) == word2.charAt(j - 1) ? prev : prev + 1);
                prev = temp;
            }
        }
        
        return d[n];
    }
}
