
/*
思路： 比较有意思的一道题，主要借助于Hashmap，但是又要配合优先级队列。另外，取决于是用递归还是非递归，又会牵扯到是使用DFS，还是Stack。所以这道题考察了很多东西，非常非常好！！！
优先级队列用来存储给定一个出发地，它的所有可以到达的终点，有了优先级队列，每次拿出的都可以说满足字典序最小的那个。基本思想就是用一个HashMap，然后刚开始对每个出发点尝试用优先级队列建立其可以到达的重点。然后就从JFK开始，要么用DFS递归，对Hashmap进行考察，要么用Stack非递归对Hashmap进行考察，把Path逐步找出来。

这道题最大重点是在后一部分，其实牵扯到如何寻找一个欧拉路径，也就是对于一个图，遍历所有边，且每条边只走一次。In graph theory, an Eulerian trail (or Eulerian path) is a trail in a graph which visits every edge exactly once. Similarly, an Eulerian circuit or Eulerian cycle is an Eulerian trail which starts and ends on the same vertex. They were first discussed by Leonhard Euler while solving the famous Seven Bridges of Königsberg problem in 1736. The problem can be stated mathematically like this:

对于代码中有关寻找欧拉路径的那部分代码，如何参考下面的说明，就知道那一段代码是如何工作的了：
一定要重点掌握！！

Explanation

First keep going forward until you get stuck. That's a good main path already. Remaining tickets form cycles which are found on the way back and get merged into that main path. By writing down the path backwards when retreating from recursion, merging the cycles into the main path is easy - the end part of the path has already been written, the start part of the path hasn't been written yet, so just write down the cycle now and then keep backwards-writing the path.

Example:

enter image description here

From JFK we first visit JFK -> A -> C -> D -> A. There we're stuck, so we write down A as the end of the route and retreat back to D. There we see the unused ticket to B and follow it: D -> B -> C -> JFK -> D. Then we're stuck again, retreat and write down the airports while doing so: Write down D before the already written A, then JFK before the D, etc. When we're back from our cycle at D, the written route is D -> B -> C -> JFK -> D -> A. Then we retreat further along the original path, prepending C, A and finally JFK to the route, ending up with the route JFK -> A -> C -> D -> B -> C -> JFK -> D -> A.
*/

public List<String> findItinerary(String[][] tickets) {
    for (String[] ticket : tickets)
        targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]); 
/*//这里k ->是JAVA固定的写法，记住就行了。From JAVA DOC： The most common usage is to construct a new object serving as an initial mapped value or memoized result, as in: map.computeIfAbsent(key, k -> new Value(f(k)));
*/
    visit("JFK");//从JFK机场开始
    return route;
}

Map<String, PriorityQueue<String>> targets = new HashMap<>();//每个城市在Map有自己的优先级队列存放自己可以到的终点
List<String> route = new LinkedList(); //存放中间计算路径。

void visit(String airport) {
    //如果当前airport在Targets存在，并且它要去的终点集合还不为空
    while(targets.containsKey(airport) && !targets.get(airport).isEmpty())
        visit(targets.get(airport).poll());//就把它对应的终点集合的最小元素拿出来（依赖于优先级队列的poll操作），然后对这个城市继续递归调用visit
    route.add(0, airport);//讲过上面的循环后，最后再把当前的airport加到route的最前面就可以了。
//注意上面的这部分其实是最重要的部分，而且牵扯到其实是找欧拉路径的代码，比较难看懂。
}

//非递归版本 Iterative version:

public List<String> findItinerary(String[][] tickets) {
    Map<String, PriorityQueue<String>> targets = new HashMap<>();
    for (String[] ticket : tickets)
        targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
    List<String> route = new LinkedList();
    Stack<String> stack = new Stack<>();
    stack.push("JFK");
    while (!stack.empty()) {
        while (targets.containsKey(stack.peek()) && !targets.get(stack.peek()).isEmpty())
            stack.push(targets.get(stack.peek()).poll());//把下一个要去的城市放入stack后，再拿出来。。。。
        route.add(0, stack.pop());
    }
    return route;
}

