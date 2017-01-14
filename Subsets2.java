public class Subsets2 {

    /**
     * Backtracking.
     * Sort the array first for skipping duplicates later.
     */
    public List<List<Integer>> subsetsWithDup(int[] num) {
        if (null == num || num.length == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(num); // Sort first.
        backtrack(res, num, 0, new ArrayList<>());
        return res;
    }

    /**
     * Backtracking. DFS.
     * Traverse a each node of a solution tree.
     * For each number n in the nums array:
     * | If n is a duplicate of previous number, skip.
     * | Pick it and backtrack.
     * | Remove it.
     */
    private void backtrack(List<List<Integer>> res, int[] nums, int pos, List<Integer> subsets) {
        res.add(new ArrayList<>(subsets)); // Dereference.
        for (int i = pos; i < nums.length; i++) {
            // 只要在这里稍加处理即可，去除duaplicate即可
            // 注意这里要考虑更广泛的情况，不是只判断是不是和pos位置的元素一样，而是判断两个相邻数是不是Duplidate
            //比如考虑1(a)，2(b)，2(c)，3(d)，3(e)，如果当前pos是b位置，那么不仅c位置的2不能加，e位置的3也不能加，
            //所以是判断相邻两个是不是duplicate,而不仅仅是判断是否和pos为duplicate,重要！！
            if (i != pos && nums[i] == nums[i - 1]) { // Check and skip duplicates.
                continue;
            }
            subsets.add(nums[i]); // Add.
            backtrack(res, nums, i + 1, subsets); // Backtrack.
            subsets.remove(subsets.size() - 1); // Reset.
        }
    }

    /**
     * Backtracking.
     * Duplicate with previous number will only be added if:
     * The previous number is already in subset.
     */
    private void backtrackB(List<List<Integer>> res, int[] nums, int pos, List<Integer> subset) {
        if (pos == nums.length) {
            res.add(new ArrayList<>(subset)); // Dereference.
            return;
        }
        subset.add(nums[pos]); // Add.
        backtrackB(res, nums, pos + 1, subset); // Backtrack.
        subset.remove(subset.size() - 1); // Reset.
        if (pos > 0 && nums[pos] == nums[pos - 1] && !subset.isEmpty() && nums[pos - 1] == subset
            .get(subset.size() - 1)) {
            return;
        }
        backtrackB(res, nums, pos + 1, subset);
    }

    /**
     * DP. Bottom-up.
     * Build subsets level by level from empty set.
     * For each number n in nums:
     * | If n is not a duplicate:
     * |   Insert n to each previous subsets and create new subsets.
     * | If n is a duplicate:
     * |   Only need to insert n to the subsets that contains a previous duplicate.
     * E.g. [1 2 2]
     * [] => [], [1] => [], [1], [2], [1 2] => [], [1], [2], [1 2], [2 2], [1 2 2]
     * Add 2 to subsets which have 2, which is the latter half of result.
     */
    public List<List<Integer>> subsetsWithDup2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>()); // Empty set.
        if (null == nums || nums.length == 0) {
            return res;
        }
        Arrays.sort(nums); // Sort first.

        int j, prevSize = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i != 0 && nums[i] == nums[i - 1]) { // Duplicate.
                j = prevSize; // # of previous sets before last number.
            } else {
                j = 0; // No dup, start from beginning.
            }
            prevSize = res.size(); // # of previous sets.
            // Add to previous sets with same num
            for (; j < prevSize; j++) {
                List<Integer> temp = new ArrayList<>(res.get(j));
                temp.add(nums[i]);
                res.add(temp);
            }
        }
        return res;
    }
}
