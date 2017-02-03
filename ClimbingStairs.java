/* 简单的动态规划，DP【n】的值取决于DP【n-1】+DP【n-2】，也就是从N-1步过来（虽说再多跳一步，但Solution还是原来的个数），以及从N-2步过来（虽说再多跳一步，但Solution还是原来的个数）。注意自己考虑的时候思路还是不清晰，在N-2步的时候，还在想从N-2步有几种方法，造成写出了DP【n-2】+2这样的公式（2说明还有2种方法，一种直接跳到N，一种先跳到N-1再跳到N） ，这样变换角度思考问题是不对的。因为如果从N-2跳到N-1后，它已经属于了一种D【N-1】的解了。
*/

// bottom-up approach
    public static int climbStairs(int n) {
        if (n < 0) return -1;
        if (n == 0 || n == 1) return 1;
        int[] cache = new int[n + 1];
        cache[0] = 1;
        cache[1] = 1;
        for (int i = 2; i < cache.length; i++) {
            cache[i] = cache[i - 1] + cache[i - 2]; // only need the last 2
        }
        return cache[n];
    }
    
    //另外还有其他几种方法，都差不多类似，供参考
    
       /**
     * Bottom-up approach
     * Remember the previous two solutions
     */
    public int climbStairs(int n) {
        if (n <= 1) return n;
        int last = 1, lastlast = 1;
        int now = 0;
        for (int i = 2; i <= n; i++) {
            now = last + lastlast;
            lastlast = last;
            last = now;
        }
        return now;
    }

    /**
     * Top-down approach with memory function
     */
    public static int climbStairs(int n) {
        int[] cache = new int[n + 1];
        return helper(n, cache);
    }
    
    public static int helper(int n, int[] cache) {
        if (n < 0) return -1;
        if (n == 0 || n == 1) return 1;
        cache[0] = 1; 
        cache[1] = 1; 
        if (cache[n] == 0)
            cache[n] = helper(n - 1, cache) + helper(n - 2, cache);            
        return cache[n];
    }
