/**
 * 思路上和Word Ladder是比较类似的，但是因为是要求出所有路径，仅仅保存路径长度是不够的，而且这里还有更多的问题，那就是为了得到所有路径，不是每个结点访问一次就可以标记为visited了，因为有些访问过的结点也会是别的路径上的结点，所以访问的集合要进行回溯（也就是标记回未访问）。所以时间上不再是一次广度优先搜索的复杂度了，取决于结果路径的数量。
 * 本题可以分两步求解，第一步使用BFS求解出所有的最短的字梯解集合，然后在解集合中使用DFS搜索问题的解。先BFS生成找到end时的生成树，标记出每个单词所在的层数。然后从目标用DFS往回找，过了大数据。
 * 
 */

public class Solution {
    public List<List<String>> findLadders(String start, String end,
            Set<String> dict) {
        List<List<String>> ladders = new ArrayList<List<String>>(); //ladders记录所有可能路径
        Map<String, List<String>> map = new HashMap<String, List<String>>(); //存储一个节点的所有邻居
        Map<String, Integer> distance = new HashMap<String, Integer>();  //记录每个单词所在的层数  

        dict.add(start);
        dict.add(end);
 
        bfs(map, distance, start, end, dict); //bfs生成distance
        
        List<String> path = new ArrayList<String>();
        
        dfs(ladders, path, end, start, distance, map);

        return ladders;
    }

    //从目标单词往回找开始单词，记录所有路径  
    void dfs(List<List<String>> ladders, List<String> path, String crt,
            String start, Map<String, Integer> distance,
            Map<String, List<String>> map) {
        path.add(crt);
        if (crt.equals(start)) { //找到了起始点，那么现在把整个path反转后的值就是一个解
            Collections.reverse(path);
            ladders.add(new ArrayList<String>(path)); //江当前path复制一个新的，然后加入结果集合中
            Collections.reverse(path); //记得翻转完后还要反转会爱，因为path后面还要继续接着用
        } else {
            for (String next : map.get(crt)) { //检查crt的每个邻居
                //对于map.get(crt)里面的每个邻居next，如果next层数比当前crt层数少1，则从这个邻居开始，递归调用DFS
                //发现把下面distance.containsKey(next) 删除后，程序依然可以通过Leetcode以及Lintcode
                //自己也感觉这一句好像很多余
                if (distance.containsKey(next) && distance.get(crt) == distance.get(next) + 1) { 
                    dfs(ladders, path, next, start, distance, map); //递归调用，现在从next元素开始继续往前找
                }
            }           
        }
        path.remove(path.size() - 1); //取出path的最后一个元素，也就是crt，这里其实就是回溯操作
    }

    void bfs(Map<String, List<String>> map, Map<String, Integer> distance,
            String start, String end, Set<String> dict) {
        Queue<String> q = new LinkedList<String>(); //用来做BFS用
        q.offer(start); //将start加入queue中，开始判断
        distance.put(start, 0); //记录Start为第0层
        for (String s : dict) {
            map.put(s, new ArrayList<String>()); //初始化map
        }
        
        while (!q.isEmpty()) {
            String crt = q.poll(); //取出Q里面的一个元素

            List<String> nextList = expand(crt, dict); //其实这就是World Latter I 里面的招距离为1的邻居的那个函数
            for (String next : nextList) {
                map.get(next).add(crt); //将crt加入next对应在map的list中，也就是记录下来crt是next的一个邻居
                if (!distance.containsKey(next)) { //如果当前next对应的这个节点还没有被确定在第几层的话，就确定其位置
                //否则的话，代表之前已经考察过这个点了
                    distance.put(next, distance.get(crt) + 1);
                    q.offer(next);
                }
            }
        }
    }

    List<String> expand(String crt, Set<String> dict) {
        List<String> expansion = new ArrayList<String>();

        for (int i = 0; i < crt.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (ch != crt.charAt(i)) {
                    String expanded = crt.substring(0, i) + ch
                            + crt.substring(i + 1);
                    if (dict.contains(expanded)) {
                        expansion.add(expanded);
                    }
                }
            }
        }

        return expansion;
    }
}
