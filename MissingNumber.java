public class MissingNumber {

    /**方法一：  
     * Math
     * Considering that the n numbers are distinct, we can get the sum of the array
     * Then substract it from the sum of 0 ~ n
     * 利用数学公式，先计算数列和，然后过一遍数组，依次剪掉当前数组值，最后的余额就是missing number
     */
    public int missingNumber1(int[] nums) {
        int res = nums.length * (nums.length + 1) / 2; // may overflow
        for (int n : nums) res -= n;
        return res;
    }

    /**
     * avoid overflow
     */
    public int missingNumber2(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res += i + 1 - nums[i];
        }
        return res;
    }

    /**方法二：
     * Bit Manipulation 位运算的方法非常巧，注意位运算的定义和性质
     * xor all numbers in the array and from 0 to n, the result is the missing number
     The basic idea is to use XOR operation. We all know that a^b^b =a, which means two xor operations with the same number will eliminate the number and reveal the original number.
In this solution, I apply XOR operation to both the index and value of the array. In a complete array with no missing numbers, the index and value should be perfectly corresponding( nums[index] = index), so in a missing array, what left finally is the missing number.
     */
     
    public int missingNumber3(int[] nums) {
        int xor = 0;
        for (int i = 0; i < nums.length; i++) {
            xor = xor ^ nums[i] ^ (i + 1);
        }
        return xor;
    }
}
