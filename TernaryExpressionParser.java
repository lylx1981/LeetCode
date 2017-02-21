/*思路：自己想到了递归以及用Stack，但是貌似很多人推荐用Stack，但是由于三元操作符的特殊性（？判断完毕后我还要知道：后面的部分才可以，但是这部分可能现在还没有入stack），所以这道题从后往前来遍历表达式，从最后一个字符开始逐个入stack。每遇到一个？，就从Stack里面连续Pop出第一部分和第二部分，同时对当前值进行判断后，看是保留第一部分还是第二部分，然后把保留的部分继续入Stack即可。


Iterate the expression from tail, whenever encounter a character before '?', calculate the right value and push back to stack.

P.S. this code is guaranteed only if "the given expression is valid" base on the requirement.
Initially, I thought approach this problem from start to end. But I found that if condition is "T", I need to look ahead and skip the second arguments of ":", which is kind of stuck. Your solution is great, if from end to start, by end of the loop, the rest values within the stack are those "second arguments" whose evaluated condition is true, am I right?



*/



public String parseTernary(String expression) {
    if (expression == null || expression.length() == 0) return "";
    Deque<Character> stack = new LinkedList<>();

    for (int i = expression.length() - 1; i >= 0; i--) {
        char c = expression.charAt(i);
        if (!stack.isEmpty() && stack.peek() == '?') {

            stack.pop(); //pop '?'
            char first = stack.pop();
            stack.pop(); //pop ':'
            char second = stack.pop();

            if (c == 'T') stack.push(first);
            else stack.push(second);
        } else {
            stack.push(c);
        }
    }

    return String.valueOf(stack.peek());
}
