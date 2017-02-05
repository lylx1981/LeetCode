
    /** 思路：非常简单的题，过一遍数组统计一下出现次数，然后看看第一个次数为1的那个，就是答案。
    My solution is pretty straightforward. It takes O(n) and goes through the string twice:
    
    Get the frequency of each character.
    Get the first character that has a frequency of one.
    Actually the code below passes all the cases. However, according to @xietao0221, we could change the size of the frequency array to 256 to store other kinds of characters. Thanks for all the other comments and suggestions. Fight on!
     */




public class Solution {
    public int firstUniqChar(String s) {
        int freq [] = new int[26];
        for(int i = 0; i < s.length(); i ++)
            freq [s.charAt(i) - 'a'] ++;
        for(int i = 0; i < s.length(); i ++)
            if(freq [s.charAt(i) - 'a'] == 1)
                return i;
        return -1;
    }
}
