/* 思路： 用Hashmap，和那道ismorphic是类似的  用了set的解法。 注意这里要建立双向映射，这个题其实挺有意思的，也就是Word不能同时映射到多个字符，同时一个字符也不能映射到多个Word。

*/

public class Solution {
      public boolean wordPattern(String pattern, String str) {
        if (pattern == null || str == null) return false;
        String[] words = str.split(" ");
        if (words.length != pattern.length()) return false;

        Map<String, Character> map = new HashMap<>(pattern.length());

        for (int i = 0; i < words.length; i++) { 
            char c = pattern.charAt(i);
            if (map.containsKey(words[i])) { //当前Map里如果已经存在这个Word,我就要判断他们是不是映射到同一个字符
                if (map.get(words[i]) != c) 
                return false;
            } else { //如果当前Map里不存在这个Word,这就是一个新的Word,我仍然要保证这个新的Word当前所映射到的字符c是没有被映射到过其他Word的 
                if (map.containsValue(c)) {
                    return false;
                } else {
                    map.put(words[i], c);
                }
                
            }
            
        }

        return true;
    }
}
