
    /** 思路：用Hashmap, 对nums计数当作budget，然后再对nums2一个个判断即可，如果在nums中，且budget不为0，就属于交集中的一员。


    What if elements of nums2 are stored on disk, and the memory is
    limited such that you cannot load all elements into the memory at
    once?
    If only nums2 cannot fit in memory, put all elements of nums1 into a HashMap, read chunks of array that fit into the memory, and record the intersections.
    
    If both nums1 and nums2 are so huge that neither fit into the memory, sort them individually (external sort), then read 2 elements from each array at a time in memory, record intersections.

    I think the goal of this question is to test whether the interview understands some of the data engineering techniques. From a data engineer's perspective, basically there are three ideas to solve the question:
    
    Store the two strings in distributed system(whether self designed or not), then using MapReduce technique to solve the problem;
    
    Processing the Strings by chunk, which fits the memory, then deal with each chunk of data at a time;
    
    Processing the Strings by streaming, then check.


     */

    
public class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < nums1.length; i++)
        {
            if(map.containsKey(nums1[i])) map.put(nums1[i], map.get(nums1[i])+1);
            else map.put(nums1[i], 1);
        }
    
        for(int i = 0; i < nums2.length; i++)
        {
            if(map.containsKey(nums2[i]) && map.get(nums2[i]) > 0)
            {
                result.add(nums2[i]); 
                map.put(nums2[i], map.get(nums2[i])-1); //Budget-1
            }
        }
    
       int[] r = new int[result.size()];
       for(int i = 0; i < result.size(); i++)
       {
           r[i] = result.get(i);
       }
    
       return r;
    }
}
