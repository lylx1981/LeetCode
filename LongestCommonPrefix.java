/*
思路：这道题主要考察对罗马数字的编排知识，查询了百科，可以看到有如下几个规矩：
    在較大的羅馬數字的右邊記上較小的羅馬數字，表示大數字加小數字。
    在較大的羅馬數字的左邊記上較小的羅馬數字，表示大數字减小數字。
    左减的数字有限制，仅限于I、X、C。比如45不可以写成VL，只能是XLV
    但是，左減時不可跨越一個位值。比如，99不可以用IC（ {\displaystyle 100-1} 100-1）表示，而是用XCIX（ {\displaystyle [100-10]+[10-1]} [100-10]+[10-1]）表示。（等同於阿拉伯數字每位數字分別表示。）
    左減數字必須為一位，比如8寫成VIII，而非IIX。
    右加數字不可連續超過三位，比如14寫成XIV，而非XIIII。（見下方“數碼限制”一項。）
    
    
这里面最重要的是“左減數字必須為一位，比如8寫成VIII，而非IIX。”这一条，所以可以通过这一条来判断何时该数字是加还是减（也就是说这是唯一一种情况需要减的）。

*/

class LongestCommonPrefix {
    
    public static void main(String[] args) {
        
    }
    
    
    
    /** 方法一：从前到后找，每次把当前前缀和下一个字符串比较，前缀只会随着遍历越来越短的。
     * Only need to know the length of prefix
     * Initialize result with first word
     * Traverse from second word to last word
     * Get minimum length of current result and next word
     * Check whether prefix is that long in that length
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null) return null;
        if (strs.length == 0) return "";
        if (strs.length == 1) return strs[0];

        String word = strs[0];
        int prefixLength = word.length();

        for (int i = 1; i < strs.length; ++i) {
            String nextWord = strs[i];
            prefixLength = Math.min(prefixLength, nextWord.length());
            for (int j = 0; j < prefixLength; ++j)
                if (word.charAt(j) != nextWord.charAt(j)) {
                    prefixLength = j;
                    break;
                }
        }

        return word.substring(0, prefixLength);
    }
    
    
    /**方法二：从后往前找，可能能进一步节省点空间，但是还是第一种就容易理解。
     * Find common prefix one by one from the end of the input string array
     * Overwrite the ith string with common prefix result
     * Thus space usage is reduced
     * Return first in group
     */
    public static String longestCommonPrefix(String[] strs) {
        for (int i = strs.length - 2; i >= 0 ; i--) {
            strs[i] = commonPrefix(strs[i + 1], strs[i]);
        }
        return strs[0];
    }
    
    /**
     * Get length of two strings
     * Loop over each char till one length runs out
     * If same char, append it to result
     * If not same, break
     * Return result
     */
    private static String commonPrefix(String a, String b) {
        StringBuilder pref = new StringBuilder();
        int lenA = a.length();
        int lenB = b.length();
        int i = 0;
        while (i < lenA && i < lenB) {
            if (a.charAt(i) == b.charAt(i)) pref.append(a.charAt(i));
            else break;
            i++;
        }    
        return pref.toString();
    }
    
}
