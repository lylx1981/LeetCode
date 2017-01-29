public class GroupAnagrams {

    /** 思路，把每个字符串排序，然后用Hashmap。Hashmap里的Key就是一个排好序的字串，Value是相关的字串与Key字串有相同字符，但是字符顺序不是排好序的字串
     * 
     * 
     * Hash Table.
     * Use sorted word as the key, since all anagrams share the same sorted form.
     * Then same anagrams form a list as the value.
     * For each word, sort its character array and build a string key.
     * If the key is not in map yet, add an empty array list to map.
     * Then add the word to the list.
     * <p>
     * A possible improvement is sorting the word using counting sort. Two pass.
     * Since all inputs are lowercase characters.
     * Reduce sorting time from O(nlogn) to O(n).
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return Collections.emptyList();
        }
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            char[] word = strs[i].toCharArray(); //注意这里的代码，如何对一个字符串排序，需要先转换成Char数组
            Arrays.sort(word); //对Char数组进行排序
            String key = String.valueOf(word);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(strs[i]); //没有当前Key，就新加一个Key
        }
        return new ArrayList<>(map.values());
    }
}
