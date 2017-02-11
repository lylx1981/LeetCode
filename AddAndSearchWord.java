
/*
思路： 基本上就是和208实现Trie的题一样，就是从那个代码改过来的，主要是对于遇到.的时候的处理，这时候就对每个可能的符号进行判断即可，同时这里用了递归和BackTracking。其他的90%都一样。 
*/


public class WordDictionary {
    /*
     * Trie.
     * Create a trie in the word dictionary class.
     */

        TrieNode root = new TrieNode();

        // Adds a word into the data structure.
        public void addWord(String word) {
            if (word == null || word.length() == 0) {
                return;
            }
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (node.links[c - 'a'] == null) {
                    node.links[c - 'a'] = new TrieNode();
                }
                node = node.links[c - 'a'];
            }
            node.isEnd = true;
        }

        // Returns if the word is in the data structure. A word could
        // contain the dot character '.' to represent any one letter.
        public boolean search(String word) {
            if (word == null) {
                return false;
            }
            return searchPrefix(word, 0, root);
        }

        /**
         * Backtracking.
         * Statement: Given a word, a position, and a trie node, find whether the word is in the trie.
         * Recurrent Relation:
         * The word is in the trie if: Current character at pos match + Other characters from pos + 1 are in too.
         * Base case:
         * When subset is empty, return whether the node is end.
         * Current char can be '.' or a letter.
         * If it's not dot:
         * | Get the next node.
         * | Return next is not null && search(word, pos + 1, next).
         * <p>
         * If it's dot, how to deal with it? '.' can match any character.
         * | As long as current node has non-null link, search the rest of the prefix in trie.
         * | If one of them returns true, return true.
         * Return false.
         */
        private boolean searchPrefix(String word, int pos, TrieNode node) {
            if (pos == word.length()) {
                return node.isEnd; //已经达到Word的末尾了，直接判断当前node是不是叶子即可
            }
            if (word.charAt(pos) == '.') { //当碰见一个"."的时候，对每个可能的结果进行递归访问就可以了，如果递归调用返回的结果是True，那么就说明匹配到了，返回True即可。
                for (int i = 0; i < node.links.length; i++) {
                    //node.links[i] != null这里一定要有这个条件，因为不一定有这一位的孩子
                    //对node.links里面的每个元素都是从 pos + 1开始递归检查，所以说一种BackTracking的操作 
                    if (node.links[i] != null && searchPrefix(word, pos + 1, node.links[i])) {
                        return true;
                    }
                }
            } else {
                //如果不是".",那就直接进行递归调用即可。
                TrieNode next = node.links[word.charAt(pos) - 'a'];
                return next != null && searchPrefix(word, pos + 1, next);
            }
            return false;
        }

        class TrieNode {

            private final int R = 26;
            TrieNode[] links;
            boolean isEnd;

            public TrieNode() {
                links = new TrieNode[R];
            }
        }
    }

