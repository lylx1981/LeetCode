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
        long start = 1, end = x;
        while (start + 1 < end) {
            long mid = start + (end - start) / 2;
            if (mid * mid <= x) {
                start = mid;
            } else {
                end = mid;
            }
        }
        //最后这个地方判断一下，如果end的这个数的平方还小于x，肯定它比Start的数大，肯定返回它，否则返回Start那个数就行
        if (end * end <= x) {
            return (int) end;
        }
        return (int) start;
    }
}
