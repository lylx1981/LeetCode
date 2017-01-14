class Solution {
    /**
     * @param A: An integers array.
     * @return: return any of peek positions.
     */
    public int findPeak(int[] A) {
        // write your code here
        int start = 1, end = A.length-2; // 1.使得答案在之间，2.后面while循环中不会出界
        
      //处理特殊情况，两头的两个元素
        if (A[0] > A[1]) return 0;
        if (A[A.length - 1] > A[A.length - 2]) return n - 1;
        
        while(start + 1 <  end) {
            int mid = (start + end) / 2;
            if(A[mid] < A[mid - 1]) { //说明往左侧是个上坡，故必有一个峰，因为A[-1]假设都是负无穷
                end = mid;
            } else if(A[mid] < A[mid + 1]) { //说明往右侧是个上坡，故必有一个峰，因为A[A.length-1]假设都是负无穷
                start = mid;
            } else {
                return mid; //mid现在直接就是一个波峰，直接返回即可
                
            }
        }
        //特殊情况处理即可
        if(A[start] < A[end]) {
            return end;
        } else { 
            return start;
        }
    }
}
