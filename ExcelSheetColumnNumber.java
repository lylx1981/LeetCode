 
    /** 和另外一个Excel Sheet题是姐妹题，这道题好像更简单一点。就是进制转换。同时这一次直接从前往后走，也就是从高位往地位走。
     * Go through the title
     * Map A ~ Z to 1 ~ 26
     * next result = current res * 26 + number of current letter
     */
    public static int titleToNumber(String s) {
        if (s == null || s.length() == 0) return 0;
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res = res * 26 + (s.charAt(i) - '@'); //注意‘@’就是A字符下面的那个字符，也就是A的ASCAII码是65，‘@’是64.
            // s.charAt(i) - '@'的操作就直接得到了对应的数字值。
        }
        return res;
    }
