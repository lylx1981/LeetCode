/*
思路：非常简单的题，双指针，从前往后判断就行了。

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
     
    /**
     * Two Pointers.
     * For i from 0 to m-n:
     * | For j from 0 to n-1:
     * |   If characters are not the same, break
     * |   If j reaches the end of needle, return i.
     * Return -1.
     * Special case:
     * If needle is empty, no need to check , just return 0.
     */
    public int strStr(String haystack, String needle) {
        if (needle.isEmpty()) {
            return 0;
        }
        int m = haystack.length();
        int n = needle.length();
        for (int i = 0; i <= m - n; i++) { // Why m-n? From m-n+1 to m-1 the characters are not enough for needle.
            for (int j = 0; j < n; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
                if (j == n - 1) {
                    return i;
                }
            }
        }
        return -1;
    }
