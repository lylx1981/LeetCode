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
  
  
 ---- 终极攻略！！！！！看看这个帖子马上就清楚原理了，重要（Github里自己写全面拷贝的下面这个链接的内容以防其丢失了）：

http://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/


    Longest Increasing Subsequence Size (N log N)
After few months of gap posting an algo. The current post is pending from long time, and many readers (e.g. here, here, here may be few more, I am not keeping track of all) are posting requests for explanation of the below problem.

Given an array of random numbers. Find longest increasing subsequence (LIS) in the array. I know many of you might have read recursive and dynamic programming (DP) solutions. There are few requests for O(N log N) algo in the forum posts.

For the time being, forget about recursive and DP solutions. Let us take small samples and extend the solution to large instances. Even though it may look complex at first time, once if we understood the logic, coding is simple.

Consider an input array A = {2, 5, 3}. I will extend the array during explanation.

By observation we know that the LIS is either {2, 3} or {2, 5}. Note that I am considering only strictly increasing sequences.

Let us add two more elements, say 7, 11 to the array. These elements will extend the existing sequences. Now the increasing sequences are {2, 3, 7, 11} and {2, 5, 7, 11} for the input array {2, 5, 3, 7, 11}.

Further, we add one more element, say 8 to the array i.e. input array becomes {2, 5, 3, 7, 11, 8}. Note that the latest element 8 is greater than smallest element of any active sequence (will discuss shortly about active sequences). How can we extend the existing sequences with 8? First of all, can 8 be part of LIS? If yes, how? If we want to add 8, it should come after 7 (by replacing 11).

Since the approach is offline (what we mean by offline?), we are not sure whether adding 8 will extend the series or not. Assume there is 9 in the input array, say {2, 5, 3, 7, 11, 8, 7, 9 …}. We can replace 11 with 8, as there is potentially best candidate (9) that can extend the new series {2, 3, 7, 8} or {2, 5, 7, 8}.

Our observation is, assume that the end element of largest sequence is E. We can add (replace) current element A[i] to the existing sequence if there is an element A[j] (j > i) such that E < A[i] < A[j] or (E > A[i] < A[j] – for replace). In the above example, E = 11, A[i] = 8 and A[j] = 9.

In case of our original array {2, 5, 3}, note that we face same situation when we are adding 3 to increasing sequence {2, 5}. I just created two increasing sequences to make explanation simple. Instead of two sequences, 3 can replace 5 in the sequence {2, 5}.

I know it will be confusing, I will clear it shortly!

The question is, when will it be safe to add or replace an element in the existing sequence?

Let us consider another sample A = {2, 5, 3}. Say, the next element is 1. How can it extend the current sequences {2,3} or {2, 5}. Obviously, it can’t extend either. Yet, there is a potential that the new smallest element can be start of an LIS. To make it clear, consider the array is {2, 5, 3, 1, 2, 3, 4, 5, 6}. Making 1 as new sequence will create new sequence which is largest.

The observation is, when we encounter new smallest element in the array, it can be a potential candidate to start new sequence.

From the observations, we need to maintain lists of increasing sequences.

In general, we have set of active lists of varying length. We are adding an element A[i] to these lists. We scan the lists (for end elements) in decreasing order of their length. We will verify the end elements of all the lists to find a list whose end element is smaller than A[i] (floor value).

Our strategy determined by the following conditions,

1. If A[i] is smallest among all end 
   candidates of active lists, we will start 
   new active list of length 1.
2. If A[i] is largest among all end candidates of 
  active lists, we will clone the largest active 
  list, and extend it by A[i].

3. If A[i] is in between, we will find a list with 
  largest end element that is smaller than A[i]. 
  Clone and extend this list by A[i]. We will discard all
  other lists of same length as that of this modified list.
Note that at any instance during our construction of active lists, the following condition is maintained.

“end element of smaller list is smaller than end elements of larger lists”.

It will be clear with an example, let us take example from wiki {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15}.

