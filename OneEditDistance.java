/**
 * 思路，这道题要好好看看，很有意思，刚开始自己只想到了改变一个字符的情况，而且其实one edit还包括Delete和Add，而特别的Add其实可以用一个Delete来达到等效效果，所以只需要考虑Replace和Delete的情况。
 * 
 * 下面的算法讲的很清楚。
 */

/*
 * There're 3 possibilities to satisfy one edit distance apart: 
 * 
 * 1) Replace 1 char:
 	  s: a B c
 	  t: a D c
 * 2) Delete 1 char from s: 
	  s: a D  b c
	  t: a    b c
 * 3) Delete 1 char from t
	  s: a   b c
	  t: a D b c
 */
public boolean isOneEditDistance(String s, String t) {
    for (int i = 0; i < Math.min(s.length(), t.length()); i++) { 
    	if (s.charAt(i) != t.charAt(i)) {
    		if (s.length() == t.length()) // s has the same length as t, so the only possibility is replacing one char in s and t
    			return s.substring(i + 1).equals(t.substring(i + 1));
			else if (s.length() < t.length()) // t is longer than s, so the only possibility is deleting one char from t
				return s.substring(i).equals(t.substring(i + 1));	        	
			else // s is longer than t, so the only possibility is deleting one char from s
				return t.substring(i).equals(s.substring(i + 1));
    	}
    }       
    //All previous chars are the same, the only possibility is deleting the end char in the longer one of s and t 
    return Math.abs(s.length() - t.length()) == 1;        
}




/** 也可以看下面的这个代码，也不错
     * Edit distance: add, remove, or replace.
     * Three possible situations:
     * 1) s and t are of same length, replace
     * 2) delete 1 char from s
     * 3) delete 1 char from t
     * <p>
     * Implementation:
     * Get the lengths of two strings, m and n.
     * Compare m and n. Always put the shorter string as the first parameter.
     * If the difference of their lengths are larger than 1, return false.
     * Loop through the shorter string to find a different char.
     * If found and they are of same length, the rest of them should be the same.
     * If found and they are of different length, the rest of shorter string, including that char,
     * should be the same as the rest of longer string, excluding that char.
     * If all chars are the same, it can only be the last character in longer string.
     * Return true iff longer string is one character longer.
     */
    public boolean isOneEditDistance(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m > n) {
            return isOneEditDistance(t, s); // This way, n >= m always
        }
        if (n - m > 1) return false;
        for (int i = 0; i < m; i++) {
            if (s.charAt(i) == t.charAt(i)) {
                continue;
            }
            if (m == n) {
                return s.substring(i + 1).equals(t.substring(i + 1));
            }
            if (m < n) {
                return s.substring(i).equals(t.substring(i + 1));
            }
        }
        return m != n; // If all characters are the same, will pass previous checks.
    }
