public class Solution {
    
    /** 思路： 这道题和191 Number of 1 Bits 基本一样的代码，其实就是看有几个1.  凡是 2的Power的话，整个2进制表示里只会有1个1存在。所以n & (n - 1)的操作后，会将n最右边的一个1清除 ，而因为只有1个1存在，所以得到的结果必定是0，所以只要判断n & (n - 1)操作后结果是0不是0即可。当n=0的时候，直接就是False连上面的判断都不用做，所以在前面的处理之前加上一个n>0的额外条件判断即可。
     * 
     * 2's power only has a single 1 at the highest bit.
     * So n & (n - 1) should be 0.
     * Check also if n is positive.
     */
     
     public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
    
    //自己写的代码（基本和191代码一样），和上面说的思想是一样的，用来计数，看最后计数一旦大于1，就返回False,不如上面的代码简洁。
    public boolean isPowerOfTwo(int n) {
        if (n<1) return false;
        int res = 0;
        while (n != 0) {
            res++;
            n &= n - 1; 
            if (res>1) return false;
        }
        return true;
    }
}
