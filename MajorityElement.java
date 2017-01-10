public class MajorityElement {
    /**
     * @param nums: a list of integers
     * @return: find a  majority number
     */
    /*
    
    Since the majority always
    take more than a half space, the middle element is guaranteed to be the majority.
    Sorting array takes nlog(n). So the time complexity of this solution is nlog(n).
    
    */

    public int majorityElement(int[] num) {
        if (num.length == 1) {
            return num[0];
        }
        Arrays.sort(num);
        return num[num.length / 2];
        
    }
    
    // 投票算法, 多余2/n的那个元素肯定会留在result里面的
    //O(n) time O(1) space fastest solution
    
    public int majorityElement(int[] nums) {
    	int result = 0;
    	int count = 0;
    	for (int i = 0; i < nums.length; i++) {
    		if (count == 0){
    			result = nums[i];
    			count = 1;
    		} else if (result == nums[i]){
    			count++;
    		} else {
    			count--;
    		}
    	}
    	return result;
    }
}
