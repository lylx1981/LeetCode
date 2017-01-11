public class SearchRange {
    public int[] searchRange(int[] A, int target) {
        if (A.length == 0) {
            return new int[]{-1, -1};
        }
        
        int start, end, mid;
        int[] bound = new int[2]; 
        
        // search for left bound
        start = 0; 
        end = A.length - 1;
        //start + 1 < end 这样的用法使得想东西更简单，对于start, 
        // end紧紧埃在一起的情况也就是只涉及2个元素的情况暂不考虑。特殊情况处理即可，避免了复杂的边界情况处理和伤脑筋
        while (start + 1 < end) {
            mid = start + (end - start) / 2;
            if (A[mid] == target) {
                end = mid; // 这个操作会导致我们继续向左找，因为将mid赋值给了end
            } 
            // 不管大于还是小于的情况，都把当前mid赋值给start或者end，最传统的二分法写法。
            else if (A[mid] < target) {
                start = mid; 
            } else {
                end = mid;
            }
        }
        
        // 对于对于start, end紧紧埃在一起的情况（只涉及2个元素的情况），分别判断简单处理即可，下同。
        if (A[start] == target) {
            bound[0] = start;
        } else if (A[end] == target) {
            bound[0] = end;
        } else {
            bound[0] = bound[1] = -1;
            return bound;
        }
        
        // search for right bound
        start = 0;
        end = A.length - 1;
        while (start + 1 < end) {
            mid = start + (end - start) / 2;
            if (A[mid] == target) {
                start = mid; // 这个操作会导致我们继续向右找，因为将mid赋值给了start
            } 
            
            // 不管大于还是小于的情况，都把当前mid赋值给start或者end
            else if (A[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if (A[end] == target) {
            bound[1] = end;
        } else if (A[start] == target) {
            bound[1] = start;
        } else {
            bound[0] = bound[1] = -1;
            return bound;
        }
        
        return bound;
    }
}
