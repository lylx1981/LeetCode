
/*
思路：基本和自己想的一致，主要是如何选取一种合适的数据结果来存储蛇，因为每次蛇都要移动，那么会牵扯到蛇尾巴要删除，蛇头要往前进，所以这就牵扯到又要从尾巴删除元素，又要从头加入元素，看了答案后，他选取的是JAVA的Deque，这个数据结构就是JAVA自己本身自带的支持在头尾进行插入删除操作的不错的数据结构 （From JAVA Doc: Deque Interface: A linear collection that supports element insertion and removal at both ends.）。

另外一个注意的就是针对每走一步的不同Case进行处理，比如：

1. 不吃食物，那么就简单的在蛇头增加一个新的节点，并删除老的蛇尾。
2. 吃食物，那么在蛇头增加一个新的节点就是食物的位置，老的蛇尾不用删除，因为蛇当前身体长度要加1.
3. 如果蛇头跑出界，或者蛇碰到自己身体里，那么直接就Over了。

另外，Leetcode代码用了二维坐标转换为1维坐标来存储蛇的身体。
*/


public class SnakeGame {

    //2D position info is encoded to 1D and stored as two copies 
    Set<Integer> set; // this copy is good for fast loop-up for eating body case 便于查找，是个Hashmap
    Deque<Integer> body; // this copy is good for updating tail，便于插入删除操作
    int score;
    int[][] food;
    int foodIndex;
    int width;
    int height;
    
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        set = new HashSet<>();
        //把第一个蛇的身体分别加入set，body里
        set.add(0); //intially at [0][0]
        body = new LinkedList<>();
        body.offerLast(0);
    }
    
  
    public int move(String direction) {
        //case 0: game already over: do nothing
        if (score == -1) {
            return -1;
        }
        
        // compute new head，里面下面的换算公式，把该点的二维坐标换算出来
        int rowHead = body.peekFirst() / width; //蛇头行坐标
        int colHead = body.peekFirst() % width;//蛇头列坐标
        switch (direction) {
            case "U" : rowHead--; //往上走，蛇头行坐标减1
                       break;
            case "D" : rowHead++; //往下走，蛇头行坐标加1
                       break;
            case "L" : colHead--; //往左走，蛇头列坐标减1
                       break;
            default :  colHead++; //默认是往右走
        }
        int head = rowHead * width + colHead;
        
        //case 1: out of boundary or eating body 判断是不是出界或者碰到自己身体了
        set.remove(body.peekLast()); // new head is legal to be in old tail's position, remove from set temporarily ，暂时把老蛇尾删除
        if (rowHead < 0 || rowHead == height || colHead < 0 || colHead == width || set.contains(head)) {
            return score = -1;
        }
        
        // add head for case2 and case3 加入新的蛇头
        set.add(head); 
        body.offerFirst(head);
        
        //case2: eating food, keep tail, add head 当前新的蛇头位置和当前食物位置一样，则吃食物
        if (foodIndex < food.length && rowHead == food[foodIndex][0] && colHead == food[foodIndex][1]) {
            set.add(body.peekLast()); // old tail does not change, so add it back to set，再把老的蛇尾加回去
            foodIndex++;//继续喂下一个食物
            return ++score;//加分
        }
        
        //case3: normal move, remove tail, add head
        body.pollLast(); //不吃食物简单移动，
        return score;
        
    }
}
