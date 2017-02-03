/* 比较经典的数字题，记得%10操作用来“得到”末位，/10操作用来“去除”末位
另外注意 用long类型来存储计算值，以免越界
如果一个数尾数是0，把其变成高位时，这些0都不要。

另外，自己在想这道题的时候，想成字符串的相关操作了。。。。

*/

class Solution {
    
    public static void main(String[] args) {
        System.out.println(new ReverseInt().reverse(-123));
    }
    
    /**
     * Last digit is zero, output?
     * Reversed might overflow? 1000000003
     */
    public int reverse(int x) {
        long out = 0; // result might overflow
        while (x != 0) {
            out = out * 10 + x % 10; // append last digit of x
            x = x / 10; // remove last digit
        }
        if (out > Integer.MAX_VALUE || out < Integer.MIN_VALUE) return 0; //如果越界Int了，就返回0
        return (int)out;
    }
}
