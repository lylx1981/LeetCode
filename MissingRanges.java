/*
Given a sorted integer array where the range of elements are [lower, upper] inclusive, return its missing ranges.

For example, given [0, 1, 3, 50, 75], lower = 0 and upper = 99, return ["2", "4->49", "51->74", "76->99"].

思路：就是考虑各种极端情况。核心思想就是考虑 , 注意：A.length==0的情况必须考虑，比较lower upper大小。

1. 为空，lower upper 之间关系。

2. lower A[0]  之间关系

3. A[i]~A[i+1]  之间关系

4 A[A.length-1] ~ upper 之间关系

这四种情况的case。各种情况都要考虑。比较繁琐，但是没有难度。这题适合电面。没难度，就写代码就行了。
*/

public class Solution {   
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {   
        List<String> list = new ArrayList<String>();   
        if(nums == null || nums.length == 0) {   
            if(upper - lower >= 1) {   
                list.add(lower +"->" + upper);   
            } else {   
                list.add(lower+"");   
            }   
            return list;   
        }   
           
        for(int i=0; i<nums.length; i++) {   
            if(i==0 && nums[i]-lower > 1){   
                list.add(lower + "->" + (nums[0]-1));   
            } else if( i==0 && nums[i]-lower ==1){   
                list.add(lower+"");   
            }    
               
            if(i>0 && nums[i]-nums[i-1] >2 ){   
                list.add(nums[i-1]+1 + "->" + (nums[i]-1));   
            } else if(i>0 && nums[i]-nums[i-1] == 2){   
                list.add(nums[i]-1 + "");   
            }    
               
            if(i == nums.length-1 && upper - nums[i] > 1){   
                list.add(nums[nums.length-1]+1 +"->" + upper);   
            } else if(i == nums.length-1 && upper - nums[i] == 1) {   
                list.add(upper+"");   
            }   
        }   
        return list;   
    }   
}  
