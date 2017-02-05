/**
 * 思路：只要一个字符有偶数个，那么有一定能有机会排列成回文，同时”有且只有一个字符可以是奇数个“，那么料单的就是放在最中间的那个。那么问题就是看看一个串是不是只有一个字符是奇数个就行了。可以用计数法，也可以用下面程序的方法，就是类似于投票的方法，加一个Bugdet，再减一个Bugdet，每一加一减一抵消的方法。
 
The idea is to iterate over string, adding current character to set if set doesn't contain that character, or removing current character from set if set contains it.
When the iteration is finished, just return set.size()==0 || set.size()==1.

set.size()==0 corresponds to the situation when there are even number of any character in the string, and
set.size()==1 corresponsds to the fact that there are even number of any character except one.

*/

public class Solution {
    public boolean canPermutePalindrome(String s) {
        Set<Character> set=new HashSet<Character>();
        for(int i=0; i<s.length(); ++i){
            if (!set.contains(s.charAt(i)))
                set.add(s.charAt(i));
            else 
                set.remove(s.charAt(i));
        }
        return set.size()==0 || set.size()==1;
    }
}
