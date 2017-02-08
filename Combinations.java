/*/
//思路： 直接按照BackTracking的模板改写过来的，主要就是几个变化，一个是个数等于k个就退出，另外为了避免添加重复元素，多了一个StartIndex, 每次只能从该Index之后的元素添加，这样就保证了可以不添加重复的。

下面这段代码里是直接改写模板的，其实里面的nums数组都可以进一步不要，在自己下一次练习的时候，要优化一下代码

discuss.leetcode.com/topic/46162/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partioning
*/

public class Solution {
    public List<List<Integer>> combine(int n, int k) {
   List<List<Integer>> list = new ArrayList<>();
   // Arrays.sort(nums); // not necessary
   int[] nums = new int[n];
   for(int i = 0; i < n; i++){
       nums[i] = i+1;
   }
   backtrack(list, new ArrayList<>(), nums, k, 0);
   return list;
}

    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, int k, int startIndex){
       if(tempList.size() == k){
          list.add(new ArrayList<>(tempList));
       } else{
          for(int i = startIndex; i < nums.length; i++){ 
             if(tempList.contains(nums[i])) continue; // element already exists, skip
             tempList.add(nums[i]);
             backtrack(list, tempList, nums, k, i+1); //因为加入了当前i，所以接下来再加入的时候只能从其后的元素开始加
             tempList.remove(tempList.size() - 1);
          }
       }
    }
}
