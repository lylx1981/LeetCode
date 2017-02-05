
    /** 思路：二分法即可，Time Complexity O(logN)。这个题和其他题有点像，主要有记下几个地方要注意：
     * 1.  mid = left + (right - left) /  防止越界。
     * 2.   long t = mid * mid; //防止越界
     * 3. while (left+1 < right) 九章模板
     * 4. 对于平方，对于一个数num,其平方根不可能大于num/2, 所以right指针直接从num/2开始即可。
     * 


     */

public class Solution {
    public boolean isPerfectSquare(int num) {
          if (num < 1) return false;
          long left = 1, right = num/2;// long type to avoid 2147483647 case
        
          while (left+1 < right) {
            long mid = left + (right - left) / 2;
            long t = mid * mid;
            if (t > num) {
              right = mid - 1;
            } else if (t < num) {
              left = mid + 1;
            } else {
              return true;
            }
          }
          //九章模板最后对left end分别判断一下即可。
          if(left*left == num || right * right == num){
              return true;
          }else {
              return false;
          }
        
    }
}
