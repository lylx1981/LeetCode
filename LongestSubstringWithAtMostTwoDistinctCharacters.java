/**注意这是可以扩展到k的解法。
 * 双指针+Hashmap，比较有意思，而且比较简单的一道题，用一个Hashmap记录当前窗口里有多少个不同字符，没超过的话，前面的指针一直往前走扩大窗口更更新最大值，如果一旦窗口中不同字符值超过k个，就从 后面的指针让其往前走，缩小窗口即可。整个溜达一遍，就可以了 。
 */

public class Solution {
    /**
     * @param s : A string
     * @return : The length of the longest substring 
     *           that contains at most k distinct characters.
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
          // write your code here
      int maxLen = 0;

      // Key: letter; value: the number of occurrences. Value记录当前窗口中每个字符的出现次数
      Map<Character, Integer> map = new HashMap<Character, Integer>();
      int i, j = 0;//j是前面的指针，i是后面的指针
      char c;
      for (i = 0; i < s.length(); i++) {
        while (j < s.length()) {
          c = s.charAt(j);
          if (map.containsKey(c)) {
            map.put(c, map.get(c) + 1);
          } else {
		    //如果当前位置的字符c不在Map中，而且Map中已经有k个字符了，那么这个时候就要缩减窗口了
            if(map.size() ==k) 
              break; 
            map.put(c, 1);
          }
          j++;
        }
      
        maxLen = Math.max(maxLen, j - i); //更新最大值
        c = s.charAt(i); //缩减窗口的话，就是把当前慢指针对应的字符要去掉了
        if(map.containsKey(c)){
          int count = map.get(c);
          if (count > 1) {
            map.put(c, count - 1); //如果这个字符在窗口里面不止一个，那么就只能先将其数值-1，否则，直接从Map中把这个键值对去掉。
          } else {
            map.remove(c);
          }
        }   //注意i接下来会根据外层循环一直向前走，也就是窗口会一直缩，知道map的size又小于k
      }
      return maxLen; 
  }  
