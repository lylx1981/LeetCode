 
 /*
思路1：比较直观的DFS，针对每个可能的前缀（如果该前缀在字典里的话），对去掉前缀剩余的串继续DFS。DFS返回的是给定一个串，其所有可能的解。另外，使用Hashmap记忆之前计算过的结果以避免重复计算。 注意下面第一个代码有超时危险，并且写的不规范，同时如果字典很大的话没法Work，因为其有对字典的逐个遍历操作。


注意，应该好好看看这题，对于DFS，大字典，以及Backtracking解法的比较与讨论，观察他们之间的不同，好好体会！！
*/

//
public List<String> wordBreak(String s, Set<String> wordDict) {
    return DFS(s, wordDict, new HashMap<String, LinkedList<String>>());
}       

// DFS function returns an array including all substrings derived from s.

List<String> DFS(String s, Set<String> wordDict, HashMap<String, LinkedList<String>>map) {
    if (map.containsKey(s)) 
        return map.get(s); //如果当前s已经之前计算过其结果了，直接返回即可。
        
    LinkedList<String>res = new LinkedList<String>();     
    if (s.length() == 0) {
        res.add("");
        return res;
    }   
    //有人评论这个算法最大的问题是遍历每个Dict里面的Word，如果字典很大，肯定这里不行，所以不推荐！！
    for (String word : wordDict) {
        if (s.startsWith(word)) {
            //继续对去掉Word之后的那个串，递归进行DFS，返回结果是这个串的所有Solution，那么就把Word挂在每个Solution前面就是当前的最终结果了。
            List<String>sublist = DFS(s.substring(word.length()), wordDict, map);
            for (String sub : sublist) 
            //注意如果(sub.isEmpty()是非空的话，才需要后面补空格，否则就补一个空串“”即可。
                res.add(word + (sub.isEmpty() ? "" : " ") + sub);               
        }
    }       
    map.put(s, res); //把s对应的结果存起来，以备后面快速用，避免重复计算。
    return res;
}

/*有人评论上面的代码不规范，下面是更规范的写法，推荐看看。自己也感觉上面的代码怪怪的。下面的代码就很规范
I think the wordDict may be large. So It's better to solve this problem in a formal way. Below is my java code (derive from https://discuss.leetcode.com/topic/12997/11ms-c-solution-concise/2) hope it helps!*/

public class Solution {
    HashMap<String,List<String>> map = new HashMap<String,List<String>>();
    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res = new ArrayList<String>();
        if(s == null || s.length() == 0) {
            return res;
        }
        if(map.containsKey(s)) {
            return map.get(s);
        }
        if(wordDict.contains(s)) {
            res.add(s);
        }
        //这里对S进行遍历，来取一个在字典里的前缀，而不是对字典进行遍历，这个是最最最重要的一点改进
        for(int i = 1 ; i < s.length() ; i++) {
            String t = s.substring(i);
            if(wordDict.contains(t)) {
                List<String> temp = wordBreak(s.substring(0 , i) , wordDict);
                if(temp.size() != 0) {
                    for(int j = 0 ; j < temp.size() ; j++) {
                        res.add(temp.get(j) + " " + t);
                    }
                }
            }
        }
        map.put(s , res); //这个题也涉及到使用Hashmap里存储中间结果，好像在别的DFS题里面倒是见的不多。可能是这道题本身如果不这样做的话，就会TLE
        return res;
    }
}

/*接着这个人展示了一下Backtracking的方法是什么样的，虽然Backtracking会导致TLE，可以看出Backtracking就没有利用Hashmap存储中间结果的部分。
well, back tracking is a brute force way（BarckTracking本质上是brute force）, just like a for for loop. For every position, we have to calculate it's subproblem. So it takes O(2^n).
below is formal backtracking solution, but it get TLE.*/

public class Solution {
    public List<String> wordBreak(String s, Set<String> wordDict) {
        List<String> res = new ArrayList<String>();
        if(s == null || s.length() == 0) {
            return res;
        }
        dfshelper(res , "" , 0 , wordDict , s);
        return res;
    }
    
    protected void dfshelper(List<String> res , String path , int start , Set<String> wordDict , String s) {
        if(start == s.length()) {
            res.add(path);
            return;
        }
        for(int i = start ; i < s.length() ; i++) {
            String t = s.substring(start , i+1);
            if(wordDict.contains(t)) {
                if(i+1 != s.length()) { //看看i是不是已经是最后一个字符了，如果是的话，后面就不再加“ ”空格了
                    path += t + " ";
                    dfshelper(res , path , i+1 , wordDict , s);
                    path = path.substring(0 , path.length() - t.length() - 1); //回溯
                }else {
                    path += t;
                    dfshelper(res , path , i+1 , wordDict , s);
                    path = path.substring(0 , path.length() - t.length());    //回溯                
                }
            }
        }
    }
}

