
    /*
    思路：自己差不多想出来了，其实判断是不是一棵树，本质上只要判断下面任何一个成立就行了：
    
    1） Has n-1 edges and is acyclic. 一共就N-1条边且无环
    2） Has n-1 edges and is connected. 一共就N-1条边且所有点都是联通的
    
    具体的可以有各种不同的实现方法，个人比较喜欢BFS方法，但是貌似Leetcode上最受欢迎的是Union-Find算法，可以看看，很巧。

*/

/*
可以看看如下几个链接，增加一下UF的基础知识：

http://www.jianshu.com/p/b5b8d488266e
http://dongxicheng.org/structure/union-find-set/


并查集（Union Find Set），也称为不相交集数据结构（Disjointed Set Data Structure），两个名字各自概括了这一数据结构的部分特征。简单地讲，并查集维护了一列互不相交的集合S1、S2、S3、…，支持查找（find）与合并（union）两种操作。

find
找到元素所在的集合，通常返回该集合的代表元（representative）
元素a与元素b是否属于同一个集合，只要判断find（a）与find（b）是否相等
union
将两个集合合并为一个集合
将元素a与元素b所在的集合合并为一个集合，使用union（a，b）

1、  概述
并查集（Disjoint set或者Union-find set）是一种树型的数据结构，常用于处理一些不相交集合（Disjoint Sets）的合并及查询问题。
2、  基本操作
并查集是一种非常简单的数据结构，它主要涉及两个基本操作，分别为：
A． 合并两个不相交集合
B． 判断两个元素是否属于同一个集合
（1）       合并两个不相交集合（Union(x,y)）
合并操作很简单：先设置一个数组Father[x]，表示x的“父亲”的编号。那么，合并两个不相交集合的方法就是，找到其中一个集合最父亲的父亲（也就是最久远的祖先），将另外一个集合的最久远的祖先的父亲指向它。



*/
public class Solution {
    public boolean validTree(int n, int[][] edges) {
        //注意Edges的每一行都是2个元素组成的一个Pair
        // initialize n isolated islands
        int[] nums = new int[n]; //这个就用来存所有节点的父亲，
        Arrays.fill(nums, -1);
        
        // perform union find
        for (int i = 0; i < edges.length; i++) {
            int x = find(nums, edges[i][0]); //找当前edges[i][0]的所在Set的代表节点，也就是Root
            int y = find(nums, edges[i][1]); //找当前edges[i][1]的所在Set的代表节点，也就是Root
            
            // if two vertices happen to be in the same set
            // then there's a cycle
            if (x == y) return false; //如果他们的Root一样，则说明他们是在一个Set里，那么他们都可以走到Root去，从而连接起来，而又因为他们现在之间直接就是相连的，所以就出现了一个环，所以返回False
            
            // union
            nums[x] = y; //把但钱x的父亲设置为y,也就是让他们所在的Set合并，看看UF的基础知识就明白了。
        }
        
        return edges.length == n - 1; //如果前面一直没有发现环，那么最后只要判断是不是一共有N-1条就行了 。
    }
    
    int find(int nums[], int i) {
        if (nums[i] == -1) return i;
        return find(nums, nums[i]); //注意nums[i]存放的是i的父亲，所以这里会递归，最后达到i所在的Set的Root
    }
}


class Node{
    int val;
    Node parent;
    public Node(int val)
    {
        this.val = val;
    }
}

public class Solution {
   

    
    // BFS, using queue
    private boolean valid(int n, int[][] edges)
    {
        // build the graph using adjacent list
        List<Set<Integer>> graph = new ArrayList<Set<Integer>>(); //这里把Edge转换为邻接表 Ajdacent List,
        for(int i = 0; i < n; i++)
            graph.add(new HashSet<Integer>()); //对每个节点加入一个存放其邻居的HashSet
        for(int[] edge : edges)
        {   //对每条Edge，初始化Graph邻接表 Ajdacent List
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        // no cycle
        boolean[] visited = new boolean[n]; //存放当前节点是不是已经被访问过
        Queue<Integer> queue = new ArrayDeque<Integer>(); //Queue用来存放每个待考察的点
        queue.add(0);
        while(!queue.isEmpty())
        {
            int node = queue.poll();
            if(visited[node])
                return false; //如果当前考察点已经被Visited过了，说明只有有环的情况才会出现这样的结果，直接返回False
            visited[node] = true; //否则，说明当前节点还没被访问过，则现在设置其为已经访问过，继续。
            for(int neighbor : graph.get(node))
            {   //对当前节点的每个邻居，将其加入Queue，同时从他们的邻居表里将当前节点删除
                queue.offer(neighbor);
                graph.get(neighbor).remove((Integer)node);
            }
        }
        
        // fully connected
        for(boolean result : visited)
        {
            if(!result)
                return false; //观察是不是所有节点都访问过了，如果不是，证明不连通。
        }
        
        return true;
    }
    
        
    // DFS, using stack
    private boolean validDFS(int n, int[][] edges)
    {
        // build the graph using adjacent list
        List<Set<Integer>> graph = new ArrayList<Set<Integer>>();
        for(int i = 0; i < n; i++)
            graph.add(new HashSet<Integer>());
        for(int[] edge : edges)
        {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        // no cycle
        boolean[] visited = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<Integer>();
        stack.push(0);
        while(!stack.isEmpty())
        {
            int node = stack.pop();
            if(visited[node])
                return false;
            visited[node] = true;
            for(int neighbor : graph.get(node))
            {
                stack.push(neighbor);
                graph.get(neighbor).remove(node);
            }
        }
        
        // fully connected
        for(boolean result : visited)
        {
            if(!result)
                return false;
        }
        
        return true;
    }
}
