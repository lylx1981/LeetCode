    /*
    思路： 方法一：动态规划。T[i]就代表以nums[i]为结尾的最长的子串的长度。初始值所有T【i】都是1，因为就是他们单个自己。
所以为了计算T[i],也就是检查从所有i前面的位置j，只要nums[j] < nums[i],那么T[j]代表的那个子串就可以把当前nums[i]也包括进来，从而长度加1.

遍历所有元素后，对所有T【i】取其中的最大的那个，就是结果了。
    */        
        
public int lengthOfLIS(int[] nums) {
		// Base case
		if(nums.length <= 1) 
			return nums.length;

		// This will be our array to track longest sequence length
		int T[] = new int[nums.length];

		// Fill each position with value 1 in the array
		for(int i=0; i < nums.length; i++)
			T[i] = 1;


		// Mark one pointer at i. For each i, start from j=0.
		for(int i=1; i < nums.length; i++)
		{
			for(int j=0; j < i; j++)
			{
				// It means next number contributes to increasing sequence.
				if(nums[j] < nums[i]) //注意这里要求是严格递增 
				{
					// But increase the value only if it results in a larger value of the sequence than T[i]
					// It is possible that T[i] already has larger value from some previous j'th iteration
					if(T[j] + 1 > T[i]) //T[i]可能已经被前面的j更新过了，所以这里其实是一个在所有j里取最大值的过程
					{
						T[i] = T[j] + 1;
					}
				}
			}
		}

		// Find the maximum length from the array that we just generated 
		int longest = 0;
		for(int i=0; i < T.length; i++)
			longest = Math.max(longest, T[i]);

		return longest;
}

    /*方法二： 用一个数组tail,tail【i】 表示所有长度为i的递增子串的结束位置的最小值（递增子串的结束位置也就是这个子串的最大值）。
    遍历nums所有元素，同时对于每个元素，用二分法遍历当前tail数组，找到一个可以插入的位置。可以插入的位置不是随便的，可以一旦插入tail[k],必须是说这个元素当前就是所有长度为k的递增子串的结束位置的最小值是当前考察的这个元素。
    
    如果当前考察元素大于所有tail里面的元素，那么size++, 该元素现在也就放在tail的末尾。最后结果返回size的最后的值即可。
    
    -------------------------下面这个分析很好，解释了如何过渡到用二分法的-----------
    第二个想法
    
    先创建一个数组dp，dp[i]表示长度为i的递增子序列的最后一个数的最小值。
    
    然后依次遍历数组中的每个元素，在遍历到第i个元素时，查看dp数组中下标小于i的元素，如果有数比第i个元素大，就替换这个元素来保证dp[i]表示长度为i的递增子序列的最后一个数的最小值，如果有相等的就什么也不错，如果都比第i个元素小，那么将第i个元素值赋给dp[i]。
    
    最后，dp数组中最后一个有值的元素的下标就是所要的结果。
    
    对于这个算法，每次遍历到一个数都要检查前面遍历过的数，时间复杂度是O(n2)，因为要使用到dp数组，空间复杂度也为O(n)。
    
    第三个想法
    
    可以发现在第二个算法中的dp数组应该是一个递增数组。因为每次遍历到第i个数，都只在该数比dp[0..i-1]的数大才将这个数设置给dp[i],所以该数组是递增的。
    
    那么对于这个递增的数组我们可以使用二分查找来提升性能。
    
    这时时间复杂度是O(nlog(n))，因为要使用到dp数组，空间复杂度也为O(n)。
    
    We can solve the longest increasing subsequence problem using only
    arrays and binary search. It processes the sequence elements in order,
    for each new X[i], maintaining a candidate sequence S by:
    • if X[i] is larger than the last element in S, add X[i] into S.
    • otherwise, find the smallest element that is larger than X[i], S[k]
    < X[k] and X[i] ≤ S[k+1], replace S[k+1] with X[i].
    After finishing processing all n numbers, the length of S is is length
    of LIS of X.
    
    
    
    Code*/


/**
     * DP.
     * Create an array to store the minimum value of a subsequence's maximum at a specific length.
     * How to get the value? Use binary search.
     * Search for the insertion point of current number.
     * Update the number.
     * If the insertion point equal to the current size, it means the array can be extended.
     * Then the length of Longest Increasing Subsequence can increase by 1.
     */
    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length]; //这个数组其实一直是递增的
        int size = 0;
        for (int x : nums) {
            int l = 0; // Binary search for the insertion point
            int r = size;
            while (l < r) {
                int m = l + (r - l) / 2;
                if (tails[m] < x) { //所有长度为m的递增子串的结束位置的最小值比x还小，x一定不能放在这里，因为x放在这里的话，就破坏力tail的定义
                    l = m + 1;
                } else { //说明虽然当前tails[m]>x了，但是我要保证所有tail的元素都一直满足定义，那么现在这个m可能位置还是太靠右，我要再继续检查检查tail中有没有更小的位置上的值是>x的，因为这样的话我才能以最贪心的方式找到最长的递增子序列
                    r = m;
                }
            }
            tails[l] = x; // Update tails, won't break its increasing trend
            if (l == size) { // x is larger than all elements l==size说明在当前tails里面就没找到满足的位置，所以现在l要插到最后面，也就是说一个新的长度的递增子串的最小值
                size++;
            }
        }
        return size;
    }
}
