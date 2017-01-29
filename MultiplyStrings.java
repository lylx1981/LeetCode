/**
 * 思路：一位一位乘，模拟乘法运算即可。主要注意以下事实：
 * 1. m位数 乘以 n位数，结果最多是m+n位数（所以结果数组最多有m+n位）
 * 2.如何对每两位求乘积，然后在结果数组里如何存放结果，以及如何存放进位。
 * 
 * 画画图，做一个三位数乘以三位数的乘法，就能明白，很巧！
 */

public class Solution {

    /**
     * Math. String.
     * How to do multiplication?
     * Start from right to left, multiply each pair of digits, and add them together.
     * Result num1[i] * num2[j] will be placed at i + j and i + j + 1.
     * Mimic this process.
     * Special cases:
     * 1) If one of the strings is null, return empty.
     * 2) If one of the strings is zero, return zero.
     */
    public String multiply(String num1, String num2) {
        if (num1 == null || num2 == null) {
            return "";
        }
        if ("0".equals(num1) || "0".equals(num2)) { // If one number is 0.
            return "0";
        }
        int m = num1.length(), n = num2.length();
        int[] product = new int[m + n]; // Length is at most m + n.
        // Pick one digit from one number, multiply with each digit in the other number.
        for (int i = m - 1; i >= 0; i--) { // From right to left, from lower significant digit to higher.
            for (int j = n - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j, p2 = i + j + 1; // p1 is a more significant digit than p2. //p2位就是两位相乘后余数应该在的位置，而P1位就是如果有进位，进位应该存放的位置，画画图就能看出来
                //可以通过特殊例子来记忆，比如两个数的最后一位i=m-1, j= n-1, 其乘积的结果一定是放在produce的最后一位(m+n-1) 和倒数第二位(m+n-2)，所以p1位低位就是m+n-1=m-1+n-1+1=i+j+1, p2位高位m+n-2=m-1+n-1=i+j，当i,j是别的值的时候也成立
                
                
                int sum = mul + product[p2];//mul加上当前p2位的值后（可能是上一行计算出来的结果），求和后才是当前的结果
                product[p1] += sum / 10; // Carry. 如果有进位，则p1位加上进位，注意这里是+=，也就是继续在p1位现在的值上继续加
                product[p2] = sum % 10; //而p2位当前就是求余，直接赋值，而不是+=
            }
        }

        StringBuilder ans = new StringBuilder();
        for (int p : product) {
            if (p != 0 || ans.length() != 0) { // Not zero, or not empty.
                ans.append(p);
            }
        }
        return ans.toString();
    }
}
