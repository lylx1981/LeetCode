
/** 非常重要的一道题，题目很简单，但是要考察是不是能想到各种情况，要背会下面提示给出的各个情况
 * 
 * Requirements for atoi:
 * 
    The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.
    
    The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.
    
    If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.
    
    If no valid conversion could be performed, a zero value is returned. If the correct value is out of the range of representable values, INT_MAX (2147483647) or INT_MIN (-2147483648) is returned.
 * 
 * 

 */


public class Solution {
   public int myAtoi(String str) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if(str == null) {
            return 0;
        }
        str = str.trim(); //trim一下，开头的空格就没有了。
        if (str.length() == 0) {
            return 0;
        }
            
        int sign = 1; 
        int index = 0;
    
        if (str.charAt(index) == '+') { //可能数字开头会有+号，但正数可能也省略没有
            index++;
        } else if (str.charAt(index) == '-') {
            sign = -1; //记录数字的真正符号位
            index++;
        }
        
        //Index记录了到底从哪里开始考察字符串
        long num = 0; //注意这里用Long类型来保存中间结果，很重要的一点。
        for (; index < str.length(); index++) {
            if (str.charAt(index) < '0' || str.charAt(index) > '9') //遇到第一个不是Digit的位，就退出。这里要记住代码是如何判断一个字符是Ditgit的
                break;
            num = num * 10 + (str.charAt(index) - '0'); //从高位向低位判断的时候，要把当前结果先乘以10， “(str.charAt(index) - '0')” 是另外一个把str.charAt(index) 变为Integer的技巧
            if (num > Integer.MAX_VALUE ) {
                break; //如果越界了，直接退出，记住，我们这里num只可能是正值。sign符号位到最后才加
            }
        }   
        //最后加上符号位后，继续判断是不是越界
        if (num * sign >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (num * sign <= Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int)num * sign; //最后也是很重要的一点，要把刚才计算的结果重新变为Int类型，再返回，这些都是细节。
    }
}
