

/*
思路：自己直接想的不是继续用原来的回溯法，而是依赖与DP，比如DP【i】推到DP【i+1】,但是最后发现其实推不出来，因为在新增的i+1行和i+1列上如何把一个皇后放在上面完全取决于前i行i列的结果，尤其是最复杂的是对角线的判断的规则也必须成立，所以没有办法写出推演公式。自己想的简单了，以为方法就是假定在新增的一行的某个位置放一个新皇后（一列也一样），对所有i行i列的每个解， 如果新加的这个皇后造成与这个解的某个皇后在某列冲突了，那就把冲突的那个皇后放到新加的列上去，从而也就算出了推导公式。但是后来发现不行，因为对角线的关系太复杂，没办法判断是不是可行。

从N皇后2问题52可以看出，即使是求解次数而不求解具体解的形状的时候，仍然可能依然于回溯法。而以前看到的很多题是一旦只求次数的话，就不用回溯法了，这是从N queens 2这个问题又得到的一个新的理解。



复杂度
时间 O(N^2) 空间 O(N)

思路
跟I的解法一样，只不过把原本构造字符串的地方换成了计数器加一。

*/

public class Solution {
    
    Set<Integer> rowSet = new HashSet<Integer>();
    Set<Integer> diag1Set = new HashSet<Integer>();
    Set<Integer> diag2Set = new HashSet<Integer>();
    int cnt = 0;
    
    public int totalNQueens(int n) {
        helper(n, 0);
        return cnt;
    }
    
    public void helper(int n, int col){
        if(col == n){
            cnt++;
        } else {
            for(int row = 0; row < n; row++){
                int diag1 = row + col;
                int diag2 = row - col;
                if(rowSet.contains(row) || diag1Set.contains(diag1) || diag2Set.contains(diag2)){
                    continue;
                }
                rowSet.add(row);
                diag1Set.add(diag1);
                diag2Set.add(diag2);
                helper(n, col + 1);
                diag2Set.remove(diag2);
                diag1Set.remove(diag1);
                rowSet.remove(row);
            }
        }
    }
}
