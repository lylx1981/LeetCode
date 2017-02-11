
/*
思路： 和自己想的一样，要首先定义节点的数据结构，因为每个节点都可能有26个子孩子，而且每次查找每个子孩子的时候速度还要快，所以这里每个节点里都用了一个26大小的数组直接存可能子孩子的link (不知道这里用Hashmap是不是也可以).

另外，要学习的一点是对于节点的数据结构，同时要尽可能的也写出其各种API以便其他程序调用(比如真正要实现的search,insert等等). 这种解耦设计API的方法非常非常重要，要牢记！！！



下面是Trie和其他一些树的一些基本知识，要掌握
From Wikipedia, the free encyclopedia

In computer science, a trie, also called digital tree and sometimes radix tree or prefix tree (as they can be searched by prefixes), is a kind of search tree—an ordered tree data structure that is used to store a dynamic set or associative array where the keys are usually strings. Unlike a binary search tree, no node in the tree stores the key associated with that node; instead, its position in the tree defines the key with which it is associated. All the descendants of a node have a common prefix of the string associated with that node, and the root is associated with the empty string.

Though tries are usually keyed by character strings, they need not be. The same algorithms can be adapted to serve similar functions of ordered lists of any construct, e.g. permutations on a list of digits or shapes. In particular, a bitwise trie is keyed on the individual bits making up any fixed-length binary datum, such as an integer or memory address.

Trie树，又叫做前缀树或者是字典树，是一种有序的树。从空字符串的根开始，往下遍历到某个节点，确定了对应的字符串，也就是说，任意一个节点的所有子孙都具备相同的前缀。每一棵Trie树都可以被看做是一个简单版的确定有限状态的自动机（DFA，deterministic finite automaton），也就是说，对于一个任意给定的属于该自动机的状态(①)和一个属于该自动机字母表的字符(②)，都可以根据给定的转移函数(③)转到下一个状态去。其中：

① 对于Trie树中的每一个节点都确定了一个自动机的状态；
② 给定一个属于该自动机字母表的字符，在图中可以看到根据不同的字符形成的分支；
③ 从当前节点进入下一层次节点的过程经过状态转移函数得出。
一个非常常见的应用就是搜索提示，在搜索框中输入搜索信息的前缀，如“乌鲁”，提示“乌鲁木齐”；再有就是输入法的联想功能，也是一样的道理。



Radix tree


In computer science, a radix tree (also radix trie or compact prefix tree) is a data structure that represents a space-optimized trie in which each node that is the only child is merged with its parent. The result is that the number of children of every internal node is at least the radix r of the radix tree, where r is a positive integer and a power x of 2, having x ≥ 1. Unlike in regular tries, edges can be labeled with sequences of elements as well as single elements（也就是说对于基数树的每个节点，如果该节点是唯一的儿子的话，就和父节点合并。）. This makes radix trees much more efficient for small sets (especially if the strings are long) and for sets of strings that share long prefixes.

http://www.cnblogs.com/maybe2030/p/4732377.html [Data Structure] 数据结构中各种树
http://blog.csdn.net/whoamiyang/article/details/51926985 浅谈AVL树,红黑树,B树,B+树原理及应用

AVL树：平衡二叉树，一般是用平衡因子差值决定并通过旋转来实现，左右子树树高差不超过1，那么和红黑树比较它是严格的平衡二叉树，平衡条件非常严格（树高差只有1），只要插入或删除不满足上面的条件就要通过旋转来保持平衡。由于旋转是非常耗费时间的。我们可以推出AVL树适合用于插入删除次数比较少，但查找多的情况。

红黑树：平衡二叉树，通过对任何一条从根到叶子的简单路径上各个节点的颜色进行约束，确保没有一条路径会比其他路径长2倍，因而是近似平衡的。所以相对于严格要求平衡的AVL树来说，它的旋转保持平衡次数较少。用于搜索时，插入删除次数多的情况下我们就用红黑树来取代AVL。（现在部分场景使用跳表来替换红黑树，可搜索“为啥 redis 使用跳表(skiplist)而不是使用 red-black？”）

B树，B+树：它们特点是一样的，是多路查找树，一般用于数据库系统中，为什么，因为它们分支多层数少呗，都知道磁盘IO是非常耗时的，而像大量数据存储在磁盘中所以我们要有效的减少磁盘IO次数避免磁盘频繁的查找。B+树是B树的变种树，有n棵子树的节点中含有n个关键字，每个关键字不保存数据，只用来索引，数据都保存在叶子节点。是为文件系统而生的。B和B+主要用在文件系统以及数据库中做索引等，比如Mysql：B-Tree Index in MySql

Trie树：又名单词查找树，一种树形结构，常用来操作字符串。它是不同字符串的相同前缀只保存一份。相对直接保存字符串肯定是节省空间的，但是它保存大量字符串时会很耗费内存（是内存）。类似的有前缀树(prefix tree)，后缀树(suffix tree)，radix tree(patricia tree, compact prefix tree)，crit-bit tree（解决耗费内存问题），以及前面说的double array trie。简单的补充下我了解应用前缀树：字符串快速检索，字符串排序，最长公共前缀，自动匹配前缀显示后缀。后缀树：查找字符串s1在s2中，字符串s1在s2中出现的次数，字符串s1,s2最长公共部分，最长回文串。radix tree：linux内核，nginx。 IP选路 也是前缀匹配，一定程度会用到trie。 

*/

   

public class Trie {
        
    private class TrieNode {

        private final int R = 26;
        private TrieNode[] links; //存放每个可能的孩子
        private boolean end; //标记当前节点是不是一个End叶子

        // Initialize your data structure here.
        public TrieNode() {
            links = new TrieNode[R];
        }
        //下面这些就是自己定义的TrieNode自己的一些基本操作
        public boolean hasLink(char ch) {
            return links[ch - 'a'] != null;
        }

        public TrieNode getNode(char ch) {
            return links[ch - 'a'];
        }

        public void putNode(char ch) {
            links[ch - 'a'] = new TrieNode();
        }

        public void setEnd() {
            end = true;
        }

        public boolean isEnd() {
            return end;
        }
    }
    
    private TrieNode root; //Trie的根节点 
    
    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        if (word == null) {
            return;
        }
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!node.hasLink(c)) {
                node.putNode(c);
            }
            node = node.getNode(c);
        }
        node.setEnd(); //因为当前节点已经不再是End了，要把其对应位改成True
    }

    // search a prefix or whole key in trie and
    // returns the node where search ends
    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!node.hasLink(c)) {
                return null;
            }
            node = node.getNode(c);
        }
        return node;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        if (word == null) {
            return false;
        }
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd(); // Note to check node.isEnd(). 当找到匹配，且现在这个Node是个叶子节点，才代表真的存在这个Word，这一点很重要。
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if (prefix == null) {
            return false;
        }
        TrieNode node = searchPrefix(prefix);
        return node != null;
        }   
}

