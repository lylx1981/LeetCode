    /* 基本想法是Backtracking，另外还有Game Threory的方法，特别难。这个题不好理解，比较绕。
    
    Backtracking的思路是，对第一个Palyer开始，每个可能的Move进行判断，如果该Move所导致的那个新的字符串对于第二个Palyer来说是一种必败的格局，那么我只要第一步就选择这个动作就好了，因为我一旦选择了这个动作，所导致的那个接下来的格局必定是让第二个Palyer败的格局，无论Palyer2怎么应对（同样的，递归操作会继续向深处判断）。
    
    也就是说对于第一个Palyer来说，虽然我有很多种可能来选择下一步，但是我只选择一种特殊的下一步：也就是我这一步一走导致的格局不论Player如何应对，它都必定输。这样的话，就一定能保证第一个Palyer肯定是赢的了，这也是解释了为什么Palyer2可能有各种不同的走法，如何解决这种不确定性。根源在于Palyer1要走的那一步，是需要导致新的格局是不管palyer2怎么走，都必定会输的那个格局的走法！！
    
    
      At first glance, backtracking seems to be the only feasible solution to this problem. We can basically try every possible move for the first player (Let's call him 1P from now on), and recursively check if the second player 2P has any chance to win. If 2P is guaranteed to lose, then we know the current move 1P takes must be the winning move. The naive implementation is actually very simple:
      
      Now let's check the time complexity: Suppose originally the board of size N contains only '+' signs, then roughly we have:

T(N) = (N-2) * T(N-2) = (N-2) * (N-4) * T(N-4) ... = (N-2) * (N-4) * (N-6) * ... ~ O(N!!)

The canWin() returns false if the current player is "guarantee a lose", but not "cannot guarantee a win", because it has tried every possible move to make a win but cannot find it.
    */        
        



int len;
string ss;
bool canWin(string s) {
    len = s.size();
    ss = s;
    return canWin(); //canWin()的True的意思是当前Palyer对于当前的字符串ss“保证确定能赢”，而canWin()返回False的意思是当前Palyer以及当前字符串ss保证一定会输的意思！！
}
bool canWin() {
    for (int is = 0; is <= len-2; ++is) {
        if (ss[is] == '+' && ss[is+1] == '+') {
            ss[is] = '-'; ss[is+1] = '-';
            bool wins = !canWin(); //对于第一次循环，现在调用再次CanWin（）判断的是把第二个Palyer作为当前考察Player以及当前更新后的ss，它能不能保证必定输的意思，如果答案是False，就代表第二个Palyer必定会输，这也就是代表第一个Palyer是确定能赢的了。
            ss[is] = '+'; ss[is+1] = '+'; //回溯
            if (wins) return true; 
        }
    }
    return false; //Canwin（）默认就是False。如果从来不进循环的话，默认就是False
} 

/*由于Backtracking的方法太慢了，那么就用Memorization的技巧存储当中中间结果，这样可以加快很多！！

https://leetcode.com/discuss/64291/share-my-java-backtracking-solution

The idea of the solution is clear, but the time complexity of the backtracking method is high. 

Use a HashMap to avoid duplicate computation

Key : InputString.

Value: can win or not.

*/

public boolean canWin(String s) {
    if (s == null || s.length() < 2) {
        return false;
    }
    HashMap<String, Boolean> winMap = new HashMap<String, Boolean>();
    return helper(s, winMap);
}

public boolean helper(String s, HashMap<String, Boolean> winMap) {
    if (winMap.containsKey(s)) {
        return winMap.get(s);
    }
    for (int i = 0; i < s.length() - 1; i++) {
        if (s.startsWith("++", i)) {
            String t = s.substring(0, i) + "--" + s.substring(i+2);
            if (!helper(t, winMap)) {
                winMap.put(s, true); //把当前这个s是不是能赢记录下来
                return true;
            }
        }
    }
    winMap.put(s, false);
    return false;
}


