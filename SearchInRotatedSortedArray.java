public class SearchInRotatedSortedArray {   
    //二分法变形，总体看来感觉即使是Array被Rotated了，还是可以像以前一样用二分法，差别不大
    public int search(int[] A, int target) {
        if (A == null || A.length == 0) {
            return -1;
        }

        int start = 0;
        int end = A.length - 1;
        int mid;
        // start + 1 < end 还是九章的写法
        while (start + 1 < end) {
            mid = start + (end - start) / 2;
            if (A[mid] == target) {
                return mid;
            }
            if (A[start] < A[mid]) {
                // situation 1, red line 也就是落在了上边的递增的那部分线上，记住下面那部分线上所有的点的值都小于A[start]
                // 因为此时A[start],A[mid]已经确认了是完全递增的，所以可以用下面的方法直接判断target是不是在其中，如果是，
                //把end向左挪即可
                if (A[start] <= target && target <= A[mid]) {
                    end = mid;
                } else {
                    // 如果target并不在当前mid左侧已经完全有序的部分，那么就把start放到mid,继续判断右边
                    //那边现在右边可以看作是一个缩小了的新的被Rotated Array，继续判断即可。
                    start = mid;
                }
            } else {
                // situation 2, green line 也就是落在了下边的递增的那部分线上
                if (A[mid] <= target && target <= A[end]) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
        } // while
        //最后对start end的终止位置做一下特殊判断即可
        if (A[start] == target) {
            return start;
        }
        if (A[end] == target) {
            return end;
        }
        return -1;
    }
}
