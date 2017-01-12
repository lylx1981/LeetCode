public class Solution {
    public  ArrayList<ArrayList<Integer>> combinationSum2(int[] candidates, int target) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (candidates == null) {
            return result;
        }

        ArrayList<Integer> path = new ArrayList<Integer>();
        Arrays.sort(candidates);
        helper(candidates, target, path, 0, result);

        return result;
    }

     void helper(int[] candidates, int target, ArrayList<Integer> path, int index,
        ArrayList<ArrayList<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<Integer>(path));
            return;
        }

        int prev = -1;
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] > target) {
                break;
            }

            if (prev != -1 && prev == candidates[i]) {
                continue;
            }

            path.add(candidates[i]);
            //比起combinationSum的原始题，这里只需要把下面一步的i变为i+1就可以了，这样一个元素就只会用一次了，其他代码都一样。
            helper(candidates, target - candidates[i], path, i+1, result);
            path.remove(path.size() - 1);

            prev = candidates[i];
        }
    }
}
