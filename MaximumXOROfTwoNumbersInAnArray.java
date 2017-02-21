/*思路： 这道题比较费劲，Leetcode最受欢迎的解法不是自己想到的，自己想的是和下面这个解法类似，有点像存储各种模式的意思，就是对于每个sentence里面的Word，我计算一下以这个Word开始一行的话，那么下一行应该开始的是哪个Word。把这个结果记录在nextIndex中。另外一个，如果Cols很大，可能造成以当前Worrd开始写这一行的话，可能写到Sentence末尾以后这一行还没有结束，那么我就要重新在从sentence的第0个Word开始写，同时我记录一下一共跨越了几次Stentence的最后一个Word。


This algorithm's idea is:
to iteratively determine what would be each bit of the final result from left to right. And it narrows down the candidate group iteration by iteration. e.g. assume input are a,b,c,d,...z, 26 integers in total. In first iteration, if you found that a, d, e, h, u differs on the MSB(most significant bit), so you are sure your final result's MSB is set. Now in second iteration, you try to see if among a, d, e, h, u there are at least two numbers make the 2nd MSB differs, if yes, then definitely, the 2nd MSB will be set in the final result. And maybe at this point the candidate group shinks from a,d,e,h,u to a, e, h. Implicitly, every iteration, you are narrowing down the candidate group, but you don't need to track how the group is shrinking, you only cares about the final result.


example: Given [14, 11, 7, 2], which in binary are [1110, 1011, 0111, 0010].
Since the MSB is 3, I'll start from i = 3 to make it simplify.

i = 3, set = {1000, 0000}, mask =  1000, max = 1000
i = 2, set = {1100, 1000, 0100, 0000}, mask =  1100, max = 1100
i = 1, set = {1110, 1010, 0110, 0010}, mask =  1110, max = 1100 //每个数的倒数第二位都是1，所以没有相异的位能够使得达到当前tep(1110)的倒数第二个1，所以max最终保持不变，还是1100
i = 0, set = {1110, 1011, 0111, 0010}, mask =  1111，max = 1100，当前tep是（1101），也和上一步一样，没法找到合适的Set元素，使得两者异或操作的结果是tep1101,所以max还是保持不变。

I saw deeply and found out a very important xor feature I missed, that is: if a^b=c, then a^c=b, b^c=a. That's why the answer using this code:

            for(int prefix : set){
                if(set.contains(tmp ^ prefix)) {
                    max = tmp;
                    break;
                }
            }
            
另外可以看看另外一种用Trie的方法。            

*/

public class Solution {
    public int findMaximumXOR(int[] nums) {
        int max = 0, mask = 0;
        for (int i = 31; i >= 0; i--) {
            mask |= (1 << i);
            HashSet<Integer> set = new HashSet<Integer>();
            for (int num : nums) {
                set.add(num & mask); // reserve Left bits and ignore Right bits
            }
            
            /* Use 0 to keep the bit, 1 to find XOR
             * 0 ^ 0 = 0 
             * 0 ^ 1 = 1
             * 1 ^ 0 = 1
             * 1 ^ 1 = 0
             */
            int tmp = max | (1 << i); // in each iteration, there are pair(s) whoes Left bits can XOR to max
            //也就是现在让max的当前i位也为1，我现在看看有没有让这一位为1，也就是看看有没有2个在Set里面的元素使得他们在当前位的异或操作结果是1. 这里是根据性质if a^b=c, then a^c=b, b^c=a. 
            for (int prefix : set) {
                if (set.contains(tmp ^ prefix)) {//当前这个数有没有在Set里存在
                    max = tmp;
                }
            }
        }
        return max;
    }
}
