/*
This problem is quite well-formed in my opinion. The triangle has a tree-like structure, which would lead people to think about traversal algorithms such as DFS. However, if you look closely, you would notice that the adjacent nodes always share a 'branch'. In other word, there are overlapping subproblems. Also, suppose x and y are 'children' of k. Once minimum paths from x and y to the bottom are known, the minimum path starting from k can be decided in O(1), that is optimal substructure. Therefore, dynamic programming would be the best solution to this problem in terms of time complexity.

What I like about this problem even more is that the difference between 'top-down' and 'bottom-up' DP can be 'literally' pictured in the input triangle. For 'top-down' DP, starting from the node on the very top, we recursively find the minimum path sum of each node. When a path sum is calculated, we store it in an array (memoization); the next time we need to calculate the path sum of the same node, just retrieve it from the array. However, you will need a cache that is at least the same size as the input triangle itself to store the pathsum, which takes O(N^2) space. With some clever thinking, it might be possible to release some of the memory that will never be used after a particular point, but the order of the nodes being processed is not straightforwardly seen in a recursive solution, so deciding which part of the cache to discard can be a hard job.

'Bottom-up' DP, on the other hand, is very straightforward: we start from the nodes on the bottom row; the min pathsums for these nodes are the values of the nodes themselves. From there, the min pathsum at the ith node on the kth row would be the lesser of the pathsums of its two children plus the value of itself, i.e.:

minpath[k][i] = min( minpath[k+1][i], minpath[k+1][i+1]) + triangle[k][i];
Or even better, since the row minpath[k+1] would be useless after minpath[k] is computed, we can simply set minpath as a 1D array, and iteratively update itself:

For the kth level:
minpath[i] = min( minpath[i], minpath[i+1]) + triangle[k][i]; 

下面的算法实现其实就是一个思想，只不过下面的算法是JAVA的
*/

class Triangle {

    public static void main(String[] args) {
        List<List<Integer>> input = generateInput(TestType.TEST1);
        // List<List<Integer>> input = generateInput(TestType.TEST2);
        testInput(input);
        System.out.println(minimumTotal(input));
    }

    /**
     * DP
     * Math.min(result.get(i), result.get(i + 1)) + triangle.get(curLv).get(i)
     * Pick the smaller one of next row and add it up to current level
     */
    public static int minimumTotal(List<List<Integer>> triangle) {
        int level = triangle.size() - 1;
        List<Integer> res = new ArrayList<Integer>(triangle.get(level));
        for (int i = level - 1; i >= 0; i--) { // start from second last row
            for (int j = 0; j <= i; j++) { // go through each node
                //因为下一行每个孩子只会参与一次对上一行的某个元素的计算，所以只要这个孩子被用过了，它的值就不需要保留了，更新为上一行的新值即可。
                //所以一共就需要一个一维数组就可以了（相比top-down的方法的优势），见如下表达
                int cur = Math.min(res.get(j), res.get(j + 1)) + triangle.get(i).get(j); // add the smaller one
                //注意上面的这个变量应该改个其他名字，否则容易糊涂，比较叫temp,然后下面这行就变为res.set(j, temp)
                res.set(j, cur);
            }
        }
        return res.get(0);
    }
    
    
      // version 0: top-down 这个是九章的算法，这个算法需要int[][] f = new int[n][n];，也就是N^2的存储空间了。
      //原因是上一行的每个f[i][j-1]的值都需要在计算下一行的f[i+1][j-1] 和 f[i+1][j]都要被用到，所以仅用一维数组存储是不够的。
      
public class Solution {
    /**
     * @param triangle: a list of lists of integers.
     * @return: An integer, minimum path sum.
     */
    public int minimumTotal(int[][] triangle) {
        if (triangle == null || triangle.length == 0) {
            return -1;
        }
        if (triangle[0] == null || triangle[0].length == 0) {
            return -1;
        }
        
        // state: f[x][y] = minimum path value from 0,0 to x,y
        int n = triangle.length;
        int[][] f = new int[n][n];
        
        // initialize 
        f[0][0] = triangle[0][0];
        for (int i = 1; i < n; i++) {
            f[i][0] = f[i - 1][0] + triangle[i][0];
            f[i][i] = f[i - 1][i - 1] + triangle[i][i];
        }
        
        // top down
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < i; j++) {
                f[i][j] = Math.min(f[i - 1][j], f[i - 1][j - 1]) + triangle[i][j];
            }
        }
        
        // answer
        int best = f[n - 1][0];
        for (int i = 1; i < n; i++) {
            best = Math.min(best, f[n - 1][i]);
        }
        return best;
    
}   
    //另一个只需要O（1）Space复杂度的方法，原理是直接对三角形里面的值修改了，有人评论说不推荐，除非题目允许这样做。
    The idea is simple.
      
    /*  Go from bottom to top.

      We start form the row above the bottom row [size()-2].

      Each number add the smaller number of two numbers that below it.

      And finally we get to the top we the smallest sum.
      */
      
    public int minimumTotal(List<List<Integer>> triangle) {
        for(int i = triangle.size() - 2; i >= 0; i--)
            for(int j = 0; j <= i; j++)
                triangle.get(i).set(j, triangle.get(i).get(j) + Math.min(triangle.get(i + 1).get(j), triangle.get(i + 1).get(j + 1)));
        return triangle.get(0).get(0);
    }

   }
