/*思路：感觉非常简单的题，先对每个字符求出现次数（并保存最大出现次数），然后对每一个不同的出现次数统计都有哪些字符出现了那么多次，然后从最大出现次数开始，把每个次数对应的字符依次按照出现次数打印出来即可。注意下面代码直接用了TreeMap这种数据结构

Build a map of characters to the number of times it occurs in the string
Create an array where the index of the array represents how many times that character occurred in the String
Iterate from the end of the array to the beginning, and at each index, append each character to the return string that number of times.

*/

public class Solution {
    public String frequencySort(String s) {
        int[] freq = new int [256];
        for (char ch: s.toCharArray()) freq[ch]++;
        TreeMap<Integer, List<Character>> tree = new TreeMap<Integer, List<Character>>();
        for (int i=0; i<freq.length; i++) {
            if (freq[i] > 0) {
                if (!tree.containsKey(freq[i])) {
                    tree.put(freq[i], new LinkedList<Character>());
                }
                tree.get(freq[i]).add((char)i);
            }
        }
        StringBuilder sb = new StringBuilder();
        while(tree.size() > 0) {
            Map.Entry<Integer, List<Character>> entry = tree.pollLastEntry(); //The pollLastEntry() method is used to remove and returns a key-value mapping associated with the greatest key in this map, or null if the map is empty. //注意这个函数就是取得了当前次数最多的那个Entry
            for (Character ch: entry.getValue()) {
                sb.append(new String(new char[entry.getKey()]).replace('\0', ch));
            }
        }
        return sb.toString();
    }
}
