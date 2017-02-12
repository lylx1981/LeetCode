
    /*
    思路：本来这道题想的是用BackTracking，但是发现直接用递归的方法是最简单的。主要要考虑如下三个情况：
    
    // '1' and '8' can be anywhere
    // '0' cannot be at the beginning, except when n == 1，也就是0不能说在一个字符串的首尾
    // '6' and '9' cannot be at the center when n is odd // 如果字符串是奇数个的话，最中间的那个位置不能是6，9
    
    
    可以从这一道题来体会为什么这道题用递归更有效而不是BackTracking，是因为这道题在一个分支的一个地方，可以同时联系生成多个结果，也就是对s前后加1，6，8，9.这样分厂有效。而Backtracking的情况更适用于一个分支一条路，最后一次只应该生成一个结果的情况。
*/

//这个程序用多定义了一个m这样的方法，很巧的解决了0不能说在一个字符串的首尾这个要求
public List<String> findStrobogrammatic(int n) {
    return helper(n, n);
}

List<String> helper(int n, int m) {
    //注意下面Array.asList的方法的使用
    //Arrays.asList： Returns a fixed-size list backed by the specified array.This method also provides a convenient way to create a fixed-size list initialized to contain several elements: List<String> stooges = Arrays.asList("Larry", "Moe", "Curly"); 所以一行需要有2个地方学习，一个是Arrays.asList生成了一个字符串数组，同时这个数组又作为参数输入给new ArrayList<String>（），也就是按照这个数组，再Clone生成一个新的字符串数组。
    //问题：为什么这里不能直接就返回Arrays.asList生成的结果呢？
    
    if (n == 0) return new ArrayList<String>(Arrays.asList(""));
    if (n == 1) return new ArrayList<String>(Arrays.asList("0", "1", "8")); //这一步也同时解决了如果字符串是奇数个的话，最中间的那个位置不能是6，9
    
    List<String> list = helper(n - 2, m);
    
    List<String> res = new ArrayList<String>();
    
    for (int i = 0; i < list.size(); i++) {
        String s = list.get(i);
        
        if (n != m) res.add("0" + s + "0"); //只有n!=m的时候，可以放0.
        
        res.add("1" + s + "1");
        res.add("6" + s + "9");
        res.add("8" + s + "8");
        res.add("9" + s + "6");
    }
    
    return res;
}
        
        
/* 方法二： Backtracking，和自己的想法一样，但是貌似这道题BackTracking不是最好的方法 

Notes:

DFS and operate on chars at symmetric pos, and stop when we reach more than half of n;
use char array to save a lot of time from string concatenation.*/
public class Solution {
    public List<String> findStrobogrammatic(int n) {
        // DFS, O(5^(n/2)) time and O(n) space
        List<String> result = new ArrayList<String>();
        findStrobogrammaticHelper(n, 0, result, new char[n]); //curr就是当前处理的索引，从0开始。n-curr-1也会同时一起被处理
        return result;
    }
    
    private void findStrobogrammaticHelper(int n, int curr, List<String> result, char[] array) {
        // reaching more than half of n, add to result
        if (n % 2 == 0 && curr == n / 2 || curr > n / 2) {
            result.add(new String(array));
            return;
        }
        // '1' and '8' can be anywhere
        array[curr] = '1';
        array[n-curr-1] = '1';
        findStrobogrammaticHelper(n, curr + 1, result, array);
        //这里后面其实就是相当于做了回溯操作
        array[curr] = '8';
        array[n-curr-1] = '8';
        findStrobogrammaticHelper(n, curr + 1, result, array);
        // '0' cannot be at the beginning, except when n == 1
        if (n == 1 || curr != 0) {
            array[curr] = '0';
            array[n-curr-1] = '0'; 
            findStrobogrammaticHelper(n, curr + 1, result, array);
        }
        // '6' and '9' cannot be at the center when n is odd
        if (!(n % 2 == 1 && curr == n / 2)) {
            array[curr] = '6';
            array[n-curr-1] = '9';
            findStrobogrammaticHelper(n, curr + 1, result, array);
            array[curr] = '9';
            array[n-curr-1] = '6';
            findStrobogrammaticHelper(n, curr + 1, result, array);
        }
    }
}
