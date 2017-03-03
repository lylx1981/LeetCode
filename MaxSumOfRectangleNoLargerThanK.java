/* 思路：还是比较难的一道题，基本思路是把2-D的数据都累加到1-D上，然后按照1-D求最大Sum的Subarray不停的算。
对于将2-D变为1-D的过程中，需要试验各种组合，逐个计算。如果看不懂的话，看看下面Youtube的Video，代码基本就是按照这个Video的意思编写的。

The naive solution is brute-force, which is O((mn)^2). In order to be more efficient, I tried something similar to Kadane's algorithm. The only difference is that here we have upper bound restriction K. Here's the easily understanding video link for the problem "find the max sum rectangle in 2D array": Maximum Sum Rectangular Submatrix in Matrix dynamic programming/2D kadane (Trust me, it's really easy and straightforward).

https://www.youtube.com/watch?v=yCQN096CwWM 这个就是上面的Youtube链接，讲的非常清楚

关于kadane's algorithm（这个算法其实就是求1-D的具有最大Sum的Subarray的问题，就是应用经典的动态规划）
https://www.youtube.com/watch?v=86CQq3pKSUw

Once you are clear how to solve the above problem, the next step is to find the max sum no more than K in an array. This can be done within O(nlogn), and you can refer to this article: max subarray sum no more than k. Kadane's Algorithm 

For the solution below, I assume that the number of rows is larger than the number of columns. Thus in general time complexity is O[min(m,n)^2 * max(m,n) * log(max(m,n))], space O(max(m, n)).
*/

public int maxSumSubmatrix(int[][] matrix, int k) {
    //2D Kadane's algorithm + 1D maxSum problem with sum limit k
    //2D subarray sum solution
    
    //boundary check
    if(matrix.length == 0) return 0;
    
    int m = matrix.length, n = matrix[0].length;
    int result = Integer.MIN_VALUE;
    
    //outer loop should use smaller axis
    //now we assume we have more rows than cols, therefore outer loop will be based on cols 
    for(int left = 0; left < n; left++){
        //array that accumulate sums for each row from left to right 
        int[] sums = new int[m]; //m个位置上多行或者多列叠加后的一维数组，每次left向右移动一列的话，该Sum整个要重新开始重来
        for(int right = left; right < n; right++){
            //update sums[] to include values in curr right col 从当前left开始，把右边的列都逐行累计加上去
            for(int i = 0; i < m; i++){
                sums[i] += matrix[i][right]; //对当前的right列累加上去
            }
            
            //we use TreeSet to help us find the rectangle with maxSum <= k with O(logN) time
            TreeSet<Integer> set = new TreeSet<Integer>();
            //add 0 to cover the single row case
            set.add(0);
            int currSum = 0;
            //对累加当前right的列之后的sums数组，开始这当前right列加上去之后这一轮的求最大Sum的Subarry
            for(int sum : sums){ //对叠加后的一位数组Sum开始求其最大Sum的Subarry，当然要满足和还要<=k
                currSum += sum;
                //we use sum subtraction (curSum - sum) to get the subarray with sum <= k
                //therefore we need to look for the smallest sum >= currSum - k 重要！！ 看下面的描述很容易看懂为什么
                /*sums[i,j] = sums[i] - sums[j].

                sums[i,j] is target subarray that needs to have sum <= k
                
                sums[j] is known current cumulative sum
                
                And we use binary search to find sums[i]. Therefore sums[i] needs to have sum >= sums[j] - k*/

                Integer num = set.ceiling(currSum - k);
                if(num != null) result = Math.max( result, currSum - num ); //num找出来后，真正的和现在就是currSum - num
                set.add(currSum);
            }
        }
    }
    
    return result;
}
