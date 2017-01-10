class PascalsTriangle2 {

    public static void main(String[] args) {
        PascalsTriangle2 p = new PascalsTriangle2();
        int k = 3;
        System.out.println(p.getRow(k).toString());
    }

    /**
     * Generate in-place within a list
     * 0, 0, 0, 0, initialized, [1, 0, 0, 0]
     * i = 1, [1, 1, 0, 0]
     * i = 2, [1, 2, 1, 0]
     * i = 3, [1, 3, 3, 1]
     * 每一轮的i,就是计算当前第i行的值，且从后往前算的目的是因为这样不用需要额外的空间来存储可能被覆盖
     * 的j值，因为一个j会被用2次，来计算下一行的值。从后往前计算就可以把这个问题解决了，很巧。
     */
    public List<Integer> getRow(int k) {
        List<Integer> row = new ArrayList<Integer>(k + 1);
        row.add(1);
        for (int i = 1; i <= k; i++) { // repeat k times
            for (int j = i - 1; j >= 1; j--) { // do it backwards
                row.set(j, row.get(j - 1) + row.get(j));
            }
            row.add(1); // add 1 at the end
        }
        return row;
    }
}
