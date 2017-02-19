/*
思路：两个指针顺着判断就可以了。

*/

//感觉一点也没有什么Tricky
 public class Solution {
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) return true;
        int indexS = 0, indexT = 0;
        while (indexT < t.length()) {
            if (t.charAt(indexT) == s.charAt(indexS)) {
                indexS++;
                if (indexS == s.length()) return true;
            }
            indexT++;
        }
        return false;
    }
}

//针对于follow UP 一种思想是预处理用一个Map存储每个字符出现的位置。见下面例子
// Follow-up: O(N) time for pre-processing, O(Mlog?) for each S.
    // Eg-1. s="abc", t="bahbgdca"
    // idx=[a={1,7}, b={0,3}, c={6}]
    //  i=0 ('a'): prev=1
    //  i=1 ('b'): prev=3
    //  i=2 ('c'): prev=6 (return true)
    // Eg-2. s="abc", t="bahgdcb"
    // idx=[a={1}, b={0,6}, c={5}]
    //  i=0 ('a'): prev=1
    //  i=1 ('b'): prev=6
    //  i=2 ('c'): prev=? (return false)
    public boolean isSubsequence(String s, String t) {
        List<Integer>[] idx = new List[256]; // Just for clarity
        for (int i = 0; i < t.length(); i++) {
            if (idx[t.charAt(i)] == null)
                idx[t.charAt(i)] = new ArrayList<>();
            idx[t.charAt(i)].add(i);
        }
        
        int prev = 0; //存放至少要在这个索引后面的才有效。
        for (int i = 0; i < s.length(); i++) {
            if (idx[s.charAt(i)] == null) return false; // Note: char of S does NOT exist in T causing NPE
            int j = Collections.binarySearch(idx[s.charAt(i)], prev); //利用Collection的二分查找
            if (j < 0) j = -j - 1; //binary search如果没有找到，则会返回the index of the search key, if it is contained in the list; otherwise, (-(insertion point) - 1)，这一步没有太看懂。
            if (j == idx[s.charAt(i)].size()) return false; // or return list.size() if all elements in the list are less than the specified key 其实就是没有找到的意思？
            prev = idx[s.charAt(i)].get(j) + 1;
        }
        return true;
    }
    
    
