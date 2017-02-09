/* 思路: 这道题比较有意思，应该熟练掌握。第一种思路就是下面这个思路，比较有意思，就是观察到凡是在Border的O以及与这些O相连的内部O都不应该被标记为X，所以第一部就先把这些O用BFS法找出来，标记为零一个标记D，然后把其他所有O标记为X，然后再把标记为D的这些位置再标记回O即可。

First, check the four border of the matrix. If there is a element is
'O', alter it and all its neighbor 'O' elements to '1'.
Then ,alter all the 'O' to 'X'
At last,alter all the '1' to 'O'
For example:

         X X X X           X X X X             X X X X
         X X O X  ->       X X O X    ->       X X X X
         X O X X           X 1 X X             X O X X
         X O X X           X 1 X X             X O X X
         
第二种思路是类似于200 number of islands这道题的代码，思想基本一致。         
         
*/

public class Solution {
    public void solve(char[][] board) {
        if (board.length == 0) return;
        
        int rowN = board.length;
        int colN = board[0].length;
        Queue<Point> queue = new LinkedList<Point>();
       
       //get all 'O's on the edge first
        for (int r = 0; r< rowN; r++){
        	if (board[r][0] == 'O') {
        		board[r][0] = '+';
                queue.add(new Point(r, 0));
        	}
        	if (board[r][colN-1] == 'O') {
        		board[r][colN-1] = '+';
                queue.add(new Point(r, colN-1));
        	}
        	}
        
        for (int c = 0; c< colN; c++){
        	if (board[0][c] == 'O') {
        		board[0][c] = '+';
                queue.add(new Point(0, c));
        	}
        	if (board[rowN-1][c] == 'O') {
        		board[rowN-1][c] = '+';
                queue.add(new Point(rowN-1, c));
        	}
        	}
        

        //BFS for the 'O's, and mark it as '+'
        while (!queue.isEmpty()){
        	Point p = queue.poll();
        	int row = p.x;
        	int col = p.y;
        	if (row - 1 >= 0 && board[row-1][col] == 'O') {board[row-1][col] = '+'; queue.add(new Point(row-1, col));}
        	if (row + 1 < rowN && board[row+1][col] == 'O') {board[row+1][col] = '+'; queue.add(new Point(row+1, col));}
        	if (col - 1 >= 0 && board[row][col - 1] == 'O') {board[row][col-1] = '+'; queue.add(new Point(row, col-1));}
            if (col + 1 < colN && board[row][col + 1] == 'O') {board[row][col+1] = '+'; queue.add(new Point(row, col+1));}        	      
        }
        

        //turn all '+' to 'O', and 'O' to 'X'
        for (int i = 0; i<rowN; i++){
        	for (int j=0; j<colN; j++){
        		if (board[i][j] == 'O') board[i][j] = 'X';
        		if (board[i][j] == '+') board[i][j] = 'O';
        	}
        }
       
        
    }
}
    
    
    class SurroundedRegions {
    public static void main(String[] args) {
        
    }
    
    /**
     * 第二种思路是类似于200 number of islands这道题的代码，思想基本一致。  还是对所有位置进行遍历，如果遇到一个O，就BFS它，同时记录每次BFS所经过过的点，如果BFS过程中发现有任何一个O在边界上，那么BFS中所涉及的所有O都不应该被标记为X。否则，如果经过BFS，没有一个O在边界上，那么就对其遍历过的所有O都变为X。代码基本是200题一样，但是还是比较建议上面的方法。
     * Use a queue to store index to do BFS
     * A 2d boolean array to remember whether a point is visited
     * A 2d int array to represent 4-connectivity
     * 
     * Traverse through the board and BFS at where it's 'O' and not visited
     * Set surround as true at first
     * Create an integer list for points to change
     * Check points around to see whether there is an 'O' point within the board
     * and not visited
     * If so, add it to queue, set visited true
     * If not, it's not surrounded
     */
    public static void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;
        
        Queue<Integer> q = new LinkedList<Integer>();
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n]; //记录是否已经被Visisted过。
        int[][] dir = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O' && !visited[i][j]) {
                    boolean surround = true;
                    List<Integer> pointsToChange = new ArrayList<Integer>(); //用来记录本次有待Change的点的集合
                    q.add(i * n + j); // add root
                    visited[i][j] = true; // set root visited
                    while (q.size() > 0) { // BFS
                        int point = q.poll(); // get from queue
                        pointsToChange.add(point); //把所有Quene里的元素都加进这个有待Change的集合里。
                        int x = point / n; // get coordinates
                        int y = point % n;
                        // try 4 direction 考察四个方向，看有没有在Border上
                        for (int k = 0; k < dir.length; k++) { 
                            int nextX = x + dir[k][0];
                            int nextY = y + dir[k][1];
                            if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n) { // within board
                                if (board[nextX][nextY] == 'O' && !visited[nextX][nextY]) // add to queue
                                    q.add(nextX * n + nextY);
                                visited[nextX][nextY] = true; // set visited 标记为已经Visisted过了，后面不需要再考察它了
                            } else surround = false; // false if on the boundry
                        }
                    }
                    if (surround) for (int p : pointsToChange) board[p / n][p % n] = 'X'; // set to 'X' 当且仅当surround是True，才把有待Change的点都从O变为X。
                }
            }
        }
    }
}
