/**
 * Given a sorted integer array without duplicates, return the summary of its ranges.
 * <p>
 * For example, given [0,1,2,4,5,7], return ["0->2","4->5","7"].
 * <p>
 * <p>
 * Tags: Array
 * Similar Problems: (M) Missing Ranges, (H) Data Stream as Disjoint Intervals
 */ 非常简单的题，遍历一遍i即可，没有难度，每到一个新的i,另一个指针end就从i+1开始往后找到最后一个连续的地方
 * 然后这就是新找到的Range，然后i挪到这个位置，继续向前找就行了。
 */
public class SummaryRanges {

    private SummaryRanges s;

    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int end = i + 1;
            while (end < nums.length && nums[end] - nums[end - 1] == 1) {
                end++;
            }
            if (end - i == 1) {
                res.add(Integer.toString(nums[i]));
            } else {
                res.add(nums[i] + "->" + nums[end - 1]);
                i = end - 1;
            }
        }
        return res;
    }

    @Before
    public void setUp() {
        s = new SummaryRanges();
    }

    @Test
    public void testExamples() {
        int[] nums = {0, 1, 2, 4, 5, 7};
        List<String> res = s.summaryRanges(nums);
        Assert.assertEquals("[0->2, 4->5, 7]", res.toString());
    }

    @After
    public void tearDown() {
        s = null;
    }

}
