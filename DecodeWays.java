
/**
 * 思路：DP，下面代码是从前往后判断，其实也可以从后往前判断，思想都是一样的。
 * 当判断第i位的时候，nums[i]的解取决于nums[i - 1]和nums[i - 2]的解。特别的有几种情况要需要处理：

 * 
 * 
 */

public class Solution {
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int[] nums = new int[s.length() + 1];
        nums[s.length()] = 1; //空字符的时候，只有一种解法
        nums[s.length()-1] = s.charAt(0) != '0' ? 1 : 0; // 只有一种字符的时候，如果字符是0，则只有0种解法，如果字符不是0，则肯定能解析成一个字母，那么就是1种解法。

        for (int i = 2; i <= s.length(); i++) {
            if (s.charAt(i - 1) != '0') {
                nums[i] = nums[i - 1]; //当第i个字符，其索引在s里就是i-1。当第i个字符自己不是0的时候，这个一位数字符完全可以被解析成一个字母，这种情况下nums[i]至少是和nums[i - 1]一样，注意nums[i]存放的是共计有i个字符的时候一共有多少种解法，而第i个字符在s里面的索引是i-1,这里之前糊涂了很久
            }
            //再判断当前第i个字符以及第i-1个字符组成的两位数如果大于等于10小于等于26的话，那么砍掉这个两位数，剩下个数为i-2个字符的字符串的解法个数在nums数组里对应的是nums[i - 2]，所以这个值也要继续加到nums[i]上。
            int twoDigits = (s.charAt(i - 2) - '0') * 10 + s.charAt(i - 1) - '0';
            if (twoDigits >= 10 && twoDigits <= 26) {
                nums[i] += nums[i - 2];
            }
        }
        
        
        return nums[s.length()];
    }
}
