public class ProductofArrayExceptSelf {

    private ProductofArrayExceptSelf p;

        /**
         * Array. One-pass. O(1) Space.
         * The product is actually composed of two parts, the integers on the left, and integers on the right.
         * For a naive O(n) Time, O(n) Space solution.
         * You can use two arrays to store products from the beginning and from the end.
         * Then multiply each position in the two arrays to generate result.
         * If we want to reduce space usage to O(1), we can just replace the two arrays with two integers.
         * 非常巧的方法，虽然只过一次pass, 用了以下2个变量：
        int left = 1; // Product of integers before i.
        int right = 1; // Product of integers after n-1-i.
        
         每次是更新2个不同元素的值，比如i=1时候，适用left当前值先更新res[1]本身再更新left, 也就是包含更新A[1]后的所有左边left的乘积(这个值接下来就可以用来去算A[2]的左边了) ,同时以适用right当前值先更新res[n-2]本身再更新right, 也就是包含更新A[n-2]后的所有右边right的乘积(这个值接下来就可以用来去算A[n-3]的右边了)，
        很巧妙  */
        
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, 1);
        int left = 1; // Product of integers before i.
        int right = 1; // Product of integers after n-1-i.
        for (int i = 0; i < nums.length; i++) {
            res[i] *= left; // Update result in the front.
            left *= nums[i]; // Update left.
            res[n - 1 - i] *= right; // Update result at the end.
            right *= nums[n - 1 - i]; // Update right.
        }
        return res;
    }

    /**
     * Array. Two-pass. O(1) Space.
     * Scan from the beginning to store the result of products of integers on the left.
     * Scan from the end to start to generate final result.
     *这个解法更巧，第一遍从前面开始等于是把所有A[i]左半侧的积算了，第二遍从后面开始，是把A[i]右半边的积算了。可以自己画个图就明白了。 
     */
    public int[] productExceptSelfB(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            res[i] *= right;
            right *= nums[i];
        }
        return res;
    }

    @Before
    public void setUp() {
        p = new ProductofArrayExceptSelf();
    }

    @Test
    public void testExamples() {
        int[] nums = {1, 2, 3, 4};
        int[] res = {24, 12, 8, 6};
        Assert.assertArrayEquals(res, p.productExceptSelf(nums));
        Assert.assertArrayEquals(res, p.productExceptSelfB(nums));
        nums = new int[]{1, 2, 3};
        res = new int[]{6, 3, 2};
        Assert.assertArrayEquals(res, p.productExceptSelf(nums));
        Assert.assertArrayEquals(res, p.productExceptSelfB(nums));
        nums = new int[]{1, 2};
        res = new int[]{2, 1};
        Assert.assertArrayEquals(res, p.productExceptSelf(nums));
        Assert.assertArrayEquals(res, p.productExceptSelfB(nums));
    }

    @After
    public void tearDown() {
        p = null;
    }

}
