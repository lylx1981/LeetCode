public class WordLadder {
    /*
      故此题相当于图搜索问题，将 start, end, dict 中的单词看做图中的节点，节点与节点（单词与单词）可通过一步转化得到，可以转换得到的节点相当于边的两个节点，边的权重为1（都是通过1步转化）。到这里问题就比较明确了，相当于搜索从 start 到 end 两点间的最短距离，即 Dijkstra 最短路径算法。通过 BFS 和哈希表实现。 BFS其中用到了Queue，一层一层向外，然后逐次将每层碰到的节点加入Quene中
          哈希表用来存储已经访问过的节点，这样来避免路径中出现环

        具体程序是：首先将 start 入队，随后弹出该节点，比较其和 end 是否相同；再从 dict 中选出所有距离为1的单词入队，并将所有与当前节点距离为1且未访问过的节点（需要使用哈希表）入队，方便下一层遍历时使用，直至队列为空。
    */
    public int ladderLength(String start, String end, Set<String> dict) {
        if (dict == null) {
            return 0;
        }

        if (start.equals(end)) {
            return 1;
        }
        
        dict.add(start);
        dict.add(end);

        HashSet<String> hash = new HashSet<String>(); //存放已经访问过的节点
        Queue<String> queue = new LinkedList<String>(); //存放BFS中将要访问的节点
        queue.offer(start);
        hash.add(start);
        
        int length = 1;
        while(!queue.isEmpty()) {
            length++; //当上一层元素都判断完成后，现在进入了新的一层，所以路径长度+1
            int size = queue.size(); //对所有当前已经在Quene里的元素逐个求他们的距离为1的且在字典Dic里的，且没有被访问过的节点，并继续加入到Quene中
            //只会遍历到当前Queue的Size大小，即使For循环中会在Quene中继续添加下一层节点也没有关系，他们在本次循环中不会被访问到
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                for (String nextWord: getNextWords(word, dict)) { //getNextWords 重要的函数，求word在dict里距离为1的所有邻居
                    if (hash.contains(nextWord)) {
                        continue;
                    }
                    if (nextWord.equals(end)) {
                        return length;
                    }
                    
                    hash.add(nextWord);
                    queue.offer(nextWord);
                }
            }
        }
        return 0;
    }

    // replace character of a string at given index to a given character
    // return a new string
    // Helper函数，主要是对一个字符串的给定位置替换为一个给定的字符
    // 特别注意是先把字符串化为Char数组后，然后再进行替换，然后再次转化为字符串的，应该这样性能好，要记住这个方法！！ 
    private String replace(String s, int index, char c) {
        char[] chars = s.toCharArray();
        chars[index] = c;
        return new String(chars);
    }
    
    // get connections with given word.
    // for example, given word = 'hot', dict = {'hot', 'hit', 'hog'}
    // it will return ['hit', 'hog']
    // 这个函数是这个题目最有可能影响整体性能的一个函数
    //看关于这个函数的相关分析：
    /*

    首先分析给定单词word并从 dict 中选出所有距离为1 的单词。常规的思路可能是将word与 dict 中的单词逐个比较，并遍历每个字符串，返回距离为1的单词组。这种找距离为1的节点的方法复杂度为 l(length of word)×n(size of dict)×m(queue length)==O(lmn). 在 dict 较长时会效率很低. 其实根据 dict 的数据结构特点，比如查找任一元素的时间复杂度可认为是 O(1) --  dict是Set类型，Set类型的Contains是O（1）的，一位Set是一个Hash-based Set (JAVA 就是这么实现的) . 根据哈希表和单个单词长度通常不会太长这一特点，我们就可以根据给定单词构造到其距离为一的单词变体，然后查询其是否在 dict 中，这种实现的时间复杂度为 O(26(a to z)×l×m) = O(lm)O(26)=O(lm), 与 dict 长度没有太大关系，大大优化了时间复杂度。
    
    经验教训：根据给定数据结构特征选用合适的实现，遇到哈希表时多用其查找的 O(1)特性

    */


    private ArrayList<String> getNextWords(String word, Set<String> dict) {
        ArrayList<String> nextWords = new ArrayList<String>();
        for (char c = 'a'; c <= 'z'; c++) { //遍历26个字符
            for (int i = 0; i < word.length(); i++) {
                if (c == word.charAt(i)) { // 如果Word当前第i位置的字符和待考察的变量c的字符一致，那么不用管
                    continue;
                }
                String nextWord = replace(word, i, c); //将word的当前第i位置的字符替换为变量c所代表的字符值
                if (dict.contains(nextWord)) { //如果该Word在字典中，那么它就是一个需要考察的节点，如果不在字典中，忽略即可
                    nextWords.add(nextWord);
                }
            }
        }
        return nextWords;
    }
}