A[0] = 0. Case 1. There are no active lists, create one.
0.
-----------------------------------------------------------------------------
A[1] = 8. Case 2. Clone and extend.
0.
0, 8.
-----------------------------------------------------------------------------
A[2] = 4. Case 3. Clone, extend and discard.
0.
0, 4.
0, 8. Discarded
-----------------------------------------------------------------------------
A[3] = 12. Case 2. Clone and extend.
0.
0, 4.
0, 4, 12.
-----------------------------------------------------------------------------
A[4] = 2. Case 3. Clone, extend and discard.
0.
0, 2.
0, 4. Discarded.
0, 4, 12.
-----------------------------------------------------------------------------
A[5] = 10. Case 3. Clone, extend and discard.
0.
0, 2.
0, 2, 10. （最重要的就是这里，原来的0,4,12,现在变成了0,2,10, 连之前的4也换掉了。）
0, 4, 12. Discarded.
-----------------------------------------------------------------------------
A[6] = 6. Case 3. Clone, extend and discard.
0.
0, 2.
0, 2, 6.
0, 2, 10. Discarded.
-----------------------------------------------------------------------------
A[7] = 14. Case 2. Clone and extend.
0.
0, 2.
0, 2, 6.
0, 2, 6, 14.
-----------------------------------------------------------------------------
A[8] = 1. Case 3. Clone, extend and discard.
0.
0, 1.
0, 2. Discarded.
0, 2, 6.
0, 2, 6, 14.
-----------------------------------------------------------------------------
A[9] = 9. Case 3. Clone, extend and discard.
0.
0, 1.
0, 2, 6.
0, 2, 6, 9.
0, 2, 6, 14. Discarded.
-----------------------------------------------------------------------------
A[10] = 5. Case 3. Clone, extend and discard.
0.
0, 1.
0, 1, 5.
0, 2, 6. Discarded.
0, 2, 6, 9.
-----------------------------------------------------------------------------
A[11] = 13. Case 2. Clone and extend.
0.
0, 1.
0, 1, 5.
0, 2, 6, 9.
0, 2, 6, 9, 13.
-----------------------------------------------------------------------------
A[12] = 3. Case 3. Clone, extend and discard.
0.
0, 1.
0, 1, 3.
0, 1, 5. Discarded.
0, 2, 6, 9.
0, 2, 6, 9, 13.
-----------------------------------------------------------------------------
A[13] = 11. Case 3. Clone, extend and discard.
0.
0, 1.
0, 1, 3.
0, 2, 6, 9.
0, 2, 6, 9, 11.
0, 2, 6, 9, 13. Discarded.
-----------------------------------------------------------------------------
A[14] = 7. Case 3. Clone, extend and discard.
0.
0, 1.
0, 1, 3.
0, 1, 3, 7.
0, 2, 6, 9. Discarded.
0, 2, 6, 9, 11.
----------------------------------------------------------------------------
A[15] = 15. Case 2. Clone and extend.
0.
0, 1.
0, 1, 3.
0, 1, 3, 7.
0, 2, 6, 9, 11.
0, 2, 6, 9, 11, 15. <-- LIS List
----------------------------------------------------------------------------
It is required to understand above strategy to devise an algorithm. Also, ensure we have maintained the condition, “end element of smaller list is smaller than end elements of larger lists“. Try with few other examples, before reading further. It is important to understand what happening to end elements.

Algorithm:

Querying length of longest is fairly easy. Note that we are dealing with end elements only. We need not to maintain all the lists. We can store the end elements in an array. Discarding operation can be simulated with replacement, and extending a list is analogous to adding more elements to array.

We will use an auxiliary array to keep end elements. The maximum length of this array is that of input. In the worst case the array divided into N lists of size one (note that it does’t lead to worst case complexity). To discard an element, we will trace ceil value of A[i] in auxiliary array (again observe the end elements in your rough work), and replace ceil value with A[i]. We extend a list by adding element to auxiliary array. We also maintain a counter to keep track of auxiliary array length.

Bonus: You have learnt Patience Sorting technique partially 🙂

Here is a proverb, “Tell me and I will forget. Show me and I will remember. Involve me and I will understand.” So, pick a suit from deck of cards. Find the longest increasing sub-sequence of cards from the shuffled suit. You will never forget the approach. 🙂

Update – 17 July, 2016: Quite impressive reponses from the readers and few sites referring the post, feeling happy as my hardwork helping others. It looks like readers are not doing any homework prior to posting comments. Requesting to run through some examples after reading the article, and please do your work on paper (don’t use editor/compiler). The request is to help yourself. Profess to ‘know’ is different from real understanding (no disrespect). Given below was my personal experience.

Initial content preparation took roughly 6 hours to me. But, it was a good lesson. I finished initial code in an hour. When I start writing content to explain the reader, I realized I didn’t understand the cases. Took my note book (I have habit of maintaining binded note book to keep track of my rough work), and after few hours I filled nearly 15 pages of rough work. Whatever the content you are seeing in the gray colored example is from these pages. All the thought process for the solution triggered by a note in the book ‘Introduction to Algorithms by Udi Manber’, I strongly recommend to practice the book.

I suspect, many readers might not get the logic behind CeilIndex (binary search). I leave it as an exercise to the reader to understand how it works. Run through few examples on paper. I realized I have already covered the algorithm in another post.

Update – 5th August, 2016:

The following link worth referring after you do your work. I got to know the link via my recently created Disqus profile. The link has explanation of approach mentioned in the Wiki.
    
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
