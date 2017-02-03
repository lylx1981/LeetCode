class Solution {
    /*
     * Instead of 1 -> A, 26 -> Z, we can assume that 0 -> A, 25 -> Z, and then here comes the base 26 representation, it's similar when you convert a number from base 10 to base 2 （但是这里不用包含进位的处理！！！！，而且往上增长一位数字的 话，最高位还是A，注意A其实对应的其实是0（而不是1），这是和数字进制计算不一样的地方）
        
        
     */


public class Solution {
    public String convertToTitle(int n) {
        String res = "";
        while(n != 0) {
            char ch = (char)((n - 1) % 26 + 'A'); //注意这里的字符操作，都与'A'进行一下运算，就直接转换成对应字符了。'
            n = (n - 1) / 26;
            res = ch + res;
        }
        return res;
    }
}
