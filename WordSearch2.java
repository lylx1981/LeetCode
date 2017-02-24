/*思路： 


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
