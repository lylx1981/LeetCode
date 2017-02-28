/*
思路： 自己在纸上看看差不多就有感觉了，从一维开始（也就是只关注每个人家的x坐标），从左往右移，只要左边的点比右边少，那么就一直往右移，因为每往右移动一个位置右边点省下来的距离总是比左边这些点要多Travel的距离多，所以就是值得的（当然要随时更新在一个特定位置，其左右两边有多少点）。一直移动到某个点使得该点两边个数一样多，这个时候就是一维下最优点了，其实这样的点可以有多个存在（因为只要不打破两边个数平衡就行）。实际上有一个定理是说对于一组点，要找一个点X，使得所有点到这个点X的距离的和最小的话，这个点其实就是Mediea那个元素。另外，计算一维的所有点Travel的和的时候，因为不要求Return具体Meeting点的坐标，而只让计算Travel的和，而且因为我们已经知道一个满足条件的Meeting点就是只要保证两边点的个数一样就可以，这时候为了达到这样的目的，直接就让两个指针从头尾向中间夹逼，每次左边用掉一个点，右边用掉一个点，一直到头尾指针碰上，那么就Exactly造出了将所有点从某个位置切开并且使得在该位置左右两边点的个数相同这样的效果！！！ 同时，每次每两个点之间的距离的差加入到Sum里（因为每次考察的那两个House的人都要向着Meeting点Travel，而且只要是满足要求的meeting point(左右两边点或者house个数相等，因为前面说了，这样的点可以有多个), 他们两个Travel的和Anyway都是他们之间的距离，因为i对应左侧的一个点，j对应右测的一个点，每次用掉左右两边一个点，最后正好把所有点划分为两半--）。每次处理完两个点，就让i++, j--, 非常巧。最后求得的和就是所有点需要向Meeting point 行驶的和。 一维就是这样，二维情况一样，因为是曼哈顿距离，分别对x,y轴分别计算和，然后两者求和即可。


As long as you have different numbers of people on your left and on your right, moving a little to the side with more people decreases the sum of distances. So to minimize it, you must have equally many people on your left and on your right.


house location: [1,6] -> best point can be anywhere between 1~6
house location: [1,2,6] -> best point is 2, because 1 and 6 don't care where it is as long as the point is between them
*/


public int minTotalDistance(int[][] grid) {
    int m = grid.length, n = grid[0].length;

    List<Integer> I = new ArrayList<Integer>();
    List<Integer> J = new ArrayList<Integer>();
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1) {
                I.add(i);
            }
        }
    }
    for (int j = 0; j < n; j++) {
        for (int i = 0; i < m; i ++) {
            if (grid[i][j] == 1) {  
                J.add(j);
            }
        }
    }
    return minTotalDistance(I) + minTotalDistance(J);
}

public int minTotalDistance(List<Integer> grid) {
    int i = 0, j = grid.size() - 1, sum = 0;
    while (i < j) {
        sum += grid.get(j--) - grid.get(i++); //奇数的House的时候实际上就是在最中间的那个House开会（Media），偶数个House的时候就是在最中间的两个点的任意一个开会，。
    }
    return sum;
}
