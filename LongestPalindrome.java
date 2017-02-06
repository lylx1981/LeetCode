/*
思路： 比较简单的一道题。先统计出每个字符出现的次数，凡是偶数次出现的，都能贡献其所有，凡是奇数次出现的，其偶数部分肯定能贡献，
*/


public class Solution {
    public int longestPalindrome(String s) {
        int[] charStatArray = new int[52];//一共就可能有52个字符。
        int oneTimeOddCount = 0;
        int evenCount = 0;
    
        
    
        // keep the times of appearance of each character in the array
        for (char ch: s.toCharArray()) {
            if (ch >= 97) { //97是从小写a开始，左移，让这些字母存在数组的高26位
                charStatArray[26 + ch - 'a']++;
            }
            else {
                charStatArray[ch - 'A']++;
            }
        }
    
        // the answer is the count of characters that has even number of appereances.
        // for characters that has odd number of appereances,
        // their appereances minus 1 will make their apperances even.
        // And finally we can put an unused character in the middle of the palindrome
        // (if there is any).
        for (int cnt: charStatArray) {
            if (cnt != 0) {
                if (cnt % 2 == 0) {
                    evenCount += cnt; //如果是个偶数次出现的，直接加上即可
                } else {
                   
                        evenCount += cnt - 1;//出现奇数次的，把其偶数部分贡献出现，剩下落单的那个用oneTimeOddCount记录一下
                        oneTimeOddCount++;
                    
                }
            }
        }
    
        return oneTimeOddCount > 0 ? 1 + evenCount : evenCount; //如果最后没有落单的，那么回文就是偶数个，否则如果有落单的，再加1即可，放在最中间。
    }
}
