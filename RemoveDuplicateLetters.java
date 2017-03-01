/*思路： 问题有点绕，就从最开始来举例。寻找每个字符最后一次出现的位置），然后从中选取最小的一个（比如位置A，那么结果集中的第一个要选择的字符必定是从0(作为当前的begin)位置开始到A位置里最小的那个字母(作为当前的end)。因为要么是A这个位置的字母，因为这是这个字母最后一次出现，后面再也不会出现了，要么从0到A位置上如果有比A位置小的字母的话，那就是出现的最小的字母(比如其在位置C)。选择好这个字母后，就从选择的这个位置之后的位置继续开始考察（也就是C+1），作为新的begin（之前是0）。同时，如果位置A上的字符在这个过程中不是最小的，那么下一次继续以A为结束判断的位置（也就是当前位置A还作为End），进入下一轮判断。否则如果当前最小字符就出现在位置A，则要继续选择下一个出去A位置外最后一次出现的位置中最小的一个作为新的End。另外，对于每次挑选出来的字符，在以后的判断中一概不再对其考虑。


The basic idea is to find out the smallest result letter by letter (one letter at a time). Here is the thinking process for input "cbacdcbc":

1.find out the last appeared position for each letter;
c - 7
b - 6
a - 2
d - 4
2. find out the smallest index from the map in step 1 (a - 2);
3. the first letter in the final result must be the smallest letter from index 0 to index 2;
4. repeat step 2 to 3 to find out remaining letters.
the smallest letter from index 0 to index 2: a
the smallest letter from index 3 to index 4: c （注意当前d-4并不从上面的list删除），所以现在End还是4，但是因为现在选择的c的坐标是3，所以新的begin的位置就是3+1=4，这也就是为什么下一轮考察的begin,end都是4
the smallest letter from index 4 to index 4: d 
the smallest letter from index 5 to index 6: b
so the result is "acdb"

Notes:

after one letter is determined in step 3, it need to be removed from the "last appeared position map", and the same letter should be ignored in the following steps
in step 3, the beginning index of the search range should be the index of previous determined letter plus one

*/

public class Solution {

    public String removeDuplicateLetters(String s) {
        if (s == null || s.length() <= 1) return s;

        Map<Character, Integer> lastPosMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            lastPosMap.put(s.charAt(i), i); //这样一轮循环下来，就是每个字符最晚出现的位置了。
        }

        char[] result = new char[lastPosMap.size()];
        int begin = 0, end = findMinLastPos(lastPosMap);////寻找每个字符最后一次出现的位置中最小的那个

        for (int i = 0; i < result.length; i++) {
            char minChar = 'z' + 1;
            for (int k = begin; k <= end; k++) {
                if (lastPosMap.containsKey(s.charAt(k)) && s.charAt(k) < minChar) { //lastPosMap.containsKey(s.charAt(k)表示这些都还是考察范围内的字符
                    minChar = s.charAt(k); //选择最小字符存在minChar里
                    begin = k+1; //下一次从k+1开始，这是新的Begin的位置
                }
            }

            result[i] = minChar;//选择出来的minChar其实就是本次跳出来的一个字符，加入结果集中
            if (i == result.length-1) break;

            lastPosMap.remove(minChar);//把当前的minChar从lastPosMap删除，以后不再继续考察了。对于每次挑选出来的字符，在接下来的判断中一概不再对其考虑

            if (s.charAt(end) == minChar) end = findMinLastPos(lastPosMap); //如果当前选择的就是当前end上的这个字符，那么就需要更新End，新的End的位置
        }

        return new String(result);
    }
	//寻找每个字符最后一次出现的位置中最小的那个
    private int findMinLastPos(Map<Character, Integer> lastPosMap) {
        if (lastPosMap == null || lastPosMap.isEmpty()) return -1;
        int minLastPos = Integer.MAX_VALUE;
        for (int lastPos : lastPosMap.values()) {
             minLastPos = Math.min(minLastPos, lastPos);
        }
        return minLastPos;
    }

}

/*下面这个是Leetcode评论最多的算法，但是看不懂，不是很清楚为什么这么做。
Given the string s, the greedy choice (i.e., the leftmost letter in the answer) is the smallest s[i], s.t.
the suffix s[i .. ] contains all the unique letters. (Note that, when there are more than one smallest s[i]'s, we choose the leftmost one. Why? Simply consider the example: "abcacb".)

After determining the greedy choice s[i], we get a new string s' from s by

removing all letters to the left of s[i],
removing all s[i]'s from s.
We then recursively solve the problem w.r.t. s'.

The runtime is O(26 * n) = O(n).*/



public class Solution {
    public String removeDuplicateLetters(String s) {
        int[] cnt = new int[26];
        int pos = 0; // the position for the smallest s[i]
        for (int i = 0; i < s.length(); i++) cnt[s.charAt(i) - 'a']++;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < s.charAt(pos)) pos = i;
            if (--cnt[s.charAt(i) - 'a'] == 0) break;
        }
        return s.length() == 0 ? "" : s.charAt(pos) + removeDuplicateLetters(s.substring(pos + 1).replaceAll("" + s.charAt(pos), ""));
    }
}

