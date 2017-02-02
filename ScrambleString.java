

    /**
     * 
     * 解法: 递归最简单。给定两个s1,s2,s2是不是s1的Scramble取决于把它们各自按同样的方法切一刀后，其s2的子字符串是不是s1的子字符串。其中注意要考虑全两种情况，s2的前/后半部分子字符串分别和S1的前/后半部分子字符串组合比较。因为"fedcab" 也是“abcdef"的Scramble（将串切成abc, def，然后互换他们，就可以把def挪到前面了，自己刚开始没考虑到这一点），注意理解题中Scramble String的定义。 

        另外，注意判断S1，s2的时候，先看看他们是不是anagram，不是的话，直接接下来的都不用判断了，直接返回即可。自己在想的过程中，还在琢磨如何选取切一刀的位置，但是下面程序里貌似很简单，就从i=0开始每个都试试看就行了，因为每次都会判断是不是anagram，所以也不会很慢。
     */
     
    /**
     * separate s1 into two parts, namely --s11--, --------s12-------- separate s2 into two parts, namely --s21--,
     * --------s22--------, and test the corresponding part (s11 and s21 && s12 and s22) with isScramble. separate s2
     * into two parts, namely --------s23--------, --s24--, and test the corresponding part (s11 and s24 && s12 and s23)
     * with isScramble.
     *
     * Note that before testing each sub-part with isScramble, anagram is used first to test if the corresponding parts
     * are anagrams. If not, skip directly.
     */

    public boolean isScramble2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        if (s1.equals(s2)) {
            return true;
        }
        /*check anagram*/
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        if (!Arrays.equals(c1, c2)) {
            return false; // not anagram, can't be scramble
        }

        for (int i = 1; i < s1.length(); i++) {
            if (isScramble(s1.substring(0, i), s2.substring(0, i)) && isScramble(s1.substring(i), s2.substring(i))) {
                return true;
            }
            if (isScramble(s1.substring(0, i), s2.substring(s2.length() - i)) && isScramble(s1.substring(i),
                                                                                            s2.substring(0, s2.length()
                                                                                                            - i))) {
                return true;
            }
        }
        return false; // didn't pass the test
    }

