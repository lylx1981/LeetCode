/*思路： 
非常非常重要的一题，是对Trie这种数据结构的应用。主要的思想还是和Word search一样，从每个位置开始，进行Backtracking，但是针对当前i,j对应的一个字符C，怎么能快速判定当前它后面是应该跟哪些字符呢？这就要有一个方法来明确描述C当前是代表的哪个Word的以及哪个位置上的字符C（因为很可能目前已经在匹配的中间过程中了，并且是匹配了某一个特定的Word的一部分前缀了）。根据这样的需求，由于现在有很多Word,而且每个Word里面同一个字符还可能出现多余一次，那么这时候很明显用Trie来表示整个所有Words的结构上最合理了，那么对某个特定Word的匹配可以看作是从Root开始向下的一条路径，也就是说如果当前开始考察Board上的i,j，发现其对应的字符是某个Word的第一个字符，，那么当前在Trie里考察的节点就从Trie里面的Root节点开始，特别地，这里有点绕，由于当前在Broad【i】【i】上的字符C已经知道了，所以我们也就相应的知道了当前Trie里应该沿着Root的哪个孩子顺着找下去（也对应进入下一层递归）。之后，在下一层递归中，Trie里的当前节点就从第一次的Root也变成了刚才沿着走下去的那个孩子节点。这道题，非常巧的利用了Trie，同时也因为在Trie里是沿着Root一直往下找，那么再配合上当前Board遇见的是什么字符，也就一直找下去了，知道发现一个节点里面存了一个Word,那么就是找到了一个Word，加入结果集即可。另外，注意，有回溯操作。

  // if (i > 0) dfs(board, i - 1, j ,p, res);  以第一个DFS为例子，注意，从Board上来说，下一个要试验的位置是（i-1,j）,也就是说下一个要出现的字符是谁已经定了（比如说A）。而在Trie方面，根据当前Path的情况，现在到达了P节点，如果P节点中如果没有以A为孩子继续下去的路径，那么这个DFS就直接返回了，表示走不通。所以，这里要注意的是由Boarad上来主导怎么走从而确定下一个字符是谁，而接着从Trie上再来判断是否可行（也就是是否有可行的分支让Path继续走下去）。而不是从Trie来做决定在某个节点走哪个分支从而再来影响Board的走法。
  
好好看看代码是怎么生成一个Trie的（注意在代码中，只有最后的叶子节点才存储一个Word，中间节点并没有存储任何前缀，这是这个代码实现的一个区别，自己要注意）

Intuitively, start from every cell and try to build a word in the dictionary. Backtracking (dfs) is the powerful way to exhaust every possible ways. Apparently, we need to do pruning when current character is not in any word.

How do we instantly know the current character is invalid? HashMap?
How do we instantly know what's the next valid character? LinkedList?
But the next character can be chosen from a list of characters. "Mutil-LinkedList"?
Combing them, Trie is the natural choice. Notice that:

TrieNode is all we need. search and startsWith are useless.
No need to store character at TrieNode. c.next[i] != null is enough.
Never use c1 + c2 + c3. Use StringBuilder.
No need to use O(n^2) extra space visited[m][n].
No need to use StringBuilder. Storing word itself at leaf node is enough.
No need to use HashSet to de-duplicate. Use "one time search" trie.


。*/

public List<String> findWords(char[][] board, String[] words) {
    List<String> res = new ArrayList<>();
    TrieNode root = buildTrie(words);
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            dfs (board, i, j, root, res);
        }
    }
    return res;
}

//Trie相关的最主要的是P这个Trienode，其很好的记录了当前到底走在哪个路径上，正在对哪些Cadidate Word进行匹配，以及匹配了多少前缀了。
public void dfs(char[][] board, int i, int j, TrieNode p, List<String> res) {
    char c = board[i][j];
    if (c == '#' || p.next[c - 'a'] == null) return; //p.next[c - 'a'] == null，表示从当前P节点开始，没有发现能够按照C字符走下去的路，也就是匹配不上，那么直接返回即可
    p = p.next[c - 'a']; //这一步很重要！！！！！就是把当前的在Trie里面的对应的要考察的节点从当前节点P，挪向其next[c - 'a']对应的孩子节点。
    if (p.word != null) {   // found one
        res.add(p.word);
        p.word = null;     // de-duplicate,直接把这个节点的Word分量删除即可
    }

    board[i][j] = '#';
      // if (i > 0) dfs(board, i - 1, j ,p, res);  以第一个DFS为例子，注意，从Board上来说，下一个要试验的位置是（i-1,j）,也就是说下一个要出现的字符是谁已经定了（比如说A）。而在Trie方面，根据当前Path的情况，现在到达了P节点，如果P节点中如果没有以A为孩子继续下去的路径，那么这个DFS就直接返回了，表示走不通。所以，这里要注意的是由Boarad上来主导怎么走从而确定下一个字符是谁，而接着从Trie上再来判断是否可行（也就是是否有可行的分支让Path继续走下去）。而不是从Trie来做决定在某个节点走哪个分支从而再来影响Board的走法。
    if (i > 0) dfs(board, i - 1, j ,p, res); 
    if (j > 0) dfs(board, i, j - 1, p, res);
    if (i < board.length - 1) dfs(board, i + 1, j, p, res); 
    if (j < board[0].length - 1) dfs(board, i, j + 1, p, res); 
    board[i][j] = c; //回溯操作
}

public TrieNode buildTrie(String[] words) {
    TrieNode root = new TrieNode();
    for (String w : words) {
        TrieNode p = root; //对于每个Word,每次都从Root节点开始建关于这个Word的一条Path
        for (char c : w.toCharArray()) {
            int i = c - 'a';
            if (p.next[i] == null) p.next[i] = new TrieNode(); //如果p.next[i] 不空，说明之前已经建立过来，直接用即可，也就是和别的Word有相同前缀
            p = p.next[i]; //一直往下建节点，建路
       }
       p.word = w;//只有最后的那个叶子节点才放Word
    }
    return root;
}

class TrieNode {
    TrieNode[] next = new TrieNode[26]; //一个TrieNode节点在这道题里可以有26个孩子节点
    String word;
}
