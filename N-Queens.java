

/*
思路：特别注意皇后问题是没两个皇后不能在同一行同一列同时不能在对角线上（注意这里的对角线不是棋盘的2条中心对角线，而是说对于一个位置i,j, 其一条对角线指的是穿越i,j同时穿过(i-1, j)的对角线（下面代码中称作45度对角线），以及穿越i,j同时穿过(i-1,j+1)的另一条对角线--代码中叫135度对角线）！！简单的解法主要和Sudoku游戏一样，就是从每行开始往下判断，在每一行对每个可能的位置进行试验即可。同时实现前要先判定当前位置能不能放一个皇后（这里和Sudoko游戏一样，但是下面的第一个解法中这个步骤的复杂度太高，需要改进！！！！），能的话将新的Board进入递归即可。一旦到了最后一行，如果全部皇后都放完了，那么就加入结果集中。另外，每次对每个可能位置判断完成后，还要进行回溯操作，这个一定不能忘记。另外，发现对于皇后问题，Board上的两条中心对角线上决定不能放任何皇后，这个在纸上画一画就看出来了。因为一旦在一个中心对角线上放一个皇后，就不能能把n个皇后都放在棋盘上，自己可以试试。最后要注意的是，这道题没有考虑棋盘转圈的问题，也就是如果一个解可以经由转圈棋盘而获得另一个解，那么这两个解是否应该算是同一个解的情况。这道题比较简单，只要回溯遍历出来的任意解，都算作一个distiinct的解。另外，下面这个代码是C++的，自己可以用JAVA写一遍。

另外，还有另外1个版本是对在该代码上的进一步优化（重点是如何快速判断当前位置能不能放一个皇后），面试的时候应该用优化的解法，见下面！！


The eight queens puzzle is the problem of placing eight chess queens on an 8×8 chessboard so that no two queens threaten each other. Thus, a solution requires that no two queens share the same row, column, or diagonal. The eight queens puzzle is an example of the more general n queens problem of placing n non-attacking queens on an n×n chessboard, for which solutions exist for all natural numbers n with the exception of n=2 and n=3.

In this problem, we can go row by row, and in each position, we need to check if the column, the 45° diagonal and the 135° diagonal had a queen before.

Solution A: Directly check the validity of each position, 12ms:
*/

class Solution {
public:
    std::vector<std::vector<std::string> > solveNQueens(int n) {
        std::vector<std::vector<std::string> > res;
        std::vector<std::string> nQueens(n, std::string(n, '.'));
        solveNQueens(res, nQueens, 0, n);
        return res;
    }
private:
    void solveNQueens(std::vector<std::vector<std::string> > &res, std::vector<std::string> &nQueens, int row, int &n) {
        if (row == n) {
            res.push_back(nQueens);
            return;
        }
        for (int col = 0; col != n; ++col)
            if (isValid(nQueens, row, col, n)) { //如果能放一个皇后的话，那就就进入下一层递归
                nQueens[row][col] = 'Q';
                solveNQueens(res, nQueens, row + 1, n);
                nQueens[row][col] = '.';
            }
    }

   //判断一个位置能不能放皇后，也就是看迄今为止，同列，已经当前位置的两条对角线有没有皇后（不用判断同行，已经现在就是在判定当前第row行是不是能放一个皇后，所以现在假设的就是该行没有任何一个皇后还）
   //这一步在这个解法中复杂度太高，应该要简化！！！
    bool isValid(std::vector<std::string> &nQueens, int row, int col, int &n) {
        //check if the column had a queen before.
        for (int i = 0; i != row; ++i)
            if (nQueens[i][col] == 'Q')
                return false;
        //check if the 45° diagonal had a queen before.
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; --i, --j)
            if (nQueens[i][j] == 'Q')
                return false;
        //check if the 135° diagonal had a queen before.
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; --i, ++j)
            if (nQueens[i][j] == 'Q')
                return false;
        return true;
    }
};

/*  JAVA解法！！且对上面的解法进行了优化，能够快速判断一个点到底能不能放一个皇后
复杂度
时间 O(N^2) 空间 O(N)

思路
该方法的思路和暴力法一样，区别在于，之前我们判断一个皇后是否冲突，是遍历一遍当前皇后排列的列表，看每一个皇后是否冲突。这里，我们用三个集合来保存之前皇后的信息，就可以O(1)时间判断出皇后是否冲突。三个集合分别是行集合，用于存放有哪些行被占了，主对角线集合，用于存放哪个右上到左下的对角线被占了，副对角线集合，用于存放哪个左上到右下的对角线被占了。


重要！！！！：  如何唯一的判断某个点所在的主对角线和副对角线呢？我们发现，两个点的行号加列号的和相同，则两个点在同一条主对角线上（也就是前面所说的135度那条对角线）。两个点的行号减列号的差相同，则两个点再同一条副对角线上（也就是前面所说的45度的那条对角线）。-----这一句最重要！！！

注意
主对角线row + col，副对角线row - col*/

public class Solution {
    
    List<List<String>> res = new LinkedList<List<String>>();;
    Set<Integer> rowSet = new HashSet<Integer>();
    Set<Integer> diag1Set = new HashSet<Integer>();
    Set<Integer> diag2Set = new HashSet<Integer>();
    
    public List<List<String>> solveNQueens(int n) {
        helper(new LinkedList<Integer>(), n, 0);
        return res;
    }
    
    public void helper(LinkedList<Integer> tmp, int n, int col){
        if(col == n){
            List<String> one = new LinkedList<String>();
            for(Integer num : tmp){
                StringBuilder sb = new StringBuilder();
                for(int j = 0; j < num; j++){
                    sb.append('.');
                }
                sb.append('Q');
                for(int j = num + 1; j < n; j++){
                    sb.append('.');
                }
                one.add(sb.toString());
            }
            res.add(one);
        } else {
            // 对于列col，看皇后应该放在第几行
            for(int row = 0; row < n; row++){
                int diag1 = row + col;
                int diag2 = row - col;
                // 如果三条线上已经有占据的皇后了，则跳过该种摆法
                if(rowSet.contains(row) || diag1Set.contains(diag1) || diag2Set.contains(diag2)){
                    continue;
                }
                // 用回溯法递归求解
                tmp.add(row);
                rowSet.add(row);
                diag1Set.add(diag1);
                diag2Set.add(diag2);
                helper(tmp, n, col + 1);
                diag2Set.remove(diag2);
                diag1Set.remove(diag1);
                rowSet.remove(row);
                tmp.removeLast();
            }
        }
    }
}
