    /** 思路：其实是比较容易的一道题。
         * 就是分两步，首先第一步确定一行填几个Word,其取决于每个Word的长度，并且每个Word之间至少有一个空格，同时他们所有加在一起的长度不能大于每行的最大长度。
    确定每一行要填入的Word后，接下来判断到底需要填入多少个空格来让行左右对齐，这些Extra刚开始要尽可能均分在Word之间，但是遇到均分后还有最后一些余额Extra时，让他们尽可能填在一行左边的字符间。
    
    最后对最后一行稍作处理即可，最后一行都填空格直到填满即可。




     * String.
     * First figure out how many words to fit current line.
     * | Init a length as -1, to exclude the last space.
     * | Start from i, add word length + 1 to length as long as w is still within array and length within maxWidth.
     * Then append the words and generate line.
     * Start from the first word.
     * | Append the first word.
     * | Calculate number of spaces and extra spaces.
     * |    space -> 1, at least one space
     * |    extra -> 0,
     * |    If w moved and w != words.length, meaning not the last line
     * |      space = remain length / intervals between words + 1(at least 1 space)
     * |      extra = remain length % intervals between words
     * Append the rest of the words. The first one is appended already.
     * | Append spaces and extra spaces first. Then append word.
     * Deal with padding spaces if it is the last line.
     * | If maxWidth > current line length, it's the last line.
     * |   Pad spaces at the end.
     * Add current line to res.
     */
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new LinkedList<>();
        for (int i = 0, w; i < words.length; i = w) {
            /* Find the number of words to fit this line */
            int len = -1; // Length of current line. Init as -1 to remove last space.
            for (w = i; w < words.length && len + words[w].length() + 1 <= maxWidth; w++) {
                len += words[w].length() + 1; // 1 is an extra space in between words. 这也就是为什么上面有len = -1，其实就是要把最后一个Word的后面的空格去除不计算
            }

            StringBuilder line = new StringBuilder(words[i]); // Append first word.
            /* Calculate number of spaces, number of extra in for each space */
            int space = 1; // Number of interval between words. # of words - 1.
            int extra = 0; // Extra spaces.
            if (w != i + 1 && w != words.length) { // w moved and not last line.
                space = (maxWidth - len) / (w - 1 - i) + 1; // w - i - 1 is actually (w - 1) - i + 1 - 1. 也就是这一行有几个空隙
                extra = (maxWidth - len) % (w - 1 - i); // Extra is the modular.
            }
            /* Append the rest of the words */
            for (int j = i + 1; j < w; j++) {
                for (int s = space; s > 0; s--) { // Append space first.
                    line.append(' ');
                }
                if (extra-- > 0) {
                    line.append(' '); //如果Extra不为0，就消耗一个，并让当前Extra--
                }
                line.append(words[j]); // Append the word.
            }
            /* Deal with last line's padding spaces */
            int remain = maxWidth - line.length(); // If not last line, remain will be 0.
            while (remain-- > 0) { // Last line.
                line.append(' ');
            }
            res.add(line.toString());
        }
        return res;
    }
