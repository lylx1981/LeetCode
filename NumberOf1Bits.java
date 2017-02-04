class Solution {
    /*
     * 比较简单的一道位操作，注意优化的方法有一个技巧要学会就是 "n &= n - 1"  这个操作可以去掉二进制表示里面最右边的一个1,而且这个算法比较优化，当所有1都删除后，n值变成0了，自己退出While循环，不用把32位都判断一遍，这是最个优化的方法的核心
     
     * Pure bit manipulation
     * "n &= n - 1" is used to delete the right "1" of n
     * Stop when all 1s are deleted and n is zero
     */
    public int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            res++;
            n &= n - 1; //注意优化的方法有一个技巧要学会就是 "n &= n - 1"  这个操作可以去掉二进制表示里面最右边的一个1. 注意这里有两种情况：
            //1. n当前末位有1的时候，比较简单，比如n=3对应11， n-1就是2对应10， 所以操作结果是1，也就是11最右边的那个1要去除了。另一种情况也要注意是这个操作比较特殊的。比如n=4, 也就是100，n-1就是3对应11，这样的话n &= n - 1这个操作就把100中最高位的那个1去除了，因为它目前是n里面最右边的一个1.这个要特别注意 ！ ！！
        }
        return res;
    }
    
    /** 方法二：也可以沿用其他位操作里面提供的方法，每次去比较第i位上是不是1，
     * Most straight forward solution
     * Iterate 32 times to check each digit in n
     */
     
     public int hammingWeight(int n) {
        int res = 0;
        for (int i = 0; i < 32; i++) { // no need to iterate 32 times ？？ 什么意思，没有想的太明白
            if ((n & 0x1) == 1) res++; //就是十六进制的1啊。0x是十六进制标识, 不知道为什么源程序厘米那要用0x1,试了一下，直接写1就可以。
            n >>>= 1;
            
        }
        return res;
    }
    
