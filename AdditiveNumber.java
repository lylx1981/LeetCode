    /*
思路：和自己想的差不多，要依赖于递归。由于第一个数，第二个数可以不符合任何规律，所以要对每种可能的情况都测试一下，尤其是第二个的位数可能比第一个数还少，这都是满足要求的。
对于分解出的第一个数和第二个数，计算其用掉的位数，那么接下来判断就要从那个位置后面开始检查了，进入递归循环判断。每次递归循环首先计算输入的两个数的和，然后判断1.该和所对应的字符串是不是就是当前考察的位置往后的字符串的前缀，如果是的话，继续判断下一轮是不是Additive Number （本轮的第二个数成为下一轮的第一个数，本轮计算的和成为下一轮的第二个数，并且开始判断的索引就是上一轮的索引再往后挪两个数的和所占据的位数。

 //注意为了防止出现overflow的情况，代码里面使用了JAVA自带的BigInteger这个类
 

    */        
        
import java.math.BigInteger; //注意一定要加这一句

public class AdditiveNumber {

    /**
     * Recursive.
     * Generate the first and second of the sequence, check if the rest of the string match the sum recursively.
     * i is the length of first number, j is the length of thgste second.
     */
    public boolean isAdditiveNumber(String num) {
        int n = num.length();
        // Generate all possibilities
        //i最多试验到n/2，因为如果i是n/2的话，即使第二个数只取1位的话，剩余的长度也只剩下(n-n/2-1)了，这个数肯定比n/2的位数少，所以肯定比第一个数还小，这是不可能的，因为剩下的数用来表示前两个数的和的，只会更大。
        for (int i = 1; i <= n / 2; i++) { // First number cannot be longer than half of the string
            if (num.charAt(0) == '0' && i > 1) { // Cannot start with zero if length is larger than 2
                return false; //如果这个字符不是单个的0的话，其不能以0开头，i>1说明当前这个数已经不是单个数字来，因为这个数是以subString(0,i)取出来的
            }
            //注意为了防止出现overflow的情况，代码里面使用了JAVA自带的BigInteger这个类
            BigInteger num1 = new BigInteger(num.substring(0, i));
            for (int j = 1; Math.max(i, j) <= n - i - j; //最后取出第一个数（长度为i），第二个数(长度为j),剩余待考察的字符串不应该比第一个以及第二个字符串的长度小，因为越往后面和会越来越大，占用的字符长度只会越来越大
                 j++) { // The remaining length should not be shorter than i or j
                if (num.charAt(i) == '0' && j > 1) { // Cannot start with zero if length is larger than 2, skip
                    break;
                }
                BigInteger num2 = new BigInteger(num.substring(i, i + j));
                if (isAdditiveNumber(num1, num2, i + j, num)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Recursion.
     * Recurrence relation:
     * isAdditiveNumber = can find the third number && rest of the string is also additive.
     * Stop when reach the end.
     */
    private boolean isAdditiveNumber(BigInteger num1, BigInteger num2, int start, String num) {
        if (start == num.length()) {
/*            如果这里直接达到了字符串的末尾，那么直接返回True即可。记得最后一个数作为倒数第二个与倒数第一个数的和的判断已经在上一个递归里已经判断过了。
这里只是发现已经到达字符串的末尾，所以当前最后面的那两个数不用再判断了。*/
            return true;
        }
        num2 = num2.add(num1); // Get the sum 当前的和编程了现在的下一轮的第二个数
        num1 = num2.subtract(num1); // Get num2 当前的第二个数变成了下一轮的第一个数
        String sum = num2.toString();
        // Check if the sum exists in the string after start, and the rest is also additive
        //首先判断当前和是不是剩余字符串的前缀，如果是的话，在继续判断新的第一个，第二个数，以及新的考察位置，他们是不是一个Additivge number
        return num.startsWith(sum, start) && isAdditiveNumber(num1, num2, start + sum.length(), num);
    }

    /**
     * Iterative.
     */
    public boolean isAdditiveNumberB(String num) {
        int n = num.length();
        for (int i = 1; i <= n / 2; ++i) {
            for (int j = 1; Math.max(j, i) <= n - i - j; ++j) {
                if (isAdditiveNumberB(i, j, num)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param i   length of first number
     * @param j   length of second number
     * @param num the input string
     * @return true if it's addtive, false if not.
     */
    private boolean isAdditiveNumberB(int i, int j, String num) {
        if (num.charAt(0) == '0' && i > 1) {
            return false;
        }
        if (num.charAt(i) == '0' && j > 1) {
            return false;
        }
        String sum;
        BigInteger x1 = new BigInteger(num.substring(0, i));
        BigInteger x2 = new BigInteger(num.substring(i, i + j));
        for (int start = i + j; start != num.length(); start += sum.length()) {
            // Move x1 and x2 one step forward
            x2 = x2.add(x1); // Sum, which is the number after x2
            x1 = x2.subtract(x1); // x2
            sum = x2.toString();
            if (!num.startsWith(sum, start)) { // Check if the next number exists
                return false;
            }
        }
        return true;
    }

}
