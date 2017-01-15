public class ShortestWordDistance3 {

    /**
     * Array. Only one index. //同时可处理Word1和Word2相同即不同的情况。只用到一个指针prevIndex, 用来记录上一次出现Word1
     * 或者Word2的地方
     * 对于两个词不同的情况，每次只判断当前遇到的值是不是和preIndex存储的值不一样，如果不一样的话，就计算并更新最小距离
     * 如果两个词相同的情况，直接计算并更新最小距离即可。
     * Remember the previous index word1 or word2 seen.
     * Iterate through the array.
     * If we find either word1 or word2.
     * If previous index is initialized,
     * and the two words are the same or previous word is not the same as the current one.
     * Update min.
     * Then we update previous index.
     */
    public int shortestWordDistance(String[] words, String word1, String word2) {
        int prevIndex = -1;
        int min = words.length;
        boolean same = word1.equals(word2);
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1) || words[i].equals(word2)) {
                if (prevIndex != -1 && (same || !words[prevIndex].equals(words[i]))) {
                    min = Math.min(i - prevIndex, min);
                }
                prevIndex = i;
            }
        }
        return min;
    }

    /**
     * Array. Two indices. 用了两个index, 原理还是一样
     * Words may be the same but indices are not.
     * i1 and i2 are the last seen positions for word1 and word2.
     * When word1 and word2 are the same,
     * i1 is the first position, i2 is the latest position.
     */
    public int shortestWordDistanceB(String[] words, String word1, String word2) {
        int i1 = -1;
        int i2 = -1;
        int shortest = Integer.MAX_VALUE;
        boolean same = word1.equals(word2);
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                if (same) { //当两个词一样的时候，i1, i2都往前挪，i1取代原来i2的位置，i当前的值取代i2
                    i1 = i2;
                    i2 = i;
                } else {
                    i1 = i;
                }
            } else if (words[i].equals(word2)) {
                i2 = i;
            }
            shortest = Math.min(shortest, Math.abs(i1 - i2));
        }
        return shortest;
    }

}
