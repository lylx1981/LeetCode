/**
 * Definition for a point.
 * class Point {
 *     int x;
 *     int y;
 *     Point() { x = 0; y = 0; }
 *     Point(int a, int b) { x = a; y = b; }
 * }
 */
public class Solution {

/*
思路： 自己想来半天没有思路，一直在想比较复杂的比如DP之类的解法，但是看了Leetcode题解后，发现就是对每个点进行遍历，求每个点与其他点组成的直线，然后用Hashmap保存，同时计算候选解，然后随时更新全局最大值。斜率之类的，自己也都想到了。
应知应会：

平面里确定一条直线要两个数据，可以是两个不同的点(高中数学做法)，也可以是一个点加一个斜率(这道题做法)
斜率k = (y2 - y1)/(x2 - x1)，当 x1 == x2 时，分母为0，斜率为无穷，表示和y轴平行的直线们
在计算机里使用double表示斜率，是不严谨的也是不正确的，double有精度误差，double是有限的，斜率是无限的，无法使用有限的double表示无限的斜率，不过此题的test case没有涉及这个问题
表示斜率最靠谱的方式是用最简分数，即分子分母都无法再约分了。分子分母同时除以他们的最大公约数gcd即可得到最简分数
gcd(a,b)，一般求的是两个正整数的gcd。这道题a和b有可能是0，分别表示与x轴或y轴平行的斜率(注意ab不能同时为0,表示同一个点没有意义)，所以这道题我们规定ab取值范围：a>=0,b>=0。至于负数，先变成正数取gcd，再确定最终斜率的正负
gcd ( a , b ) = (b == 0) ? a : gcd ( b , a % b ), a,b是任意非负整数且a,b至少有一个不为0
观察gcd(a,b)，假设a,b为非负整数：
a和b中有一个为零，那么gcd为另一个不为0的数；
a和b都为0，gcd为0；
算法：

没什么算法就是穷举：对每个点，都计算一下该点和其他点连线的斜率，这样对于这个点来说，相同斜率的直线有多少条，就意味着有多少个点在同一条直线上，因为这些直线是相同的。另外，如果计算过点A和点B的直线，当算到点B时，就不用再和A连线了，因为AB这条直线上的点数已经都计算过了（也就是两层循环外层 i 从 0 到 n, 内层 j 从 i+1 到 n）。这里，我们用哈希表，以斜率为key，记录有多少重复直线。另外，注意有完全一样重复点的情况

*/
public int maxPoints(Point[] points) {
    /*
    遍历每个点，看它和后面的每个点构成的直线上有多少个点
    对每个点建立map，斜率是key
    斜率要用分数的形式，不要用double的形式存
    计算分数时先求分子分母的最大公约数gcd，再都除以gcd
    重合的点特殊处理
    */
    int l = points.length;
    if (l == 0) return 0;
    if (l <= 2) return l;
    int res = 0;
    for (int i = 0; i < l - 1; i++) {
        Map<String, Integer> map = new HashMap<>(); //注意是每次都当前判断的第i个节点，都重新生成一个Map。所以不存在4个点两两组成平行线的话（具体相同斜率），会怎么样的问题，因为对于每个点都是单独考察的，Map都是单独的。
        int overlap = 0;
        int lineMax = 0;
        for (int j = i + 1; j < l; j++) {
            int x = points[i].x - points[j].x;
            int y = points[i].y - points[j].y;
            if (x == 0 && y == 0) {
                overlap++;
                continue;
            }
            int gcd = generateGcd(x, y);
            x /= gcd;
            y /= gcd;
            // 用string来存储斜率
            String slope = String.valueOf(x) + String.valueOf(y);
            int count = map.getOrDefault(slope, 0);
            count++;
            map.put(slope, count);
            lineMax = Math.max(lineMax, count);
        }
        res = Math.max(res, lineMax + overlap + 1); //+1是因为当前i节点自己本身并没有还没有算进去，所以要加上
    }
    return res;
}

public int generateGcd(int x, int y) {
    if (y == 0) return x;
    return generateGcd(y, x % y);
}


}
