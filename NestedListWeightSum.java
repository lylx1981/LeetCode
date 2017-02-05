
    /** 思路，简单的递归即可，每深入一层，将所在层数+1即可。
     */

    
public class NestedListWeightSum {

    /**
     * Recursive.
     * sum = all integer's sum at this depth + all depthSum of other lists.
     */
    public int depthSum(List<NestedInteger> nestedList) {
        return depthSum(nestedList, 1);
    }

    private int depthSum(List<NestedInteger> list, int depth) {
        int sum = 0;
        for (NestedInteger n : list) {
            if (n.isInteger()) {
                sum += n.getInteger() * depth;
            } else {
                sum += depthSum(n.getList(), depth + 1);
            }
        }
        return sum;
    }
}
