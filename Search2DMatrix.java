// 方案一： Binary Search Twice 两次使用二分法
//先用二分法确定在哪一行，然后再在那一行里面再次应用二分法找元素
//仍然注意九章的写法，二分法的停止条件是start=1<end也就是不让两个指针碰住，每次把mid赋值给两者。然后最后的情况特殊判断处理就行了，
//这样不用伤脑筋去想边界情况。
//另外注意算中点的时候都是用 int mid = start + (end - start) / 2;而不是mid = (start + end) /2，这里是为了防止溢出

public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        if (matrix[0] == null || matrix[0].length == 0) {
            return false;
        }
        
        int row = matrix.length;
        int column = matrix[0].length;
        
        // find the row index, the last number <= target 
        int start = 0, end = row - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (matrix[mid][0] == target) {
                return true;
            } else if (matrix[mid][0] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        //特殊情况判断即可
        if (matrix[end][0] <= target) {
            row = end;
        } else if (matrix[start][0] <= target) {
            row = start;
        } else {
            return false;
        }
        
        // find the column index, the number equal to target
        start = 0;
        end = column - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (matrix[row][mid] == target) {
                return true;
            } else if (matrix[row][mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        //特殊情况判断即可
        if (matrix[row][start] == target) {
            return true;
        } else if (matrix[row][end] == target) {
            return true;
        }
        return false;
    }
}

// 方案二：Binary Search Once 只使用一次二分法 把二维数组看作一维数组来做，注意其相对转换的关系公式：
// n * m matrix convert to an array => matrix[x][y] => a[x * m + y]
// an array convert to n * m matrix => a[x] =>matrix[x / m][x % m];
// disadvantage: 1. m * n may overflow 2. / and % are expensive

public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        if (matrix[0] == null || matrix[0].length == 0) {
            return false;
        }
        
        int row = matrix.length, column = matrix[0].length;
        int start = 0, end = row * column - 1;
        
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int number = matrix[mid / column][mid % column];
            if (number == target) {
                return true;
            } else if (number < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
         //特殊情况判断即可
        if (matrix[start / column][start % column] == target) {
            return true;
        } else if (matrix[end / column][end % column] == target) {
            return true;
        }
        
        return false;
    }
}
