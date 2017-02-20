


/*
思路： 思路：图相关算法，自己基本想到了。大体思想是考虑到题意，每个数除以自己肯定有解所以说自反的，a/b有解的话b/a也有所以说对称的，另外，a/b,b/c有解的话，a/c有解，所以说传递的，所以任意两个数之间有没有解，最主要的是传递这一块，前面的两块很容易判别。所以可以想到基本上跟图相关的问题，a/b有解的话，就是在a/b之间有2条双向边，一边是value = a/b,一边是1/value. 另外，找寻a/c是不是有解，主要是看看a,c之间有没有一条路径即可。这里面因为每个数都是直接由value表示的，所以在表示图的时候不合适用类似二维数组的形式来表示图，所以这里面用Hashmap来存储相应信息。具体代码里用了2个Hashmap，一个用来存放某个节点的邻居，另外一个存放对应到每个邻居的link的value.

针对每一个Query，就在上述的建立好的两个Hashmap里面进行DFS的找路的操作，直到遇到终点，那么就代表找到了一条路，则该Query有解。


Image a/b = k as a link between node a and b, the weight from a to b is k, the reverse link is 1/k. Query is to find a path between two nodes.

*/

 public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        HashMap<String, ArrayList<String>> pairs = new HashMap<String, ArrayList<String>>();
        HashMap<String, ArrayList<Double>> valuesPair = new HashMap<String, ArrayList<Double>>();
        for (int i = 0; i < equations.length; i++) {
            String[] equation = equations[i];
            if (!pairs.containsKey(equation[0])) {
                pairs.put(equation[0], new ArrayList<String>());
                valuesPair.put(equation[0], new ArrayList<Double>());
            }
            if (!pairs.containsKey(equation[1])) {
                pairs.put(equation[1], new ArrayList<String>());
                valuesPair.put(equation[1], new ArrayList<Double>());
            }
            pairs.get(equation[0]).add(equation[1]);
            pairs.get(equation[1]).add(equation[0]);
            valuesPair.get(equation[0]).add(values[i]);
            valuesPair.get(equation[1]).add(1/values[i]);
        }
        //到此为止，图就建立好了。
        double[] result = new double[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String[] query = queries[i];
            //对每个Query进行递归操作
            result[i] = dfs(query[0], query[1], pairs, valuesPair, new HashSet<String>(), 1.0);
            if (result[i] == 0.0) result[i] = -1.0;
        }
        return result;
    }
    
    private double dfs(String start, String end, HashMap<String, ArrayList<String>> pairs, HashMap<String, ArrayList<Double>> values, HashSet<String> set, double value) {
        if (set.contains(start)) return 0.0; //Set包含的是已经访问过的节点，所以如果当前Start已经被访问过来，直接返回0
        if (!pairs.containsKey(start)) return 0.0; //如果Start根本在图里不存在也返回0
        if (start.equals(end)) return value; //如果Start直接就是当前的End，证明现在找到最后的终点了，返回当前的Value值即可，现在Value已经是从起点到终点一直计算好的Value了。
        set.add(start); //将Start加入Set中
        //对当前的Start，拿出其邻居节点，以及对应Link上的Value List
        ArrayList<String> strList = pairs.get(start);
        ArrayList<Double> valueList = values.get(start);
        double tmp = 0.0;
        //对应Start的每个邻居，进行DFS递归操作即可。Start被设置为从邻居开始，终点End不变,同时value进行更新
        for (int i = 0; i < strList.size(); i++) {
            tmp = dfs(strList.get(i), end, pairs, values, set, value*valueList.get(i)); 
            if (tmp != 0.0) { //tmp不为0，代表找到路了，直接退出即可，因为我们只需要找到一条路。
                break;
            }
        }
        set.remove(start); ////这里是回溯操作，再对当前Start操作完后，要把其从set里面剔除掉，//其实自己试验了一下，这个Set的Add和Remove操作也可以直接放在For循环的DFS前后，也可以通过测试。
        return tmp; //这里如果返回是的0，则在最外成的函数里，将返回给用户最终结果为-1.
    }
