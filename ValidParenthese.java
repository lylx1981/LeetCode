/*
思路：非常经典的题，用Stack，凡是左边的括号就一直入Stack，凡是遇到一个右边的括号，就从Stack里拿一个出来配对，如果配不上，就返回False, 如果最后Stack空了，说明所有的都配对了，返回True

*/

/**
     * Stack.
     * Use stack to check pair.
     * Whenever there is a valid pair, pop from stack since it is already valid.
     * Quick check: If string length not even, return false.
     * Create a stack.
     * For each character c in the string given:
     * | If c is a left paren, push to stack.
     * | Else if stack is not empty, and c matches with the peek:
     * |   Pop the left paren from stack.
     * | Else return false.
     * After the check, the stack should be empty because all pairs are popped.
     * Otherwise return false.
     */
    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        if (s.length() % 2 != 0) { // String length must be even.
            return false;
        }
        Deque<Character> stk = new ArrayDeque<>();
        for (Character c : s.toCharArray()) {
            if ("({[".indexOf(c) != -1) { // Push left parens.
                stk.push(c);
            } else if (!stk.isEmpty() && isMatch(stk.peek(), c)) {
                stk.pop();
            } else {
                return false;
            }
        }
        return stk.isEmpty();
    }
    
    private boolean isMatch(char c1, char c2) {
        return (c1 == '(' && c2 == ')') || (c1 == '{' && c2 == '}') || (c1 == '[' && c2 == ']');
    }
    
    
