/**
 * 思路 - 自己的想法是找到以s.charAt(0)开始的最长的palindrome，然后再用s.length()减去这个长度，就是我们需要添加的字符长度。这时候我们再在原String之前添加就可以了。比如下面的代码就是这个思想，基本就是Burte Force的来找最长的palindrome。
 * 
        但是这个题最好的方法是KMP，太复杂的代码来。另外还有一个神代码，如下，但是很难理解其中的奥妙。
 */

/*The key point is to find the longest palindrome starting from the first character, and then reverse the remaining part as the prefix to s. Any advice will be welcome!*/

public String shortestPalindrome(String s) {
    int i = 0, end = s.length() - 1, j = end; char chs[] = s.toCharArray();
    while(i < j) {
         if (chs[i] == chs[j]) {
             i++; j--;
         } else { 
             i = 0; end--; j = end; //不旦发现不是palindrome，就从头开始寻找（i变为0），同时end也往里缩一位，然后j继续从End开始，所以基本上是Brute Force方法
         }
    }
    return new StringBuilder(s.substring(end+1)).reverse().toString() + s; //最后找到最长palindrome后，（因为End现在的位置就是最长palindrome结束的位置） 直接把End+1后面那部分串颠倒一下（因为这部分串不是palindrome，必须生成新的与之配对的串），然后新生成的这个颠倒串直接刚在S前面即可。
}

//神代码如下 
public class Solution {

    public String shortestPalindrome(String s) {

        int j = 0;

        for (int i = s.length() - 1; i >= 0; i--) {//找到第一个使他不回文的位置

           if (s.charAt(i) == s.charAt(j)) { 

               j += 1;  //这个样做究竟是要达到什么目的？？ 一直没有想明白。。。。。

           }

        }

        if (j == s.length()) {  //本身是回文

            return s; 

        }

        String suffix = s.substring(j); // 后缀不能够匹配的字符串

        String prefix = new StringBuilder(suffix).reverse().toString(); // 前面补充prefix让他和suffix回文匹配

        String mid = shortestPalindrome(s.substring(0, j)); //递归调用找 [0,j]要最少可以补充多少个字符让他回文

        String ans = prefix + mid  + suffix;

        return  ans;

    }

}
