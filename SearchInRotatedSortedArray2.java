public class Solution {
    // 这个问题在面试中不会让实现完整程序
    // 只需要举出能够最坏情况的数据是 [1,1,1,1... 1] 里有一个0即可。
    // 在这种情况下是无法使用二分法的，复杂度是O(n)
    // 因此写个for循环最坏也是O(n)，那就写个for循环就好了
    //  如果你觉得，不是每个情况都是最坏情况，你想用二分法解决不是最坏情况的情况，那你就写一个二分吧。
    //  反正面试考的不是你在这个题上会不会用二分法。这个题的考点是你想不想得到最坏情况。
    public boolean search(int[] A, int target) {
        for (int i = 0; i < A.length; i ++) {
            if (A[i] == target) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Clarification: non-descending order
     * Ends up same as sequential search at worst. 
     */
    public boolean search(int[] A, int target) {
        if (A == null || A.length == 0) return false;
        int l = 0;
        int r = A.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (A[m] == target) return true;
            /*skip*/
            if(A[l] == A[m] && A[m] == A[r]) { //如果左右指针都一样而且不等于target,就把他们排除在外继续在小范围进行搜索即可。
            //也可以有别的方法，比如只排除右指针所指元素
                l++;
                r--;
            } else if(A[l] == A[m]) l = m + 1;
            else if(A[m] == A[r]) r = m;
            else if (A[l] < A[m]) { // left half sorted
                if (A[l] <= target && target < A[m]) {r = m - 1;
                } else l = m + 1;
            } else if (A[l] > A[m]) { // right half sorted
                if (A[m] < target && target <= A[r]) l = m + 1;
                else r = m - 1;
            }
        }
        return false;
    }
}
