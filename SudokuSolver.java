/*

思路：简单的Backtrack+递归就行了。对每一个空的地方判断放1-9的每个字符，每个都试一次，每次首先判断该位置能不能放这个字符（以使得仍然满足Suduco的规则，也就是一行，一列，每个方格都不能出现重复的数字）。如果判断能放当前这个字符的话，然后进入递归，对新的Board进行求解，也就是进入递归调用即可，如果递归调用返回了True，则搞定。否则如果返回False,则回溯操作把当前位置重新设置为“.”,继续试验下一个字符。

Try 1 through 9 for each cell. The time complexity should be 9 ^ m (m represents the number of blanks to be filled in), since each blank can have 9 choices. Details see comments inside code. Let me know your suggestions.

Sorry for being late to answer the time complexity question

*/

public class Solution {
    public void solveSudoku(char[][] board) {
        if(board == null || board.length == 0)
            return;
        solve(board);
    }
    
    public boolean solve(char[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == '.'){
                    for(char c = '1'; c <= '9'; c++){//trial. Try 1 through 9
                        if(isValid(board, i, j, c)){
                            board[i][j] = c; //Put c for this cell
                            
                            if(solve(board))
                                return true; //If it's the solution return true
                            else
                                board[i][j] = '.'; //Otherwise go back
                        }
                    }
                    
                    return false;
                }
            }
        }
        return true;
    }
    //判断当前位置上能不能放c这个字符，按照Suduku规则
    private boolean isValid(char[][] board, int row, int col, char c){
        for(int i = 0; i < 9; i++) {
            if(board[i][col] != '.' && board[i][col] == c) return false; //check colume
            if(board[row][i] != '.' && board[row][i] == c) return false; //check row
            if(board[3 * (row / 3) + i / 3][ 3 * (col / 3) + i % 3] != '.' && 
board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) return false; //check 3*3 block 这个方法很好，在另外一个Suduku题目里也用了这个技巧！！
        }
        return true;
    }
}
