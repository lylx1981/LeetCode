/*
思路1. 动态规划
这里动态规划的思路是 dp[i][j] 表示的是 从i 到 j 的字串，是否是回文串。

则根据回文的规则我们可以知道：

如果s[i] == s[j] 那么是否是回文决定于 dp[i+1][ j - 1]

当 s[i] != s[j] 的时候， dp[i][j] 直接就是 false。

动态规划的进行是按照字符串的长度从1 到 n推进的。


下面的代码是dp[j][i], j比i小。思路和上面是一致的。*/

public class Solution {
    public String longestPalindrome(String s) {
        s = s.trim();
        if ( s == null || s.length() == 0 ) {return "";}
        String result = "";
        boolean[][] map = new boolean[s.length()][s.length()];
        char[] string_char = s.toCharArray();
        for ( int i = 0; i < s.length(); ++i ) {
            for ( int j = i; j >= 0; --j ) {
                //i-j<3的情况不用判断，因为这种情况下dp[j+1][i-1]之间就是dp[i][i]的情况了。
                map[j][i] = (string_char[i] == string_char[j] && (( i - j) < 3 || map[j+1][i-1]) );
                if (map[j][i] && ( i - j + 1 ) > result.length() ) {
                        result = s.substring(j, i+1);
                    }
            }
        }
        return result;
    }
}


/*manacher算法 O（n）复杂度，但是貌似这个算法比较难，可以看看下面的帖子，感觉面试的时候写出DP就行。
https://www.felix021.com/blog/read.php?2040
http://articles.leetcode.com/longest-palindromic-substring-part-ii*/

public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        
        int length = s.length();    
        int max = 0;
        String result = "";
        for(int i = 1; i <= 2 * length - 1; i++){
            int count = 1;
            while(i - count >= 0 && i + count <= 2 * length  && get(s, i - count) == get(s, i + count)){
                count++;
            }
            count--; // there will be one extra count for the outbound #
            if(count > max) {
                result = s.substring((i - count) / 2, (i + count) / 2);
                max = count;
            }
        }
        
        return result;
    }
    
    private char get(String s, int i) {
        if(i % 2 == 0)
            return '#';
        else 
            return s.charAt(i / 2);
    }
    
    

/*暴力搜索, \(O(N^2) \)

最简单的方法就是选中i(0~n-1)，然后向两边扩展，复杂度为\(O(N^2) \) . 注意回文长度可能是奇数或者偶数， 即 aba or abba

下面是C++代码，了解一下即可

*/
string longestPalindrome(string s) 
{
  int n = s.length();
  if (n <= 1) return s;
  int start = 0; int maxLen = 1;
  for(int i = 0; i < n; i++)
  {
      //center: i
      int left = i - 1;
      int right = i + 1;
      while(left >= 0 && right < n
          && s[left] == s[right])
          --left, ++right;
      //s[left] != s[right] , s[left+1 : right-1] is palindrome
      int len = (right-1) - (left+1) + 1;
      if(len > maxLen)
          start = left+1, maxLen = len;
      //center: between s[i] and s[i+1]
      if(i+1 < n && s[i] == s[i+1])
      {
          left = i;
          right = i+1;
          while(left >= 0 && right < n
              && s[left] == s[right])
              --left, ++right;
          len = (right-1) - (left+1) + 1;
          if(len > maxLen)
              start = left+1, maxLen = len;
      }
  }
  return s.substr(start, maxLen);
}
