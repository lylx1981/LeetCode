
        /*

状态：square[i][j]代表以matrix[i-1][j-1]为右下角的正方形最长的边长。
转移函数：
    1. square[i][j]＝min(square[i-1][j], square[i-1][j-1], square[i][j-1])+1 
    (因为当matrix[i-1][j-1] == 1时，只有上方，左方和左上方都不为0，那么就取其最小值（因为不取最小值的话，肯定square[i][j]对应生成的更大的 正方形里肯定有0，因为有的地方肯定没法被前面三个正方形的其中之一所盖住（自己画画就看出来了）。所以为了保证一定能盖住，那么就取其三者之间最小的那个边长），然后在对其这个最小值再加1，就是square[i][j]的值。当然，另一种情况，如果只要上方，左方和左上方有1个是0，那么square[i][j]就是0+1=1，也就是其自己这个位上的1代表了一个长度为1的正方形)

    2. square[i][j]=0 (如果matrix[i-1][j-1] == 0，因为不可能有正方形包含0，所以直接说0)

每个正方形的左边，上面，左上正方形最大边长决定了该正方形的最大边长

初始化：额外多创建一行和一列，并初始化为0 --- 这个技巧很重要！！！
结果：遍历过程中每次取最大边长之积
tips: 因为每个正方形只和其左边，上面，左上正方形有关，因此可以用rolling array来进行空间优化，只用两行来记录即可，更新偶数行看奇数行，更新奇数行看偶数行。

* 
当我们判断以某个点为正方形右下角时最大的正方形时，那它的上方，左方和左上方三个点也一定是某个正方形的右下角，否则该点为右下角的正方形最大就是它自己了。这是定性的判断，那具体的最大正方形边长呢？我们知道，该点为右下角的正方形的最大边长，最多比它的上方，左方和左上方为右下角的正方形的边长多1，最好的情况是是它的上方，左方和左上方为右下角的正方形的大小都一样的，这样加上该点就可以构成一个更大的正方形。
但如果它的上方，左方和左上方为右下角的正方形的大小不一样，合起来就会缺了某个角落，这时候只能取那三个正方形中最小的正方形的边长加1了。假设dpi表示以i,j为右下角的正方形的最大边长，则有dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1当然，如果这个点在原矩阵中本身就是0的话，那dp[i]肯定就是0了。class Solution {public:
         */
   
public class MaximalSquare {

    public int maximalSquare(char[][] a) {
    if(a.length == 0) return 0;
    int m = a.length, n = a[0].length, result = 0;
    int[][] b = new int[m+1][n+1];
    for (int i = 1 ; i <= m; i++) { //i,j直接从1开始，因为第0列和第0行肯定就是其本身，最多是单个1自己组成的正方形
        for (int j = 1; j <= n; j++) {
            if(a[i-1][j-1] == '1') {
                b[i][j] = Math.min(Math.min(b[i][j-1] , b[i-1][j-1]), b[i-1][j]) + 1;
                result = Math.max(b[i][j], result); // update result
            }
        }
    }
    return result*result;
}
