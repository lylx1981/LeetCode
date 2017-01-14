public class WordSearch {

    /**
     * Backtracking.
     * For each character in board, start backtracking if the first character matches.
     * 先在原二维数组里找一个第一个匹配的点，从那里开始进行处理就行了。
     */
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0 || word == null) {
            return false;
        }
        if (word.length() == 0) {
            return true;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) { // Match the first character.
                    if (backtrack(board, i, j, word, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Backtracking.
     * Statement:
     * Given board, starting position, word, and current position in word.
     * Find whether the word is in board.
     * Recursion:
     * Check current character. If diff, return false. If same, recurse 4 neighbors with subset.
     * Base case:
     * If pos is at word's end, return true.
     * If i, j is not within the board, return false.
     * If characters are different, return false.
     * Implementation:
     * Deal with base cases.
     * Mark current position with '#' as visited.
     * Recurse the 4 adjacent grids.
     * Reset the mark.
     * Return true if one of the adjacent grid is true.
     * 注意这里用于比较两个字符的方法，不是用字符串相关的比如Equals这样的函数，而是用等号直接比较即可，
     * 比如 board[i][j] != word.charAt(pos) 因为他们是字符型数据
     */
    public boolean backtrack(char[][] board, int i, int j, String word, int pos) {
        if (word.length() == pos) {
            return true;
        }
        // Out of board or doesn't match.
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(pos)) {
            return false;
        }
        board[i][j] = '#'; // Mark as visited.
        // Search 4 neighbors.
        boolean
            res =
            backtrack(board, i - 1, j, word, pos + 1) || backtrack(board, i + 1, j, word, pos + 1) || backtrack(
                board, i, j - 1, word, pos + 1)
            || backtrack(board, i, j + 1, word, pos + 1);
        board[i][j] = word.charAt(pos);// Reset. 回溯发生在这里
        return res;
    }
   }
