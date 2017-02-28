/*
思路： 我觉得这道题是最最牛B的解法，太牛了。

自己想的很复杂的问题，这道题用很清晰的代码写出来了，自己虽然想出来了一些思路，但是还是没有头绪。最重要的这道题主要有以下几个关键点：
1. 首先只关注），看清本质，从左到右扫描并计数，当（的出现的数量比）还少的时候，说明当前）是错误的，也就是当前）之前没有相应的（匹配，那么就需要开始进行调整，具体调整过程中，又要看透：
2. 将当前出错的） 之前出现的任意一个） 去掉，都可以重新构成一个新的合法的串，这个时候进入下一层递归，对剩余的串再继续进行判断。
3. 但是为了避免生成重复的串，如果几个）））同时连在一起，我们只关注第一个，也就是删除第一个所构成的合法串将作为Res的Candidate。
4. 另外，每次进入到一个新的递归时，对于新的出错的），只需要考察在上层的步骤3中Removal 位置后的）即可。
5. 对于（出错的情况，可以和）分开来处理，直接将当前已经对（ 进行过处理完毕的s进行Reverse后，利用同样的代码对（出错的情况也进行考察替换，最后再把处理完的结果加入Res即可。



/*Explanation:
We all know how to check a string of parentheses is valid using a stack. Or even simpler use a counter.
The counter will increase when it is ‘(‘ and decrease when it is ‘)’. Whenever the counter is negative, we have more ‘)’ than ‘(‘ in the prefix.

To make the prefix valid, we need to remove a ‘)’. The problem is: which one? The answer is any one in the prefix. However, if we remove any one, we will generate duplicate results, for example: s = ()), we can remove s[1] or s[2] but the result is the same (). Thus, we restrict ourself to remove the first ) in a series of concecutive )s.

After the removal, the prefix is then valid. We then call the function recursively to solve the rest of the string. However, we need to keep another information: the last removal position. If we do not have this position, we will generate duplicate by removing two ‘)’ in two steps only with a different order.
For this, we keep tracking the last removal position and only remove ‘)’ after that.

Now one may ask. What about ‘(‘? What if s = ‘(()(()’ in which we need remove ‘(‘?
The answer is: do the same from right to left.
However a cleverer idea is: reverse the string and reuse the code!
Here is the final implement in Java.

Java*/

public List<String> removeInvalidParentheses(String s) {
    List<String> ans = new ArrayList<>();
    remove(s, ans, 0, 0, new char[]{'(', ')'});
    return ans;
}

public void remove(String s, List<String> ans, int last_i, int last_j,  char[] par) {
    for (int stack = 0, i = last_i; i < s.length(); ++i) {
        if (s.charAt(i) == par[0]) stack++;
        if (s.charAt(i) == par[1]) stack--;
        if (stack >= 0) continue;
        for (int j = last_j; j <= i; ++j) //从last_j开始即可
            if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1])) //要么当前是last_j位，那么直接可以因为它是第一个出现的。或者，如果当前已经不是在last_j位,那么只有j-1那一位不是par[1]才可以，这样才可以保证不生成重复的
                remove(s.substring(0, j) + s.substring(j + 1, s.length()), ans, i, j, par); //删除掉第j位的），并组合成一个新串，新串继续进入下一层递归
                //特别注意，当前的j的值变成下一层递归的last_j,但是，因为上面已经把原来第j位的那个） 去掉了，也就是字符串已经发生了变化，所以现在的第j位其实是没有删除之前的j+1位（也就是删除了第j位，那么原来的j+1位现在就变成了第j位）！！！也就是从原来未删除时的j+1位继续开始判断就行了，这里非常细节，但是非常重要！！！！
        return;
    }
    //只有不进上面的for循环，才会走到这一步，也就是说当前对（ 已经处理完了，已经从头到尾扫过一遍了。
    String reversed = new StringBuilder(s).reverse().toString(); //将当前s逆序排列
    if (par[0] == '(') // finished left to right //这里是递归里面有时还是会出现即使在
        remove(reversed, ans, 0, 0, new char[]{')', '('}); //因为这里是会出现递归的效应，所以要额外的再来一层判断，也就是说如果par[0] == '('，说明现在还是在判断“）”的过程中，那么这个时候及需要从这里进入判断“（”的过程了，也就是判断完）后现在开始判断（。否则如果已经par[0] == '）'走到这里说明其实是“（”的过程也结束了（特别地，上面一行继续进行了第二次的Rerverse操作其实就是再次把当前串再Rerverse一遍。因为串前后被Reverse了两次，那么现在串就是一个正常顺序的最终结果了，这个地方非常非常巧！！！！）。如果是这样的情况，现在就直接将当前结果加入Res就行了。

    else // finished right to left
        ans.add(reversed);
}
