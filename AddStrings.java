/*
思路： 非常简单的一道题，从最后面一位开始计算，每次计算取出对应位，然后转换为10进制的Integer，然后通过求余就算当前位结果值，通过/10，计算进位！
*/

public class Solution {
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for(int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0 || carry == 1; i--, j--){
            int x = i < 0 ? 0 : num1.charAt(i) - '0'; //这里比较巧，如果当前位已经没有了（比如一个字串比较短），就当作0即可，
            int y = j < 0 ? 0 : num2.charAt(j) - '0';
            sb.append((x + y + carry) % 10);//注意这里已经考虑可能的最终计算结果比原先数还多出1位的情况，见For循环的终止条件i >= 0 || j >= 0 || carry == 1
            carry = (x + y + carry) / 10; 
        }
        return sb.reverse().toString();
    }
}
