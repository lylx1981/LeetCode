/*
非常简单的题，对两个数求异或操作，然后用Integer.bitCount统计1的个数就行了。

*/

public class Solution {
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }
}
