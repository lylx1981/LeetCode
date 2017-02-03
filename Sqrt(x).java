/* 二分法来加速找，注意要找的数是最大的一个满足其平方小于等于x的整数。
注意底下用的是九章的二分模板，从来不让收尾指针碰上，最多只要在最后稍作一下判断即可。
*/


class Solution {
    /**
     * @param x: An integer
     * @return: The sqrt of x
     */
    public int sqrt(int x) {
        // find the last number which square of it <= x
        //if(x==0) return 0； 如果程序要改写成考虑溢出的写法的时候，这里加上这一句对输入是0的情况单独处理即可。
        long start = 1, end = x;
        while (start + 1 < end) {
            long mid = start + (end - start) / 2; // 这里这样的写法可以避免Overflow，尤其对于这种Math题要考虑！！！！！！！
            if (mid * mid <= x) { //另外这里有人评论： 这里有可能Overflow，这里可以写成  if (mid > x/mid)
                start = mid;
            } else {
                end = mid;
            }
        }
        //这里也可以写成 if (end > x/end)的形式，但是有一个特殊情况也就是x=0时会出错，这样只需在程序最前面加一句if(x==0) return 0；即可
        if (end * end <= x) { 
            return (int) end;
        }
        return (int) start;
    }
}

/*
Leetcode的代码如下： 有关于溢出的考虑

Instead of using fancy Newton's method, this plain binary search approach also works.
*/
    
    
public int sqrt(int x) {
    if (x == 0)
        return 0;
    int left = 1, right = Integer.MAX_VALUE; //其实这里改成x就可以了
    while (true) {
        int mid = left + (right - left)/2;
        if (mid > x/mid) { // mid > x/mid这样的表达避免了九章算法中mid * mid <= x中mid * mid 会导致溢出的情况
            right = mid - 1;
        } else {
            if (mid + 1 > x/(mid + 1))
                return mid;
            left = mid + 1;
        }
    }
}

