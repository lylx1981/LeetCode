/*

思路： BackTracking, 和自己想的差不多，只是更简洁。每次只要决定要不要缩写当前字符（一共两种选择，所以需要回溯）。第一种方案是，如果要缩写，就把直接增加缩写计数器Count++，继续下一层循环。否则，如果决定不缩写，就把当前Count的数编程字符串加到当前中间结果字符串当中，再加当前考察字符，再把当前Count清零，然后进入下一层递归即可。

The idea is: for every character, we can keep it or abbreviate it. To keep it, we add it to the current solution and carry on backtracking. To abbreviate it, we omit it in the current solution, but increment the count, which indicates how many characters have we abbreviated. When we reach the end or need to put a character in the current solution, and count is bigger than zero, we add the number into the solution.
*/



  public List<String> generateAbbreviations(String word){
        List<String> ret = new ArrayList<String>();
        backtrack(ret, word, 0, "", 0);

        return ret;
    }

    private void backtrack(List<String> ret, String word, int pos, String cur, int count){
        if(pos==word.length()){
            if(count > 0) cur += count;
            ret.add(cur);
        }
        else{
            backtrack(ret, word, pos + 1, cur, count + 1);
            backtrack(ret, word, pos+1, cur + (count>0 ? count : "") + word.charAt(pos), 0);
        }
    }
