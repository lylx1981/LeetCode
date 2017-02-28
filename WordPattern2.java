/*
思路：这道题和自己想的差不多，用到Hashmap以及Backtracking,需要好好看看。总体思想就是从待匹配字符串以及Pattern的的开头开始，分别让Str里面的不同前缀构建Pattern中当前对应字符的映射（用Hashmap和Set记录），然后用新的映射集继续进入下一层递归。每次递归，如果发现当前的Str考察位置开始的某个前缀和当前Pattern考察的字符不存在映射，则添加新的映射。否则如果发现映射集中已经发现当前字符C映射到了其他Word，或者当前Word已经映射到了其他字符C，那么也就是当前考察的这个前缀与Pattern当前考察位置的字符无法Match，也就是无法Follow之前的匹配集中已经添加的映射，直接Break即可。下面的这个英文描述写的很清晰，一看就明白了。这道题还是比较有意思的。

We can solve this problem using backtracking, we just have to keep trying to use a character in the pattern to match different length of substrings in the input string, keep trying till we go through the input string and the pattern.

For example, input string is "redblueredblue", and the pattern is "abab", first let's use 'a' to match "r", 'b' to match "e", then we find that 'a' does not match "d", so we do backtracking, use 'b' to match "ed", so on and so forth ...

When we do the recursion, if the pattern character exists in the hash map already, we just have to see if we can use it to match the same length of the string. For example, let's say we have the following map:

'a': "red"

'b': "blue"

now when we see 'a' again, we know that it should match "red", the length is 3, then let's see if str[i ... i+3] matches 'a', where i is the current index of the input string. Thanks to StefanPochmann's suggestion, in Java we can elegantly use str.startsWith(s, i) to do the check.

Also thanks to i-tikhonov's suggestion, we can use a hash set to avoid duplicate matches, if character a matches string "red", then character b cannot be used to match "red". In my opinion though, we can say apple (pattern 'a') is "fruit", orange (pattern 'o') is "fruit", so they can match the same string, anyhow, I guess it really depends on how the problem states. 我认为这里就是应该一个字符只能映射到一个Word，同样一个Word只能由一个字符来映射，也就是双向一对一

The following code should pass OJ now, if we don't need to worry about the duplicate matches, just remove the code that associates with the hash set.
*/

public class Solution {
  
  public boolean wordPatternMatch(String pattern, String str) {
    Map<Character, String> map = new HashMap<>();
    Set<String> set = new HashSet<>();
    
    return isMatch(str, 0, pattern, 0, map, set);
  }
  //i是当前的Str的考察位置，Pat是Pattern，j是当前在Pattern里面的考察位置
  boolean isMatch(String str, int i, String pat, int j, Map<Character, String> map, Set<String> set) {
    // base case
    if (i == str.length() && j == pat.length()) return true;
    if (i == str.length() || j == pat.length()) return false;
    
    // get current pattern character
    char c = pat.charAt(j);
    
    // if the pattern character exists
    if (map.containsKey(c)) {
      String s = map.get(c); //如果当前字符C已经映射到一个Word了，就把它拿出来，
      
      // then check if we can use it to match str[i...i+s.length()]
      if (!str.startsWith(s, i)) { //看看当前从考察位置i开始，是不是以s为开头，不是的话，直接返回False即可。
        return false;
      }
      
      // if it can match, great, continue to match the rest
      return isMatch(str, i + s.length(), pat, j + 1, map, set); //否则如果Match到了，那就继续从str的i+s位置继续开始与Pattern的j+1位置开始Match
    }
    //如果当前字符还没有映射到任何Set
    // pattern character does not exist in the map
    for (int k = i; k < str.length(); k++) {
      String p = str.substring(i, k + 1);//从i位置开始，取不同长度的前缀，这里字符串p已经跑到了str的k位置

      if (set.contains(p)) {
        continue; //如果该前缀已经出现过则不管（因为之前肯定有其他字符已经映射到这个前缀了），这里也保证了，一个Word只会由一个字符映射过来。这个地方和Word Pattern 1的写法不一样，但是思想是一样的就是要保证双向一对一映射
      }

      // create or update it
      map.put(c, p); //否则就把c映射到该前缀
      set.add(p); //并且把该前缀加入到set中
      
      // continue to match the rest 现在Map里加了新的Map，同时Set也更新了，这个时候用新的Map和Set，并且从str的k+1位置开始，和pattern的j+1开始进行匹配。
      if (isMatch(str, k + 1, pat, j + 1, map, set)) {
        return true;
      }

      // backtracking 回溯操作，删除刚才添加的前缀p,已经添加的P与字符C的映射
      map.remove(c);
      set.remove(p);
    }
    
    // we've tried our best but still no luck
    return false;
  }
  
}
