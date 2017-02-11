
    /*
    思路：这道题用Divide&Conque以及递归的思路来解决非常巧，值得学习。就是以如下的想法来思考：其实通过观察可以发现，其实不同的加括号的结果就是导致给哪个操作符高的优先级。所以可以遍历每个操作符，当遍历那个操作符的时候，就相当于给它了高的优先级，然后再这个基础上，以它为大中心划分为左右两部分，分别单独计算其左右两部分可以能够获得的值（依赖于递归），然后最后对左边两边每个可能的值都用当前操作符计算一下，这就是最后一共可能的结果，非常巧！
    
    另外注意 ((2*(3-4)*5)) 并不是一个有效的模式，是不允许存在的，也就是说如果一对括号之间有多个操作符，那么最多只能有一个没有被其他括号括住，而这个例子中从2开始到5结束的那个括号中间包含了2个乘号，这两个乘号都没有被其他括号扩住，所以这个是个不合法的模式，不应该在结果集中，而上面提到的Solution是不会包含这种模式的，因为这种模式是分不出来到底那个操作符是高优先级的操作符（比如那2个乘号）
     */
   
public class DifferentWaysToAddParentheses {

    /**
     * Divide and Conquer.
     * Divide the input into sub-strings according to the operator: left and right.
     * Stop until there is no operator in the string, parse the integer and add it to result list.
     * Then combine left and right result lists with the operator to generate result.
     */
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '*' || c == '-' || c == '+') {
                List<Integer> left = diffWaysToCompute(input.substring(0, i));
                List<Integer> right = diffWaysToCompute(input.substring(i + 1, input.length()));
                for (int j = 0; j < left.size(); j++) {
                    for (int k = 0; k < right.size(); k++) {
                        res.add(calculate(c, left.get(j), right.get(k)));
                    }
                }
            }
        }
        if (res.size() == 0) {
            res.add(Integer.parseInt(input));
        }
        return res;
    }

    private int calculate(char op, int a, int b) {
        return op == '*' ? a * b : op == '+' ? a + b : a - b;
    }

}
