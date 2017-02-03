/* 简单的字符串操作，稍微注意一点就行了。Idea就是从个位数开始依次拿出来，每拿出一次，现有的结果值就会往高位挪一位，最后把整个数字反转了，这时候看看这个数字是不是等于原来的数就行了（回文反转后和原来一样）。

不用想成字符串的相关操作了。。。。另外注意回文的定义： 回文不能说负数。


看这里对于“No Extra Space”的定义，其一般表示可以用不超过O(1)的额外空间  ---- 重要！！！

no extra space means anything of the order greater than O(1) .
Extra space would mean if the space can be expressed as a function of the length of the input. For eg. space could be O(n) if we store the n numbers while comparing arrays.


另外，这道题其实不用考虑Overflow情况，因为一个数如果是回文，那么它输入的时候合法，反转后肯定也不会越界。但是一个数如果不是回文，即使越界了，一旦越界变为负数，那么它还是返回False，效果是一样的。看下面的Leetcode评论：

Hi guys. I just don't know why we need to concern the overflow. When the reversed number overflows, it will becomes negative number which will return false when compared with x.
Here is my AC code.

*/

public class Solution {
    public boolean isPalindrome(int x) {
        if(x < 0) {
            return false;
        }
        return x == reverse(x);    
    }
    
    public int reverse(int x) {
        int rst = 0;
        while(x != 0) {
            rst = rst * 10 + x % 10;
            x = x / 10;
        }
        return rst;
    }
}
