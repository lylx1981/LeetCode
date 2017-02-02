public class MinWindowSubstring {

    /**
     * 
     * 解法

        本题要求的时间复杂度为O(n)，而从题目描述当中可以发现这道题，需要用s[i..j]和t进行匹配检查。根据以往的题目经验，我们可以得出这道题需要运用的算法为双指针。
        
        我们考虑使用head和tail表示s[head..tail]这一段字符串：
        
        若s[head..tail]与t不能匹配时，我们将tail++，说明窗口还不够大，我们继续扩大窗口；
        
        若s[head..tail]与t能够匹配时，我们将head++缩小，看看是不是能把当前的窗口缩小；
        
        这样扫描一次之后就可以得到所有与t的匹配的s子串，从中选择最小的也就是我们的答案。
        
        大体的算法有了，由于这个扫描的过程时间复杂度已经是O(n)，所以我们需要考虑如何在O(1)的时间内判定s[head..tail]与t是否能够匹配。
        
        
        这里我采用的是字母表来进行数量上的检查。现将模板串t中各个字母的个数记录下来，digit[c]表示在模板串t之中需要匹配的字符c数量。同时使用tot来记录t的总字符数。
        
        每当tail++时，我们会得到一个新的字母c，我们将digit[c]--。这里需要注意，在执行digit[c]--之前：
        
        若digit[c] > 0，则说明t是中还有未被匹配的字符c，所以s[tail]可以与t中的一个c进行匹配。因此我们需要执行tot--，来表示我们匹配了一个t中的字符。
        
        若digit[c] ≤ 0，则说明t中并不存在没有匹配的字符c，所以此时不需要执行tot--。
        
        利用以上原则，当我们发现tot = 0时，就可以知道当前的s[head..tail]已经可以将t中所有的字符全部匹配完成。
        
        此时可以考虑更新我们的最小结果，之后需要进行head++的操作。
        
        由于在tail++时，我们进行了digit[c]--的操作，所以此时我们应该做的是digit[c]++。同样的，在执行digit[c]++前我们要分两种情况：
        
        若digit[c] ≥ 0，则说明若我们将s[head]移除之后，t中会存在一个未被匹配的c。所以我们需要同时执行tot++。
        
        若digit[c] < 0，则说明即使我们将s[head]移除之后，并不会对t的匹配造成影响，s[head..tail]中仍然有足够数量的c可以和t进行匹配。所以不需要执行tot++。
        
        因为判定是否匹配只需要检查tot的值，所以其时间复杂度为O(1)。
        
        综上，我们也就通过双指针使得该算法的时间复杂度降至O(n)。


     * Hash Table. Two Pointers.
     * <p>
     * 1. Use two pointers: start and end to represent a window.
     * 2. Move end to find a valid window.
     * 3. When a valid window is found, move start to find a smaller window.
     * <p>
     * Use map to store count of each character in a String.
     * One for T and the other for the window in S.
     * Use two pointers to traverse S, the String in between is the window.
     * The pointer in the front moves when the window doesn't cover all characters in T.
     * The pointer in the back moves when the window covers all characters, to get as smaller window as possible.
     * Stop when the front pointer reaches the end.
     * 下面代码的思想和上面的中文描述是一致的！！
     */
     
    public String minWindow(String s, String t) {
        String res = "";
        int[] tCnt = new int[256]; // Assuming charset size 256.//用来特别记录t中每个具体字符在t中的出现个数
        // Initialize substring character count map.
        for (char c : t.toCharArray()) {
            tCnt[c]++;
        }
        int minLen = Integer.MAX_VALUE;
        int count = t.length(); // Counter for whether the window is valid.用来记录一共t中还有几个字符没有匹配上
        for (int start = 0, end = 0; end < s.length(); end++) {
            /*
             * If current character is in t, the value of it should be > 0.
             * If it is > 0, it is a match, decrement counter by 1.
             * Decrement the character count in map as well.
              tCnt[s.charAt(end)]〉0，说明s中的s.charAt(end)的这个字符是需要用来匹配t中的对应字符的。然后tCnt[s.charAt(end)]--是用来表示因为s.charAt(end)这个字符用来匹配了，所以在t中的这个字符需要匹配的数量要减去1，同时总体t中需要匹配的数量也就是Count也要减去1。 特别注意， 当tCnt[s.charAt(end)]个数降为0后，说明不再需要s.charAt(end)这个字符来匹配t中对应的字符了，因为已经够用了，这时候count不再减1，但我们继续记录当前窗口中这个字符富余的数量，也就是让tCnt[s.charAt(end)]继续自减1。 也就是说，这里每次判断这个if，都会使tCnt[s.charAt(end)]自减1，相当于记录对应字符富余的数量，这个数量会减到是负值。
             */
            if (tCnt[s.charAt(end)]-- > 0) {
                count--; //
            }

            while (count == 0) { // If count is 0, all characters in t are matched. 说明现在窗口完全覆盖了t中的所有字符了
                // Update minimum length and string.
                if (end - start + 1 < minLen) {
                    minLen = end - start + 1;
                    res = s.substring(start, end + 1);
                }
                /* 因为窗口能全部盖住t中的所有字符了，我现在想将Start往前挪以使窗口减小一点，所以我看看Sart++ 位置对应字符在++tCnt[s.charAt(start++)]是不是〉0，不是大于0的话，比如是个负数，说明即使现在Start往前挪了一位，t还是被完全覆盖的（因为负数代表即使当前在S中s.charAt(start++)这个字符不用来匹配，其后面还有相同字符可以用来匹配），所以这里只需要让tCnt[s.charAt(start++)]自增1即可，也就是减小目前的富余量。只要Count==0，While循环会一直进行，Start指针会一直向前挪（注意End一直没有动），同时更新最小窗口值。直到某个字符的富余量用完了，特别的，当其对应tCnt[s.charAt(start++)]的值〉0了，说明t中现在又不是能被全Cover的了，这个时候，Count对应也要++ 了，就需要重新开始继续向后挪动End，开始下一轮的扩大窗口。
                 * Move start.
                 * Remove the character at the start of the window from t's map by increasing it's value.
                 * If it's value becomes > 0, there is some character in t that is removed.
                 */
                if (++tCnt[s.charAt(start++)] > 0) {
                    count++;
                }
            }
        }
        return res;
    }

    /** 模板，好好看看！！ 
     * https://discuss.leetcode.com/topic/30941/here-is-a-10-line-template-that-can-solve-most-substring-problems
     * For most substring problem, we are given a string and need to find a substring of it which satisfy some
     * restrictions.
     * A general way is to use a map assisted with two pointers. The template is given below.
     */
    int findSubstring(String s) {
        int[] map = new int[128];
        int counter; // Check whether the substring is valid
        int begin = 0, end = 0; // Two pointers, one point to tail and one head
        int d = Integer.MAX_VALUE; // The length of substring

        // for () { /* Initialize the hash map here */ }

        while (end < s.length()) { // Traverse s

            // if (map[s[end++]]-- ?) {  /* modify counter here */ }

            // while (/* counter condition */) {

                 /* update d here if finding minimum*/

            //increase begin to make it invalid/valid again

            // if (map[s[begin++]]++ ?) { /*modify counter here*/ }
            //}

            /* update d here if finding maximum*/
        }
        return d;
    }
}
