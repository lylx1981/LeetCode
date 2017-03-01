
/*
思路： 这道题是经典的对Union-Find的应用，要好好看看!!!同时可以看看261题对Union-Find的介绍。大体思想就是把整个计算岛屿个数转换成Union-Find问题，也就是同属于一个岛屿的位置都属于同一个Set。同时，针对当前要在某个pos上加一个1的话，就看其周围4个方向是否已经有岛了，没有的话，自己就单独成为一个新的Set，否则就将当前的1与目前邻居所在的岛进行合并。合并在Union-Find里就是对两个Set的Root节点进行一些操作，也就是把一个Set的Root变为另一个Set的孩子节点即可。

对于复杂度，基本上可以看看这一段,大体能明白lg的复杂度是怎么来的：FIND operation is proportional to the depth of the tree. If N is the number of points added, the average running time is O(logN), and a sequence of 4N operations take O(NlogN)  （4因为是对于每个要加1的position，要对其4个方向都进行考察）. If there is no balancing, the worse case could be O(N^2). 另外，可以看看这个帖子，关于复杂度更细节的介绍： https://discuss.leetcode.com/topic/29518/java-python-clear-solution-with-unionfind-class-weighting-and-path-compression

This is a basic union-find problem. Given a graph with points being added, we can at least solve:

How many islands in total?
Which island is pointA belonging to?
Are pointA and pointB connected?
The idea is simple. To represent a list of islands, we use trees. i.e., a list of roots. This helps us find the identifier of an island faster. If roots[c] = p means the parent of node c is p, we can climb up the parent chain to find out the identifier of an island, i.e., which island this point belongs to:

Do root[root[roots[c]]]... until root[c] == c; 这里也就是说可以一直从c一直追溯到当前Set的真正的根节点，这个节点就是这个Set的representive

另外，把二维数组变为一维的数组，可以直接用下面的转换
To transform the two dimension problem into the classic UF, perform a linear mapping:

int id = n * x + y; //因为一共有n列，所以（x,y）转换成一维坐标就是x行（从第0行开始到x-1行），每行n列，然后再加上第x行的y （id也是从0开始的，比如[x=0,y=0], 计算出来对应id=0）。

Initially assume every cell are in non-island set {-1}. When point A is added, we create a new root, i.e., a new island. Then, check if any of its 4 neighbors belong to the same island. If not, union the neighbor by setting the root to be the same. Remember to skip non-island cells.

UNION operation is only changing the root parent so the running time is O(1).

FIND operation is proportional to the depth of the tree. If N is the number of points added, the average running time is O(logN), and a sequence of 4N operations take O(NlogN). If there is no balancing, the worse case could be O(N^2).

Remember that one island could have different roots[node] value for each node. Because roots[node] is the parent of the node, not the highest root of the island. To find the actually root, we have to climb up the tree by calling findIsland function.

Here I've attached my solution. There can be at least two improvements: union by rank & path compression. However I suggest first finish the basis, then discuss the improvements.

*/
int[][] dirs = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

public List<Integer> numIslands2(int m, int n, int[][] positions) {
    List<Integer> result = new ArrayList<>();
    if(m <= 0 || n <= 0) return result;

    int count = 0;                      // number of islands
    int[] roots = new int[m * n];       // one island = one tree 一共有m*n个点，对每个点记录他们的Root，这里Root是指的是其Parent
    Arrays.fill(roots, -1);     // //注意这里先把每个人的Root默认设置为-1               

    for(int[] p : positions) {
        int root = n * p[0] + p[1];     // assume new point is isolated island //把每个position都转化为1维坐标int id = n * x + y; 可以从这里可以看出来，对于1维坐标也是从0开始的
        //其实当前root这个变量既代表新加的这个位置，又代表这个位置对应的parent，也就是其Root
        roots[root] = root;             // add new island
        count++; //因为生成了一个新岛，先让count++ (当然后面马上可能会面临又减回去)

        for(int[] dir : dirs) {
            int x = p[0] + dir[0]; 
            int y = p[1] + dir[1];
            int nb = n * x + y;
            if(x < 0 || x >= m || y < 0 || y >= n || roots[nb] == -1) continue; //注意这里对roots[nb] == -1的邻居节点都不考虑
            
            int rootNb = findIsland(roots, nb);
            if(root != rootNb) {        // if neighbor is in another island
                roots[root] = rootNb;   // union two islands 把当前root的parent（其实就是它自己）设置为rootNb
                root = rootNb;          // current tree root = joined tree root 这个时候把root也更新为rootNb，从现在开始root就不再代表新加的位置了（也就是n * p[0] + p[1]了），而是代表新加位置的所在Set的根节点也就是那个representive了。因为在剩余的for(int[] dir : dirs)这个循环判断中，不再需要新加的位置的信息了，而只需要其所在Set的根节点了（比如比较Set之间关系，并进行合并）。
                count--; //因为发生了合并，所以count--               
            }
        }

        result.add(count); //没加一个Postion，就将当前Count加入到Result一次。
    }
    return result;
}

//从一个id，向上一直寻找其所在Set的那个根节点，也就是set的representive
public int findIsland(int[] roots, int id) {
    while(id != roots[id]) id = roots[id];
    roots[id] = roots[roots[id]];   // only one line added 有人提出在这里加这一行代码，会急速提高运行速度，这里比较高深，需要看一下更细节的关于Union-Find算法的讨论，具体可以见这个Slides： https://www.cs.princeton.edu/~rs/AlgsDS07/01UnionFind.pdf
    return id;
}
