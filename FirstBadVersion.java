/* 思路：明显就是一个Binary Search，九章算法模板即可。
      */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int start = 1, end = n;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (isBadVersion(mid)) {
                end = mid;
            } else {
                start = mid;
            }
        }
            
        if (isBadVersion(start)) {
            return start;
        }
        return end;
    }
}
