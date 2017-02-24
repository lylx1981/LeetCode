/*思路： 感觉做过机器人走路那道题的话，这道题没什么难度，最多是为了求需要的最少血，反着来，从牢房往回走，这样比较方便。health[i][j]表示的是骑士要想在[i][j]这里还是活的，最少需要多少血。*/

public int calculateMinimumHP(int[][] dungeon) {
    if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) return 0;
    
    int m = dungeon.length;
    int n = dungeon[0].length;
    
    int[][] health = new int[m][n];

    health[m - 1][n - 1] = Math.max(1 - dungeon[m - 1][n - 1], 1);//最后骑士要想活着，最少要有1滴血。注意，如果这里dungeon[m - 1][n - 1]是负值的话，1 - dungeon[m - 1][n - 1]就会比1大

    for (int i = m - 2; i >= 0; i--) {            
        health[i][n - 1] = Math.max(health[i + 1][n - 1] - dungeon[i][n - 1], 1); //因为是从最后牢房开始从后往前判断，那么这就是计算最后一行，因为最后一行直接沿着牢房往左走
    }

    for (int j = n - 2; j >= 0; j--) {
        health[m - 1][j] = Math.max(health[m - 1][j + 1] - dungeon[m - 1][j], 1);//因为是从最后牢房开始从后往前判断，那么这就是计算最后一列，因为最后一行直接沿着牢房往上走
    }

    for (int i = m - 2; i >= 0; i--) {
        for (int j = n - 2; j >= 0; j--) {
		////因为是从最后牢房开始从后往前判断，反过来走的话，只能往上和往左走，两者取最大即可。
            int down = Math.max(health[i + 1][j] - dungeon[i][j], 1); //我当前点至少需要的血的数量要么是1，要么是health[i + 1][j]- dungeon[i][j]，也就是如果要从这个点往下走，我一定要保证到走到下面那个点的时候，骑士有health[i + 1][j]这么多血。注意这里是- dungeon[i][j]，所以如果dungeon[i][j]>0,说明在当前位置上要加血的，所以health[i + 1][j] - dungeon[i][j]是一个比health[i + 1][j]更小的数。否则，是比health[i + 1][j]更大的数。总体上公式是： health[i ][j]+ dungeon[i][j] =health[i + 1][j]其中dungeon[i][j]可以正也可以负。
            int right = Math.max(health[i][j + 1] - dungeon[i][j], 1);
            health[i][j] = Math.min(right, down); //因为是求最少需要多少血，所以说取Min
        }
    }

    return health[0][0];//最后到【0】【0】就是找到的最优的需要最少多少血
}
