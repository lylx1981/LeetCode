/*
    思路： 使用Hashset，同时双指针。Hashset一直记录当前考察的字串所包含的字符，i表示考察的字符串开始的位置，j表示结束的位置。只要j当前判断的字符不在HashSet里，就把它加入到Set里，同时字符串长度+1，然后与Max比较。但是当j当前判断的字符已经在Set里存在的话，那么接下来就重新从j位置作为考察的字符串开始的位置重新判断，这也就是说让i现在挪到j的位置来，同时把i到j之间的字符现在都再次从Set中剔除出去。 
    
    The idea is use a hash set to track the longest substring without repeating characters so far, use a fast pointer j to see if character j is in the hash set or not, if not, great, add it to the hash set, move j forward and update the max length, otherwise, delete from the head by using a slow pointer i until we can put character j to the hash set.
    
    

 */



public int lengthOfLongestSubstring(String s) {
    int i = 0, j = 0, max = 0;
    Set<Character> set = new HashSet<>();
    
    while (j < s.length()) {
        if (!set.contains(s.charAt(j))) {
            set.add(s.charAt(j++));
            max = Math.max(max, set.size()); //Set的大小其实就是当前字串的长度
        } else {
            set.remove(s.charAt(i++));//注意这一步一直执行，直到把j位置也从Set删除掉，那么这样下一轮循环，set.contains(s.charAt(j) 就又会返回False，从而再次重新把J对应的元素加入到Set中了。很巧。
        }
    }
    
    return max;
}

/*下面是九章的代码，大体思想是一样的
另外也可以不用HashSet，而用数组的方法
    int[] map = new int[256]; //256个Char字符 map[char]就是字符Char自己的标志位，1表示已经被包含该字符了，0表示不包含该字符*/
    
     public int lengthOfLongestSubstring(String s) {
        int[] map = new int[256]; //
        
        int j = 0;
        int i = 0;
        int ans = 0;
        for (i = 0; i < s.length(); i++) {
            while (j < s.length() && map[s.charAt(j)]==0) {
                map[s.charAt(j)] = 1;
                ans = Math.max(ans, j-i + 1); //j-i+1就是当前字串的长度
                j ++;
            }
            map[s.charAt(i)] = 0; //这一步的作用是将第i步重新设置为0，这一步会一直执行，直到i增加到j的位置
        }
        
        return ans;
    }
