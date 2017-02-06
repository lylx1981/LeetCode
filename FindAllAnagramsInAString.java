/* 这道题是另外一到76题很像，都是用来Slide Wondow的模板！！！！ 这道题其实很有意思！！

思路： 用了Slide Window的模板，一方面一个Map用来记P中每个字符还有待匹配的次数。另外一方面一个Count用来记录当前Window到底有多少个Match了，一旦Count将为0，表明当前发现一个Match，把当前Left指针的位置记录下即可。Left指针没往前挪一步，有可能会导致Match丧失，则要重新更新Count数目(也就是Count重新>0)，使得重新开始找Match.同时Map里每个字符有待匹配的
的个数也会有所变化。

Same idea from a fantastic sliding window template, please refer:
https://discuss.leetcode.com/topic/30941/here-is-a-10-line-template-that-can-solve-most-substring-problems

Time Complexity will be O(n) because the "start" and "end" points will only move from left to right once.


*/


public class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> list = new ArrayList<>();
    if (s == null || s.length() == 0 || p == null || p.length() == 0) return list;
    
    int[] hash = new int[256]; //character hash
    
    //record each character in p to hash
    for (char c : p.toCharArray()) {
        hash[c]++; //把P中所有字符都加到Map里。注意这里一个字符有多个重复的时候，hash里面对应的位也是>1的
    }
    //two points, initialize count to p's length
    int left = 0, right = 0, count = p.length();
    
    while (right < s.length()) { //让右指针一直往前跑
        //move right everytime, if the character exists in p's hash, decrease the count
        //current hash value >= 1 means the character is existing in p
        if (hash[s.charAt(right)] >= 1) {//如果>=1, 表明在P中的这个字符还有匹配的需要，那么因为right这个位的出现，需要匹配的次数-1.
            count--;//count--表示发现了一个match
        }
        hash[s.charAt(right)]--; //因为当前right匹配了一个p中的这个字符，所以还有待匹配的个数也减去1，这个数也可以是负数，表明当前Window里面该字符出现的次数比在P中出现的次数还多，表示富余的量。
        right++;
        
        //when the count is down to 0, means we found the right anagram
        //then add window's left to result list
        if (count == 0) {
            list.add(left);//发现了一个完整的Anagram，记录Left位置
        }
        //if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
        //++ to reset the hash because we kicked out the left
        //only increase the count if the character is in p
        //the count >= 0 indicate it was original in the hash, cuz it won't go below 0
        if (right - left == p.length() ) {
           //现在Window长度和P长度一样，则现在要往右挪动左指针
            if (hash[s.charAt(left)] >= 0) { //如果当前左指针对应的数大于等于0，代表如果kick off这一位的话，就会失去一个原先已经用这个位的字符构成的匹配，这个时候需要匹配的个数就要重新往上加。当然，如果hash[s.charAt(left)] < 0,说明现在即使kick off掉这一位，没关系，因为我们还有富余的量，不会影响当前匹配。所以需要匹配的总个数没有变化。
                count++;//因为失去一个已经匹配好的字符，则count++
            }
            hash[s.charAt(left)]++; //因为失去了一个富裕的量，我们把Hash里面对应的值也要++回去。记得hash[s.charAt(left)]表明的是这个字符还有1个有待匹配，小于0的时候表明这个字符现在在Window里富余几个。
            left++;
        
        }

        
    }
        return list;
    }
}
