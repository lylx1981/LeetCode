
    /*
    思路：这道题一看就是用Hashmap，但是第一次自己想的就是把所有可能的Pair和他们的距离直接算好放进Map里就行了，但是发现最后这样的解法开销更大，见下面的描述，主要是组合爆炸问题。 最后的思路是只把每个字符出现的位置放进Map里，然后没遇到一个输入，当场计算一个两个词之间的最短距离即可。这道题学习到的是虽然最后推荐的解法也存在重复计算问题，但是每个方法都是各有利益，是对空间时间上的一种权衡。另外可以看看下面有人问和我一样的问题，以及相应的解释。
    
   Why a O(n^2) preprocessing time while O(1) for shortest not a good idea?

We can use a hash map to store the shortest distances for any pair of words.

I did the same thing after I got TLE and thought on it for sometime i realized the O(n^2) + O(1) solution is a poor decision any time n (the number of words) > the number of times shortest method is called. Lets take an example say n = 64 and shortest is called 20 times. Now if we look at the approximate total number of operations in our O(n^2) + O(1) solution we would get 64^2 + 20 = 4116. now if you observe others O(n)+O(n) solution their approximate total number of operations would be 64 + (20 * 64) = 1280.

In short O(n^2) + O(1) solution is only better if shortest is called n times.

     */
   
 /**
     * Hash Table.
     * Store the word to all its indices mapping.
     * Get the shortest distance from two lists.
     */
    public class WordDistance {

        Map<String, List<Integer>> locs;

        public WordDistance(String[] words) {
            locs = new HashMap<>();
            for (int i = 0; i < words.length; i++) {
                if (!locs.containsKey(words[i])) {
                    locs.put(words[i], new ArrayList<>());
                }
                locs.get(words[i]).add(i);
            }
        }

        /**
         * Merge. O(m + n).
         * The indices are already sorted in the list.
         * To get shortest distance, just move the pointer with smaller value.
         * For i < indices1.size(), j < indices2.size():
         * | Get indices in two lists, index1 and index2.
         * | If index1 > index2:
         * |   Update shortest with min(shortest, index1 - index2).
         * |   j++.
         * | Else
         * |   Update shortest with min(shortest, index2 - index1).
         * |   i++.
         */
        public int shortest(String word1, String word2) {
            List<Integer> indices1 = locs.get(word1);
            List<Integer> indices2 = locs.get(word2);
            int shortest = Integer.MAX_VALUE;
            for (int i = 0, j = 0; i < indices1.size() && j < indices2.size(); ) {
                int index1 = indices1.get(i);
                int index2 = indices2.get(j);
                if (index1 > index2) {
                    shortest = Math.min(shortest, index1 - index2);
                    j++;
                } else {
                    shortest = Math.min(shortest, index2 - index1);
                    i++;
                }
            }
            return shortest;
        }
    }
