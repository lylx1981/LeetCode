/* 思路：和自己想的差不多，但是这道题的归纳的太巧了，从头到尾依次决定每个位置该插入什么字符，这个Best Candiate需要是当前仍然剩余最多的字符，且当前考察位置允许这个字符插入。

这样的话比如aaabbcc
第一次插入a,因为a最多，然后是ab,然后是aba, 因为b插入后a又可以插了，a现在和c一样剩余2次，按照下面的代码应该还是插入a,然后是abac,因为c是当前剩余次数最多了（因为a,b都只剩1次了---这个地方是最巧的地方，自然的把c插入到这里来，而不是继续插b,而导致后面c可能有无法隔开的风险！！巧！！ ），然后是abaca ,然后是abacab

This is a greedy problem.
Every time we want to find the best candidate: which is the character with the largest remaining count. Thus we will be having two arrays.
One count array to store the remaining count of every character. Another array to keep track of the most left position that one character can appear.
We will iterated through these two array to find the best candidate for every position. Since the array is fixed size, it will take constant time to do this.
After we find the candidate, we update two arrays.
*/

public class Solution {
    public String rearrangeString(String str, int k) {
        int length = str.length();
        int[] count = new int[26];
        int[] valid = new int[26];
        for(int i=0;i<length;i++){
            count[str.charAt(i)-'a']++; //对出现次数计数
        }
        StringBuilder sb = new StringBuilder();
        for(int index = 0;index<length;index++){
            int candidatePos = findValidMax(count, valid, index); //寻找一个插入的Candiate
            if( candidatePos == -1) return "";
            count[candidatePos]--; //对姚插入的这个字符的计数减去1
            valid[candidatePos] = index+k; //该字符下次能够插入的位置是index+k了，因为当前index已经插入该字符了。
            sb.append((char)('a'+candidatePos));
        }
        return sb.toString();
    }
    
   private int findValidMax(int[] count, int[] valid, int index){
       int max = Integer.MIN_VALUE;
       int candidatePos = -1;
       for(int i=0;i<count.length;i++){
           if(count[i]>0 && count[i]>max && index>=valid[i]){
               max = count[i]; //寻找还剩余最大次数的字符，当然字符以其在count里的Index来表达，最后再转化成字母就行了。
               candidatePos = i;
           }
       }
       return candidatePos;
   }
}
