public class Solution {
    /**
     * @param num: a rotated sorted array
     * @return: the minimum number in the array
     * 和Search in Rotated Sorted Array II这题换汤不换药。同样当A[mid] = A[end]时，无法判断min究竟在左边还是右边。

        3 1 2 3 3 3 3 
        3 3 3 3 1 2 3
        
        但可以肯定的是可以排除A[end]：因为即使min = A[end]，由于A[end] = A[mid]，排除A[end]并没有让min丢失。
        
        另外从这个题要特别注意二分法的归纳：
        1。用mid位置的数组值和某个target比较
        2。mid位置的数组值直接和左右start end的数组值比较
        3。直接用mid的值也就是mid这个索引的值进行比较（比如find duplicate number 287那道题）
     */
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == nums[end]) {
                // if mid equals to end, that means it's fine to remove end
                // the smallest element won't be removed
                end--;
            } else if (nums[mid] < nums[end]) {
                end = mid;
                // of course you can merge == & <
            } else {
                start = mid;
                // or start = mid + 1
            }
        }
        /*
        注意以上While循环也可以用以上代码，就是说以start来与mid来判断，而不是以end来判断，如果mid和start有重复，则让Start++，但是Start++ 会造成更多的代码，所以建议还是用end
        
        while (start + 1 < end) {
            if(nums[start] < nums[end]) {
                return nums[start]; //这一句必须加，否则会出错，而这一步在上面用end比较的时候是不用加的
            }
            int mid = start + (end - start) / 2;

            
            if (nums[mid] == nums[start]) {
                
                start++;
            } else if (nums[mid] < nums[start]) {
                end= mid;
                
            } else {
                start = mid; //原因就在这里，当以start来判断的时候，可能会导致当前start丢失，让前面代码中以end判断的时候，不会出现这种情况
               
            }
        }
        */
        if (nums[start] <= nums[end]) {
            return nums[start];
        }
        return nums[end];
    }
}
