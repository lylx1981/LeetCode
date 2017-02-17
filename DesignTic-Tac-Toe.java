/*
思路：设计问题。自己把题意理解错了，以为是n是一个数字，棋盘大小是一个数字，这样的话问题会复杂的多。看了答案后才发现n和期盼大小是一个数，这也就是预示着需要一行，一列，或者一对角线的符号都是某一个选手的才可以算赢。所以问题就很简单了，看下面的分析即可。就用两个数组以及两个变量分别存储当前各行各列上的情况即可。尤其这里又使用了类似Budget的方法，一个选手的符号只让Budegt加，另一个选手只让Budget减，那么如果budget达到n就是一个选手赢，如果达到-n,就是另一个赢。


建立两个大小为n的一维数组rows和cols,以及两个变量diagonal和antiDiagonal,分别代表对角线和反对角线,如果玩家1在某一位置(假设为(i,j))放置了一个棋子,则rows[i]和cols[j]分别加1,如果该位置在对角线或者反对角线上,则diagonal或者antiDiagonal加1;如果玩家2在某一位置(假设为(k,l))放置了一个棋子,则rows[k]和cols[l]分别减1,如果该位置在对角线或者反对角线上,则diagonal或者antiDiagonal减1;那么只有当rows中某个值或者cols中某个值或者diagonal或者antiDiagonal值为n或-n的时候,表示该行、该列、该对角线或者该反对角线上的棋子都是同一个玩家放的(值为n时代表玩家1,值为-n时代表玩家2),此时返回相应的玩家即可。


The key observation is that in order to win Tic-Tac-Toe you must have the entire row or column. Thus, we don't need to keep track of an entire n^2 board. We only need to keep a count for each row and column. If at any time a row or column matches the size of the board then that player has won.

To keep track of which player, I add one for Player1 and -1 for Player2. There are two additional variables to keep track of the count of the diagonals. Each time a player places a piece we just need to check the count of that row, column, diagonal and anti-diagonal.

*/
public class TicTacToe {
private int[] rows;
private int[] cols;
private int diagonal;
private int antiDiagonal;

/** Initialize your data structure here. */
public TicTacToe(int n) {
    rows = new int[n];
    cols = new int[n];
}

/** Player {player} makes a move at ({row}, {col}).
    @param row The row of the board.
    @param col The column of the board.
    @param player The player, can be either 1 or 2.
    @return The current winning condition, can be either:
            0: No one wins.
            1: Player 1 wins.
            2: Player 2 wins. */
    public int move(int row, int col, int player) {
        int toAdd = player == 1 ? 1 : -1;
        
        rows[row] += toAdd;
        cols[col] += toAdd;
        if (row == col)
        {
            diagonal += toAdd;
        }
        
        if (col == (cols.length - row - 1))
        {
            antiDiagonal += toAdd;
        }
        
        int size = rows.length;
        if (Math.abs(rows[row]) == size ||
            Math.abs(cols[col]) == size ||
            Math.abs(diagonal) == size  ||
            Math.abs(antiDiagonal) == size)
        {
            return player;
        }
        
        return 0;
    }
}
