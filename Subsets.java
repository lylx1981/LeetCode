public class Subsets {

    private Subsets s;



    /** 方法一
     * Backtracking. //我喜欢这种Backtracking的方法
     */
     
    /**
     * DFS.
     * Add current subset to result first.
     * Put current number in subset.
     * Then backtrack with the rest of the numbers.
     * Reset by remove last number in subset.
     * Next iteration will move to next number then all subsets will not have this number.
     */
    public List<List<Integer>> subsetsB(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        subsetsB(res, nums, 0, new ArrayList<>());
        return res;
    }

    public void subsetsB(List<List<Integer>> sets, int[] nums, int pos, List<Integer> subset) {
        sets.add(new ArrayList<>(subset)); // Dereference and add current subset to result.
        for (int i = pos; i < nums.length; i++) {
            subset.add(nums[i]); // With nums[i].
            subsetsB(sets, nums, i + 1, subset); // Backtrack to generate add subsets with s[i].
            subset.remove(subset.size() - 1); // Remove s[i], next round there won't be s[i].
        }
    }
    
     /**
     * Backtracking. 另外一种Backtracking
     * Visit:
     * Backtrack without current number.
     * Then add the number and backtrack again.
     * Reset.
     * Base case:
     * When the end of array is reached, add de-referenced subset to result and return.
     */
    public List<List<Integer>> subsetsA(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrackA(res, nums, 0, new ArrayList<>());
        return res;
    }

    private void backtrackA(List<List<Integer>> res, int[] nums, int pos, List<Integer> subset) {
        if (pos == nums.length) {
            res.add(new ArrayList<>(subset));
            return;
        }
        backtrackA(res, nums, pos + 1, subset); // Without current number.
        subset.add(nums[pos]);
        backtrackA(res, nums, pos + 1, subset); // With current number.
        subset.remove(subset.size() - 1); // Reset.
    }

    /** 方法二：
     * Iterative.//迭代方法，就是每次在现有subsets中的每一个成员的末尾加入新的一个元素，同时该新元素作为一个单元素set,加入subsets中
     * Build from empty set to the next subsets.
     * Adding each subset the current num, new subsets are generated.
     * Then add new subsets to all subsets and generate next round.
     * Stop when we iterate through the array.
     * E.g.:
     * [] -> [] [1]
     * [] [1] -> [] [1] [2] [1, 2]
     */
    public List<List<Integer>> subsetsC(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        subsets.add(new ArrayList<>()); // Add empty set.
        for (int i = 0; i < nums.length; i++) {
            int n = subsets.size();
            for (int j = 0; j < n; j++) {
                List<Integer> set = new ArrayList<>(subsets.get(j)); // Dereference.
                set.add(nums[i]);
                subsets.add(set);
            }
        }
        return subsets;
    }
     /** 方法三：
     // 位操作法 1<<n 所得的结果就是2^
     // // check whether the jth digit in i's binary representation is 1 以下代码很重要：
     if ((i & (1 << j)) != 0) //原理是先把1左移j位，则对应二进制数除了j位是1，其他位都是0（包括j中为了和i进行&运算，1前面也是都补0）
     //然后这个数与i这个数再进行&操作，如果得到的数非0，则证明第j位是1，否则第j位一定是0。
     * @param S: A set of numbers.
     * @return: A list of lists. All valid subsets.
     */
    public ArrayList<ArrayList<Integer>> subsets(int[] nums) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        int n = nums.length;
        Arrays.sort(nums);
        
        // 1 << n is 2^n
        // each subset equals to an binary integer between 0 .. 2^n - 1
        // 0 -> 000 -> []
        // 1 -> 001 -> [1]
        // 2 -> 010 -> [2]
        // ..
        // 7 -> 111 -> [1,2,3]
        for (int i = 0; i < (1 << n); i++) {
            ArrayList<Integer> subset = new ArrayList<Integer>();
            for (int j = 0; j < n; j++) {
                // check whether the jth digit in i's binary representation is 1
                if ((i & (1 << j)) != 0) {
                    subset.add(nums[j]);
                }
            }
            result.add(subset);
        }
        
        return result;
    }

    @Before
    public void setUp() {
        s = new Subsets();
    }

    @Test
    public void testExamples() {
        int[] nums = {1, 2, 3};
        List<List<Integer>> res = s.subsetsB(nums);
        System.out.println(res.toString());
    }

    @After
    public void tearDown() {
        s = null;
    }
}
