/*思路： 这道题比较难，很多人说是Google的题。刚开始觉得很难，但是下面的这个题解非常好，仔细看很容易看明白。主要是在用Trie来快速查找具有某个Prefix的单词有哪些，这个是主要的技巧，其他的就是利用了一些传统的Backtracking的技术。另外，如果生成一个Trie的代码要好好理解理解，要自己会生成Trie。

主要流程就是对每个Word进行遍历 ，假设其实第一行出现的那个Word，然后用BackTracking和递归往下判断是否能最终组成一个word Square.


My first approach is brute-force, try every possible word sequences, and use the solution of Problem 422 (https://leetcode.com/problems/valid-word-square/) to check each sequence. This solution is straightforward, but too slow (TLE).

A better approach is to check the validity of the word square while we build it.
Example: ["area","lead","wall","lady","ball"]
We know that the sequence contains 4 words because the length of each word is 4.
Every word can be the first word of the sequence, let's take "wall" for example.
Which word could be the second word? Must be a word start with "a" (therefore "area"), because it has to match the second letter of word "wall".
Which word could be the third word? Must be a word start with "le" (therefore "lead"), because it has to match the third letter of word "wall" and the third letter of word "area".
What about the last word? Must be a word start with "lad" (therefore "lady"). For the same reason above.

The picture below shows how the prefix are matched while building the sequence.

0_1476809138708_wordsquare.png (看Leetcode上有图片说明，很清晰)

In order for this to work, we need to fast retrieve all the words with a given prefix. There could be 2 ways doing this:

Using a hashtable, key is prefix, value is a list of words with that prefix.
Trie, we store a list of words with the prefix on each trie node.
The implemented below uses Trie.

*/

public class Solution {
    class TrieNode {
        List<String> startWith; //存储具有该节点所对应的前缀的那些Word(前缀的组成就是从Root节点开始一直沿着Path下来的路径上遇到的字符) 
        TrieNode[] children;

        TrieNode() {
            startWith = new ArrayList<>();
            children = new TrieNode[26];
        }
    }

    class Trie {
        TrieNode root;

        Trie(String[] words) { //对所有出现的单词生成一个对应的Trie，这个很重要的代码 
            root = new TrieNode();
            for (String w : words) {
                TrieNode cur = root;
                for (char ch : w.toCharArray()) {
                    int idx = ch - 'a';
                    if (cur.children[idx] == null)
                        cur.children[idx] = new TrieNode();
                    cur.children[idx].startWith.add(w); //这里把w将入到这个节点的startWith里，
                    cur = cur.children[idx]; //不断沿着路径继续向下，w都会出现在这些节点的StartWith里
                }
            }
        }

        List<String> findByPrefix(String prefix) { //给定一个前缀，快速找到哪些单词是以这个前缀开头的
            List<String> ans = new ArrayList<>();
            TrieNode cur = root;
            for (char ch : prefix.toCharArray()) {
                int idx = ch - 'a';
                if (cur.children[idx] == null)
                    return ans;

                cur = cur.children[idx]; //不停的沿着路径向下找，知道到前缀prefix末尾后所达到的那个Trie节点
            }
            ans.addAll(cur.startWith); //最后达到的那个Trie节点里面的StartWith 里存放的就是所需要的Word
            return ans;
        }
    }

    public List<List<String>> wordSquares(String[] words) { //整个问题的总函数从这里开始
        List<List<String>> ans = new ArrayList<>();
        if (words == null || words.length == 0)
            return ans;
        int len = words[0].length();
        Trie trie = new Trie(words); //建立Trie
        List<String> ansBuilder = new ArrayList<>();
        for (String w : words) { //对每个Word进行遍历，假设其是第一行的那个单词
            ansBuilder.add(w);
            search(len, trie, ans, ansBuilder); //这是最重要的一个函数
            ansBuilder.remove(ansBuilder.size() - 1); //注意这里不是回溯操作 ---- 这个地方是不是错了？？？？，感觉应该是ansBuilder.remove()，也就是清空当前所有元素，然后继续开始考察下一个Word，并以其为新的第一行开始。别人的代码都是这么写的。Leetcode无法OJ，因为是保密题。除非是从上面的Search返回回来的ansBuilder总是只含一个元素，也就是当前w （待验证）？？
        }

        return ans;
    }

    private void search(int len, Trie tr, List<List<String>> ans,
            List<String> ansBuilder) {
        if (ansBuilder.size() == len) {
            ans.add(new ArrayList<>(ansBuilder)); //ansBuilder.size() == len，说明找到了
            return;
        }

        int idx = ansBuilder.size(); //ansBuilder.size()说明了当前需要截取的是ansBuiilder里面每个单词的第几个字符，看图例就会明白什么意思了。
        StringBuilder prefixBuilder = new StringBuilder();
        for (String s : ansBuilder)
            prefixBuilder.append(s.charAt(idx)); //对每个ansBuilder都截取对应的idx字符，然后得到一个前缀Prefix，下一个要找的Word必须有这个Prefix
        List<String> startWith = tr.findByPrefix(prefixBuilder.toString()); //把具有该前缀的所有Word从Trie找出来
        for (String sw : startWith) {
            ansBuilder.add(sw); //对每个Candidate Word，加入AnsBuilder里，然后进入下一层递归
            search(len, tr, ans, ansBuilder);
            ansBuilder.remove(ansBuilder.size() - 1); //回溯操作，去掉之前加入的那个Word,准备实验下一个Word
        }
    }
}
