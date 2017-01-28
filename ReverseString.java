/*
    思路： 非常简单的一道题双指针即可。没什么好说的。
 */

    /**
     * Two-pointer swapping
     */
    public String reverseString(String s) {
        if (s == null || s.length() < 2) return s;
        int i = 0;
        int j = s.length() - 1;
        char[] chars = s.toCharArray();
        while (i < j) {
            char c = chars[i];
            chars[i] = chars[j];
            chars[j] = c;
            i++;
            j--;
        }
        return new String(chars);
    }

    /**
     * Use StringBuilder::reverse
     */
    public String reverseStringB(String s) {
        return new StringBuilder(s).reverse().toString();
    }
