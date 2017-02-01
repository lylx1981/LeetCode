/**
 * 思路：用Hashmap。因为L里面每个Word等长，L又有Size，所以整个需要找的字符串长度也就知道了，那么接下来就在S里面从第一个字符开始挨个截取出那么长的字符串，然后按照Word的长度切开，然后判断L里面所有的Word是不是都在截取出来的这个字符串里即可，只要有一个不在，就直接退出即可。另外一种自己没有考虑的情况是L里面可能有重复的Word，所以这里用2个Hashmap，一个是对原L进行统计都有哪些Word，每个个数是多少，另外一个Hashmap来对每次截取出来的字符串进行计数后再与第一个Hashmap里面的值比较比较看是否一样。

 */


public class Solution {
    public List<Integer> findSubstring(String S, String[] L) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        HashMap<String, Integer> toFind = new HashMap<String, Integer>();
        HashMap<String, Integer> found = new HashMap<String, Integer>();
        int m = L.length, n = L[0].length();
        for (int i = 0; i < m; i ++){
            if (!toFind.containsKey(L[i])){
                toFind.put(L[i], 1);
            }
            else{
                toFind.put(L[i], toFind.get(L[i]) + 1);
            }
        }
        for (int i = 0; i <= S.length() - n * m; i ++){ //只需要查找到S.length() - n * m 即可。
            found.clear();
            int j;
            for (j = 0; j < m; j ++){
                int k = i + j * n;
                String stub = S.substring(k, k + n);
                if (!toFind.containsKey(stub)) break;//只要发现一个不再Tofind里，直接退出
                if(!found.containsKey(stub)){
                    found.put(stub, 1);
                }
                else{
                    found.put(stub, found.get(stub) + 1);
                }
                if (found.get(stub) > toFind.get(stub)) break; //只要发现一个个数超了，直接退出。
            }
            if (j == m) result.add(i);//如果顺利到达了m，则说明当前截取出来的字符串是一个解，然后加入字符串开始处索引i即可。
        }
        return result;
    }
}
