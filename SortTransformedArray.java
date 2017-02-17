/*
思路：基本想到了想法。就是利用抛物线的性质，a>0开头向上 （两头的比中心大）， a<0（中心比两头大）, a=0（只跟b有关，肯定是单调的，更简单。） 按情况处理即可，都是在输入数组收尾加两个指针（做Merge操作，也就是让head,tail逐渐向中间靠拢） ，a>0的时候，就看谁大就取谁，将其从后往前放入结果数组中即可。a<0的时候谁小就取谁，然后从头往后放进结果数组即可。


the problem seems to have many cases a>0, a=0,a<0, (when a=0, b>0, b<0). However, they can be combined into just 2 cases: a>0 or a<0

1.a>0, two ends in original array are bigger than center if you learned middle school math before.

2.a<0, center is bigger than two ends.

so use two pointers i, j and do a merge-sort like process. depending on sign of a, you may want to start from the beginning or end of the transformed array. For a==0 case, it does not matter what b's sign is.
The function is monotonically increasing or decreasing. you can start with either beginning or end.*/

public class Solution {
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        int n = nums.length;
        int[] sorted = new int[n];
        int i = 0, j = n - 1;
        int index = a >= 0 ? n - 1 : 0;
        while (i <= j) {
            if (a >= 0) {
                sorted[index--] = quad(nums[i], a, b, c) >= quad(nums[j], a, b, c) ? quad(nums[i++], a, b, c) : quad(nums[j--], a, b, c);
            } else {
                sorted[index++] = quad(nums[i], a, b, c) >= quad(nums[j], a, b, c) ? quad(nums[j--], a, b, c) : quad(nums[i++], a, b, c);
            }
        }
        return sorted;
    }
    
    private int quad(int x, int a, int b, int c) {
        return a * x * x + b * x + c;
    }
