public class PascalsTriangle {
    /**
     * @param triangle: a list of lists of integers.
     * @return: An integer, minimum path sum.
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<List<Integer>>();
        if (numRows <= 0) {
            return triangle;
        }

        List<Integer> firstRow = new ArrayList<Integer>();
        firstRow.add(1);
        triangle.add(firstRow);

        for (int i = 1; i < numRows; i++) {
            List<Integer> lastRow = triangle.get(i - 1);
            List<Integer> row = new ArrayList<Integer>(i + 1);

            for (int j = 0; j < i + 1; j++) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(lastRow.get(j - 1) + lastRow.get(j));
                }
            }
            triangle.add(row);
        }
        return triangle;
    }
}
