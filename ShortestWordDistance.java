public class ShortestWordDistance {
    
    /*
    双指针法
    复杂度: 时间 O(N) 空间 O(1)
    
    思路: 一个指针指向word1上次出现的位置，一个指针指向word2上次出现的位置。从头向后遍历每个元素，
    只要出现word1或者Word2，就计算其与另外一个单词（按照其上次出现的位置）的距离，如果新的距离小于目前的distance,则更新Distance。
    */


    public int shortestDistance(String[] words, String word1, String word2) {
        int idx1 = -1, idx2 = -1, distance = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                idx1 = i;
                // 第一次写入idx就先不比较
                if (idx2 != -1) {
                    distance = Math.min(distance, idx1 - idx2);
                }
            }
            if (words[i].equals(word2)) {
                idx2 = i;
                // 第一次写入idx就先不比较
                if (idx1 != -1) {
                    distance = Math.min(distance, idx2 - idx1);
                }
            }
        }
        return distance;
    }
    
    /**
     * Array. Only one index.
     * Remember the previous index word1 or word2 seen.
     * Iterate through the array.
     * If we find either word1 or word2.
     * If previous index is initialized, and previous word is not same as the current one, update min.
     * Then we update previous index.
     */
    public int shortestWordDistance(String[] words, String word1, String word2) {
        int prevIndex = -1;
        int min = words.length;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1) || words[i].equals(word2)) {
                if (prevIndex != -1 && !words[prevIndex].equals(words[i])) {
                    min = Math.min(i - prevIndex, min);
                }
                prevIndex = i;
            }
        }
        return min;
    }

}
