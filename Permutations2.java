/*/
//回溯法，backtracking，和第一个Permunation题目比较，最重要的技巧在于如何去重，这里主要的技艺是如果没用过，如果其有相同元素，且前面那个相同元素也没用过的话，它也不能用。以此来消除重复，非常巧！！！！

这个方法可以Generalize，就是说用一个“让所有元素保持相对顺序的约束达到了去除的目的”，所以这个题就变为了加上一个新的约束即可，这个技巧要会用。也就是不直接解决去重复，而是找一个能达到去重复的约束即可。

Permutation 排列
Combination 组合 
另外可以想到，如果另外一个题的变形是让保留重复的话，一个简单的方法就是TempList里不存数字，而直接存数组的索引，简单改动原来的Permunation即可。

*/

public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums); //这道题排序是必须的！！！！！
    backtrack(list, new ArrayList<>(), nums, new boolean[nums.length]);
    return list;
}

private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, boolean [] used){
    if(tempList.size() == nums.length){
        list.add(new ArrayList<>(tempList));
    } else{
        for(int i = 0; i < nums.length; i++){
            
            /*
            下面的判断主要是为了去除重复元素影响。
            比如，给出一个排好序的数组，[1,2,2]，那么第一个2和第二2如果在结果中互换位置，
            我们也认为是同一种方案，所以我们强制要求相同的数字，原来排在前面的，在结果
            当中也应该排在前面，这样就保证了唯一性。所以当前面的2还没有使用的时候，就
            不应该让后面的2使用。
            */
            
            //如果用过了，那么直接跳过。或者如果没用过，但是其有相同元素，且前面那个相同元素也没用过的话，它也不能用。以此来消除重复，非常巧！！！！
            if(used[i] || i > 0 && nums[i] == nums[i-1] && !used[i - 1]) continue;
            used[i] = true; 
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, used);
            used[i] = false; 
            tempList.remove(tempList.size() - 1);
        }
    }
}
