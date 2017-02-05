
    /** 思路：这样的数肯定是power of two，在这样的数中, 观察power of four的二进制位数的特点。 发现这样的数的1位在二进制表示的奇数位上。所以只需要继续判断看1在不在二进制的奇数位置上就行了。
     * 
     * 
    The basic idea is from power of 2, We can use "n&(n-1) == 0" to determine if n is power of 2. For power of 4, the additional restriction is that in binary form, the only "1" should always located at the odd position. For example, 4^0 = 1, 4^1 = 100, 4^2 = 10000. So we can use "num & 0x55555555==num" to check if "1" is located at the odd position.
     * 
     * 0xAAAAAAAA的二进制表示是： 10101010101010101010101010101010， x & 0xAAAAAAAA就是取x的偶数位。
     * 0x55555555是16进制，2进制是01010101010101010101010101010101， x & 0x55555555就是取x的奇数位


     */

    
public class Solution {
    public boolean isPowerOfFour(int num) {
        return (num > 0) && ((num & (num - 1)) == 0) && ((num & 0x55555555) == num); //如果这个数与0x55555555 &操作后还是其本身，则说明1位就在奇数位上！
    }
}
