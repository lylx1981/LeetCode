public class Solution {
//思路： 很简单的题，只对于一个舰船出现的第一个X位进行记录即可。从board[0][0]开始判断整个矩阵，对于每个出现的X,如果它左边，以及上边不是X，那么它就是一个新的舰船的第一次检查的X，此时计数器+1.

//Going over all cells, we can count only those that are the "first" cell of the battleship. First cell will be defined as the most top-left cell. We can check for first cells by only counting cells that do not have an 'X' to the left and do not have an 'X' above them.


    public int countBattleships(char[][] board) {
        int m = board.length;
        if (m==0) return 0;
        int n = board[0].length;
        
        int count=0;
        
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (board[i][j] == '.') continue;
                if (i > 0 && board[i-1][j] == 'X') continue;
                if (j > 0 && board[i][j-1] == 'X') continue;
                count++;
            }
        }
        
        return count;
    }
}
