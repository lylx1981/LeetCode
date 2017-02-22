/*思路：比较简单的题，就对每个气球按照End的坐标，从小到大排列，然后贪心算法即可。每次对于当前考察的气球，我就把Arrow放在它End的位置上，这样既能保证当前这个气球肯定能被Shut，同时可以有最大机会同时Shut到其他气球，因为注意当前考察的这个气球已经是所有未被考察气球里面End坐标最小的了，也就是说其他未考察的气球End坐标都比当前的大。这样，一旦确定了Arrow的位置，也就是当前考察气球的End，我就继续依次按照End从小到大的顺序判断那些还未考察的气球，如果某个气球其Start在当前Arrow左侧，那么它一并都可以由当前考察气球的Arrow 打破了，所以直接Continue循环，否则，一旦一个新的考察的气球，其Start位置已经在Arrow右侧了，也就是当前Arrow没法打破它，那么这个时候就需要一个新的Arrow，并且把新的Arrow继续放在这个新的考察气球的End位置上，继续这样下去即可。

No offense but the currently highest voted java solution is not ideal, the sorting can be adjusted so that there's no need to check again in the for loop.

Idea:
We know that eventually we have to shoot down every balloon, so for each ballon there must be an arrow whose position is between balloon[0] and balloon[1]. Given that, we can sort the array of balloons by their ending position. Then we make sure that while we take care of each balloon from the beginning, we can shoot as many following balloons as possible.

So what position should we pick? We should shoot as right as possible, because all balloons' end position is to the right of the current one. Therefore the position should be currentBalloon[1], because we still need to shoot down the current one.

This is exactly what I do in the for loop: check how many balloons I can shoot down with one shot aiming at the ending position of the current balloon. Then I skip all these balloons and start again from the next one (or the leftmost remaining one) that needs another arrow.

Example:

balloons = [[7,10], [1,5], [3,6], [2,4], [1,4]]
After sorting, it becomes:

balloons = [[2,4], [1,4], [1,5], [3,6], [7,10]]
So first of all, we shoot at position 4, we go through the array and see that all first 4 balloons can be taken care of by this single shot. Then we need another shot for one last balloon. So the result should be 2.




*/


public int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        Arrays.sort(points, (a, b) -> a[1] - b[1]); //对气球按照End排序
        int arrowPos = points[0][1];  //放在第一个气球的End位置上
        int arrowCnt = 1;
        for (int i = 1; i < points.length; i++) {
            if (arrowPos >= points[i][0]) { //说明当前Arrow一并可以将此气球打破
                continue;
            }
            arrowCnt++; //如果上面If不通过，则说明需要一个新的Arrow
            arrowPos = points[i][1]; //同时更新新的Arrow的位置
        }
        return arrowCnt;
    }
