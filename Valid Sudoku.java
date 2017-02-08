/**
 * 思路： 按照Sukudo的定义，一步步判断就行了，每一行，每一列，每个9方块 （类似Hashmap记录哪些数字出现过， Arrays.fill(visited, false);//注意每次重新开始都要重新赋值为False）。注意看一下Sukudo定义，不是每相邻的9个位置都要符号1-9的数字，也就是不是按照滑动窗口那样判断的。整个棋盘上一共只需要判断9个9方格即可，也就是i,j每次往前跳3步。另外，在对9方格处理的地方有一个很巧的是如何对9个位置进行循环，用了board[i + k/3][ j + k%3]，也就是让i, j分别以k/3,和k%3的方式增长，很好的完成了对9个方格的遍历，这个非常巧，是最重要的地方。
 */

public class Solution {
    public boolean isValidSudoku(char[][] board) {
        boolean[] visited = new boolean[9];
        
        // row
        for(int i = 0; i<9; i++){
            Arrays.fill(visited, false);//每次重新赋值为False
            for(int j = 0; j<9; j++){
                if(!process(visited, board[i][j]))
                    return false;
            }
        }
        
        //col
        for(int i = 0; i<9; i++){
            Arrays.fill(visited, false);
            for(int j = 0; j<9; j++){
                if(!process(visited, board[j][i]))
                    return false;
            }
        }
        
        // sub matrix
        for(int i = 0; i<9; i+= 3){
            for(int j = 0; j<9; j+= 3){
                Arrays.fill(visited, false);
                for(int k = 0; k<9; k++){
                    if(!process(visited, board[i + k/3][ j + k%3])) //这里是最巧的地方！！！！
                    return false;                   
                }
            }
        }
        return true;
        
    }
    
    private boolean process(boolean[] visited, char digit){
        if(digit == '.'){
            return true;
        }
        
        int num = digit - '0';
        if ( num < 1 || num > 9 || visited[num-1]){
            return false;
        }
        
        visited[num-1] = true;
        return true;
    }
}
