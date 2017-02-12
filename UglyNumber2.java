 /*
    思路：比较巧的一道题，实际上所有可能的Ugly数可以划分为三组，每组都是有特定的表示方法，例如：
    
    
(1) 1×2, 2×2, 3×2, 4×2, 5×2, … 2的某个倍数
(2) 1×3, 2×3, 3×3, 4×3, 5×3, … 3的某个倍数
(3) 1×5, 2×5, 3×5, 4×5, 5×5, … 5的某个倍数

那么每次就像将这三个队列合并一样，每次只选当前队列头最小的那个作为下一个ugly的数即可。所以，刚开始就是1×2， 1×3，因为1×2已经加入了，所以现在该队列头就是2×2了， 接下来2×2比1×5小，所以下一个Ugly number就是2×2

这个方法很巧，一般不好想到。这道题其实应该算作是DP。
    
  The idea of this solution is from this page:http://www.geeksforgeeks.org/ugly-numbers/

The ugly-number sequence is 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, …
because every number can only be divided by 2, 3, 5, one way to look at the sequence is to split the sequence to three groups as below:

(1) 1×2, 2×2, 3×2, 4×2, 5×2, …
(2) 1×3, 2×3, 3×3, 4×3, 5×3, …
(3) 1×5, 2×5, 3×5, 4×5, 5×5, …
We can find that every subsequence is the ugly-sequence itself (1, 2, 3, 4, 5, …) multiply 2, 3, 5.

Then we use similar merge method as merge sort, to get every ugly number from the three subsequence.

*/
public class Solution {
    public int nthUglyNumber(int n) {
        int[] ugly = new int[n];
        ugly[0] = 1;
        int index2 = 0, index3 = 0, index5 = 0;
        int factor2 = 2, factor3 = 3, factor5 = 5;
        for(int i=1;i<n;i++){
            int min = Math.min(Math.min(factor2,factor3),factor5);
            ugly[i] = min;
            if(factor2 == min)
                factor2 = 2*ugly[++index2];
            if(factor3 == min)
                factor3 = 3*ugly[++index3];
            if(factor5 == min)
                factor5 = 5*ugly[++index5];
        }
        return ugly[n-1];
    }
}
