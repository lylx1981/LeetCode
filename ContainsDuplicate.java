public class ContainsDuplicate {
   
     
    //1.1 Solution 1 - Intermediate Array
    //In a straightforward way, we can create a new array and then copy elements to the
    //new array. Then change the original array by using System.arraycopy().
    // Space is O(n) and time is O(n). 

    /*
    方法一：
    Time complexity: O(N^2), memory: O(1)
    
    The naive approach would be to run a iteration for each element and see whether a duplicate value can be found: this results in O(N^2) time complexity.
    */
    
    public boolean containsDuplicate(int[] nums) {
    
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /*
    方法二
    Time complexity: O(N lg N), memory: O(1) - not counting the memory used by sort
    
    Since it is trivial task to find duplicates in sorted array, we can sort it as the first step of the algorithm and then search for consecutive duplicates.
    */
    
    public boolean containsDuplicate(int[] nums) {
    
        Arrays.sort(nums);
        for (int ind = 1; ind < nums.length; ind++) {
            if (nums[ind] == nums[ind - 1]) {
                return true;
            }
        }
        return false;
    }
    
    /*
    方法三：
    Time complexity: O(N), memory: O(N)
    
    Finally we can used a well known data structure hash table that will help us to identify whether an element has been previously encountered in the array.
    */
    public boolean containsDuplicate(int[] nums) {
    
        final Set<Integer> distinct = new HashSet<Integer>();
        for (int num : nums) {
            if (distinct.contains(num)) {
                return true;
            }
            distinct.add(num);
        }
        return false;
    }
}
