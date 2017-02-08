

/**
 * 思路： 注意各种特殊情况即可，比如除数为0， 越界等。然后就用加法来模拟除法就行了，使得除数（divisor）不停的往上加若干倍，知道加到刚刚小于被除数（Dividend）即可。一个技巧这里用来类似二分法的技巧，有点像TCP里面的加倍，这样可以让除数刚开始的时候以2的指数倍网上增加。
 */

public class Solution {
    /**
     * @param dividend the dividend
     * @param divisor the divisor
     * @return the result
     */
    public int divide(int dividend, int divisor) {
        if (divisor == 0) {
             return dividend >= 0? Integer.MAX_VALUE : Integer.MIN_VALUE; //处理除数是0的情况
        }
        
        if (dividend == 0) {
            return 0; //被除数是0的情况
        }
        
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        //记录符号位
        boolean isNegative = (dividend < 0 && divisor > 0) || 
                             (dividend > 0 && divisor < 0);
                             
        long a = Math.abs((long)dividend);
        long b = Math.abs((long)divisor);
        int result = 0;
        while(a >= b){
            int shift = 0;
            while(a >= (b << shift)){
                shift++; //Shift每++一次，其实就是让b的值增加2倍，知道增加到大于a为止
            }
            a -= b << (shift - 1); //退出While循环说明b << (shift - 1)是目前刚好最大于a的数，把它从a中剪掉，再继续用a中剩余的量重新开始计算与b的倍数
            result += 1 << (shift - 1); // 1 << (shift - 1)，把1往左移shift - 1位其实就是对应于上面减去的值是b的2的多少倍，记录在最终结果里
	     //从这里开始，继续While循环，Shift又从0开始，继续将a中剩余的量继续判断是b的多少倍
        }
        return isNegative? -result: result;
    }
}
