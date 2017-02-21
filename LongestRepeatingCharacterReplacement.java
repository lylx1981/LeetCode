/*思路： 这道题非常非常巧，感觉自己还是对这类题目没有好的意识，不会往正确的方法想，要加强这种类型题的训练。尤其是这道题用的是double pointer以及夹逼法和滑动窗口的思想，感觉自己对这方面的比较薄弱，总是不能想到，尤其是对于理解夹逼法还是不行。
自己还是往DP的路子上想，没有特别好的思路。

这道题其实很巧，假设没有K的限制，那么给定一个字符串的话，我们最少需要多少次能够把这个字符串都改成同一个字符？答案是字符串长度减去字符串里出现最多的那个字符。

所以应用上面的那个原理，我们就用一个滑动窗口，我只要保证整个滑动窗口满足窗口的长度减去窗口内出现次数最多的字符的次数<k,则我就一直让窗口增加（初始设置两个指针，start, end,end往后跑窗口增大，start往后挪窗口减小，），因为此时所有整个窗口都是可以变成完全一样的字符组成的字符串。当发现我现在滑动窗口满足窗口的长度减去窗口内出现次数最多的字符的次数>=k的时候，此时滑动窗口不能满足条件了，我就让窗口缩小，也就是让start往右挪，直到又符合整个滑动窗口满足窗口的长度减去窗口内出现次数最多的字符的次数<k，此时整个滑动窗口又整个可以变为一个全是一样字符的字符串了。不断的这样，同时用一个变量记录所有过程中出现的窗口最大值即可。

非常巧的滑动窗口，以及双指针夹逼。

The problem says that we can make at most k changes to the string (any character can be replaced with any other character). So, let's say there were no constraints like the k. Given a string convert it to a string with all same characters with minimal changes. The answer to this is

length of the entire string - number of times of the maximum occurring character in the string
Given this, we can apply the at most k changes constraint and maintain a sliding window such that

(length of substring - number of times of the maximum occurring character in the substring) <= k

同样的描述
There's no edge case for this question. The initial step is to extend the window to its limit, that is, the longest we can get to with maximum number of modifications. Until then the variable start will remain at 0.

Then as end increase, the whole substring from 0 to end will violate the rule, so we need to update start accordingly (slide the window). We move start to the right until the whole string satisfy the constraint again. Then each time we reach such situation, we update our max length.

*/

public int characterReplacement(String s, int k) {
        int len = s.length();
        int[] count = new int[26];
        int start = 0, maxCount = 0, maxLength = 0;
        for (int end = 0; end < len; end++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(end) - 'A']); //不停的更新当前窗口中出现次数最多的那个字符的出现数量
            while (end - start + 1 - maxCount > k) { //当>k时表示条件以破坏，现在需要缩小窗口
                count[s.charAt(start) - 'A']--; //缩减窗口时，同时把缩减到的字符对应的次数也要减小
                start++;
            }
            maxLength = Math.max(maxLength, end - start + 1); //不停的记录曾经出现过的最大窗口长度。
        }
        return maxLength;
    }
