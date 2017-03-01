
/*
思路： 这道题最巧的方法是利用一个观察现象，也就是--连起来的区域如果投影到x轴或者Y轴上，那么都应该是一连串的1。那么
利用二分法，并且针对x,y这个点，用y坐标分别和左右边界进行二分法确定投影到x轴的一连串1的起点和终点(因为Image是有二维数组表示的，也就是说P(x,y) 代表第i行第j列那个点，所以左右边界实际上是由每列上是否有1确定的，所以也就是和y是相关的，因为二维数组里面，同样的y才代表一列)。同样，用x坐标和上下边界进行二分法找到投影到Y轴的一连串的1的起点和终点（同理，上下边界实际上和由每行是否有1确定的，而二维数组里面，每行是和x坐标相关的，因为二维数组里面，同样的x才代表一行。）。这两个一连串1的长度进行相乘，便是所需要的面积。

Suppose we have a 2D array

"000000111000000"
"000000101000000"
"000000101100000"
"000001100100000"
Imagine we project the 2D array to the bottom axis with the rule "if a column has any black pixel it's projection is black otherwise white". The projected 1D array is

"000001111100000"
Theorem

If there are only one black pixel region, then in a projected 1D array all the black pixels are connected --也就是连起来的区域如果投影到x轴上，那么应该是一连串的1，所以，就可以根据y的坐标，从Grid的左边向y寻到第一个包含1的那一列，同理从Grid的右边界向左寻找第一个包含1的那一列的位置，这样就是在x轴上确定了一连串的1的开始位置以及结束位置。具体找的时候，利用经典二分法就行了。对于映射到Y轴的情况，同理，利用x寻找就行了。

One simple way without any worry about boundary, is as follows: 因为Image是有二维数组表示的，所以左右边界实际上是由每列上是否有1确定的，所以也就是和y是相关的，因为二维数组里面，同样的y才代表一列。同理，上下边界实际上和由每行是否有1确定的，而二维数组里面，每行是和x相关的，因为二维数组里面，同样的x才代表一行。

Use a vertical line, to jump to the leftmost black pixel , in the range of [0, y]
Use a vertical line, to jump to the rightmost black pixel, in the range of [y, n - 1]
Use a horizontal line, to jump to the topmost black pixel, in the range of [0, x]
Use a horizontal line, to jump to the bottommost black pixel, in the range of [x, m - 1]



To find the left boundary, do the binary search in the [0, y) range and find the first column vector who has any black pixel.

To determine if a column vector has a black pixel is O(m) so the search in total is O(m log n)

We can do the same for the other boundaries. The area is then calculated by the boundaries.
Thus the algorithm runs in O(m log n + n log m)

*/
//下面这个算法就是用上面的思想实现的，虽然Leetcode上有更紧凑的代码，但是这个感觉最好看懂
public int minArea(char[][] image, int x, int y) {
    int left = leftmost(image, 0, y, true);
    int right = rightmost(image, y, image[0].length - 1, true);
    int top = leftmost(image, 0, x, false);
    int bottom = rightmost(image, x, image.length - 1, false);
    return (right - left + 1) * (bottom - top + 1);
}

int leftmost(char[][] image, int min, int max, boolean horizontal) { //horizontal是个标记位，说明当前是在计算左右边界，还是上下边界
    int l = min, r = max;
    while (l < r) {
        int mid = l + (r - l) / 2;
        if (!hasBlack(image, mid, horizontal)) {
            l = mid + 1;
        } else {
            r = mid;
        }
    }
    return l;
}

int rightmost(char[][] image, int min, int max, boolean horizontal) {
    int l = min, r = max;
    while (l < r) {
        int mid = l + (r - l + 1) / 2;
        if (!hasBlack(image, mid, horizontal)) {
            r = mid - 1;
        } else {
            l = mid;
        }
    }
    return r;
}
//用于判断某行或者某列是否有1存在
boolean hasBlack(char[][] image, int mid, boolean horizontal) {
    if (horizontal) {
        for (int i = 0; i < image.length; i++) {
            if (image[i][mid] == '1') {
                return true;
            }
        }
    } else {
        for (int j = 0; j < image[0].length; j++) {
            if (image[mid][j] == '1') {
                return true;
            }
        }
    }
    return false;
}



/*
思路：从(x,y)点直接BFS即可，凡是访问过的点就标记为Visisted （或者直接将其改为另外一个字符也可以，这样的话就不用要Visisted数组了）。每次从Queue里拿出一个元素，然后和当前记录的X,Y轴的四个边界进行比较，如果需要更新就更新边界，最后面积就是四个边界的值计算得到即可。*/

public class Solution {
    public int minArea(char[][] image, int x, int y) {
        if (image == null || image.length == 0 || image[0].length == 0) {
            return 0;
        }
        int row = image.length;
        int col = image[0].length;
        int up = x;
        int down = x;
        int left = y;
        int right = y;
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[]{x, y});
        
        boolean[][] visited = new boolean[row][col];
        visited[x][y] = true;
        
        int[] dx = new int[]{1, 0, -1, 0};
        int[] dy = new int[]{0, 1, 0, -1};
        
        while (!queue.isEmpty()) {
            int[] indices = queue.poll();
            int i = indices[0];
            int j = indices[1];
            up = Math.min(up, i);
            down = Math.max(down, i);
            left = Math.min(left, j);
            right = Math.max(right, j);
            for (int k = 0; k < 4; k++) {
                int nextX = i + dx[k];
                int nextY = j + dy[k];
                if (nextX >= 0 && nextX < row && nextY >= 0 && nextY < col && !visited[nextX][nextY] && image[nextX][nextY] == '1') {
                    visited[nextX][nextY] = true;
                    queue.offer(new int[]{nextX, nextY});
                }
            }
        }
        
        return (down - up + 1) * (right - left + 1);
    }
}
