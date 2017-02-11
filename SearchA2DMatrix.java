
    /*
    思路：非常巧的一道题，因为从行从列都有有序的，那么就有希望每次排除一行一列。但是如果我们从左上角，或者右下角开始判断的话，并不方便，比如从左上角开始，如果Traget比（0，0）位置大，那么它既可能在（0，0）右边，也可能在（0，0）下边。不方便。所以我们从右上角开始判断，非常巧，见下面的描述： 
    
    复杂度要求——O(m+n) time and O(1) extra space，同时输入只满足自顶向下和自左向右的升序，行与行之间不再有递增关系，与上题有较大区别。时间复杂度为线性要求，因此可从元素排列特点出发，从一端走向另一端无论如何都需要m+n步，因此可分析对角线元素。
    
首先分析如果从左上角开始搜索，由于元素升序为自左向右和自上而下，因此如果target大于当前搜索元素时还有两个方向需要搜索，不太合适。
如果从右上角开始搜索，由于左边的元素一定不大于当前元素，而下面的元素一定不小于当前元素，因此每次比较时均可排除一列或者一行元素（大于当前元素则排除当前行，小于当前元素则排除当前列，由矩阵特点可知），可达到题目要求的复杂度。也就是这里最核心的就是行用“一定不大于”来排除了，而列用“一定不小于”排除了，使用了不同的排除条件，非常巧！！！！


    在行和列排序好的二维数组中查找目标数字。这里我们用了一个很巧妙的方法，从矩阵的右上角开始找，相当于把这个元素当作mid，目标比mid大，则row + 1，小则col + 1，相等则返回mid。也是类似二分查找的思想。
     */
   
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length < 1 || matrix[0].length <1) {
            return false;
        }
        int col = matrix[0].length-1;
        int row = 0;
        while(col >= 0 && row <= matrix.length-1) {
            if(target == matrix[row][col]) {
                return true;
            } else if(target < matrix[row][col]) {
                col--;
            } else if(target > matrix[row][col]) {
                row++;
            }
        }
        return false;
    }
}
