    /*
    大体的思路是用信息编码的方式不仅记录当前状态，同时记录当前状态和下个状态
            
            To solve it in place, we use 2 bits to store 2 states:
            //用两个位来存储当前状态和下一个状态的所有可能，2bits已经足够了，因为总共只可能有四种可能
            [2nd bit, 1st bit] = [next state, current state] 高位来记录下一个状态，低位来记录当前状态
            
            - 00  dead (next) <- dead (current) 00 就代表当前状态是0，下一个状态还是0
            - 01  dead (next) <- live (current)  
            - 10  live (next) <- dead (current)  
            - 11  live (next) <- live (current) 
            
            In the beginning, every cell is either 00 or 01. 也就是说默认设置高位都为0，也就是下一个状态都默认是0
            
            Let's count # of neighbors from 1st state and set 2nd state bit. 接下来通过当前状态来计算下一个状态
            
            In the end, delete every cell's 1st state by doing >> 1. 
            最后只要右移一位即是当前状态了，因为低位是当前状态，因为右移而没有了
            
            对每一个Cell，检查它们邻居各自的低位，也就是当前状态，计算完成后，再把当前cell的高位设置一下
            For each cell's 1st bit, check the 8 pixels around itself, and set the cell's 2nd bit.
            
            Transition 01 -> 11: when board == 1 and lives >= 2 && lives <= 3. 
            上面刚开始是01， 是因为当前低位本来就是1，代表当前状态，而高位是在初始化的时候默认是0
            Transition 00 -> 10: when board == 0 and lives == 3.
            
            
            To get the current state, simply do
            
            board[i][j] & 1 也就是直接与1进行&操作，直接就得到了当前状态
            To get the next state, simply do
            
            board[i][j] >> 1 也就是直接向右移一位，直接就得到了高位的下一个状态
            Hope this helps!
    */        
        
    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0) return;
        int m = board.length, n = board[0].length;
    
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int lives = liveNeighbors(board, m, n, i, j);
                 // Since every 2nd state is by default dead, no need to consider transition 01 -> 00.
                 //也就是说如果对于下一个状态要转变成0的规则，不用考虑
                // In the beginning, every 2nd bit is 0;
                // So we only need to care about when will the 2nd bit become 1.
                if (board[i][j] == 1 && lives >= 2 && lives <= 3) {  
                    //3的二进制代码就是11
                    board[i][j] = 3; // Make the 2nd bit 1: 01 ---> 11 
                }
                if (board[i][j] == 0 && lives == 3) {
                    // 2的二进制代码就是10
                    board[i][j] = 2; // Make the 2nd bit 1: 00 ---> 10
                }
            }
        }
    
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //board[i][j]右移一位马上就得到了下一个状态。
                board[i][j] >>= 1;  // Get the 2nd state.
            }
        }
    }

    // 这个函数计算了对于给定Cell，坐标是x,y, 它周围的活的细胞的个数
    public int liveNeighbors(int[][] board, int m, int n, int i, int j) {
        int lives = 0; //计算周围一共有多少个活的，是具体用每个cell的低位来计算的
        for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
            for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
                lives += board[x][y] & 1; //board[x][y]与1的&操作就得到了它当前的状态
            }
        }
        lives -= board[i][j] & 1; //因为上面计算也把x,j自己也算进去了，所以要把它贡献的值去除掉。
        return lives;
    }
