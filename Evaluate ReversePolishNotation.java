/*
思路：用Stack，遇见数字就放进Stack，遇见一个符号，就从Stack里连续取两个元素进行计算，然后继续把计算结果放进Stack。

最后剩下的就是计算结果。

     */
 public class Solution {

    /**
     * Stack.
     * Suppose all reverse polish are valid.
     * For each token t in tokens:
     * | If t is an operator:
     * |   Pop two numbers and do the calculation.
     * |   Push result onto the stack.
     * | Else t should be a number:
     * |   Just push t onto the stack.
     * Return the top value of stack as the result.
     */
    public int evalRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return 0;
        }
        Deque<Integer> s = new ArrayDeque<>();
        for (int i = 0; i < tokens.length; i++) {
            switch (tokens[i]) {
                case "+":
                    s.push(s.pop() + s.pop());
                    break;
                case "-":
                    s.push(-s.pop() + s.pop());
                    break;
                case "*":
                    s.push(s.pop() * s.pop());
                    break;
                case "/":
                    int num1 = s.pop();
                    int num2 = s.pop();
                    s.push(num2 / num1);
                    break;
                default:
                    s.push(Integer.valueOf(tokens[i]));
                    break;
            }
        }
        return s.isEmpty() ? 0 : s.peek();
    }
}
