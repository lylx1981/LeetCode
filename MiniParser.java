/*
思路： 用Stack，注册处理每一层，每一层用一个NestedInteger对象表示。 字符串从前往后依次走，用Stack处理。
碰到 '[', 如果当前curr不为空，则把当前考察的对象放入Stack，然后生成一个新的 NestedInteger进入下一层 
碰到 ','（且这个逗号不是在一个]的后面，说明还在当前Curr里）,则将新的解析出来的数字 (注意这里得判断这个字符串是否为空)加入到当前Curr中。
碰到 ']', 这里是关键，说明当前Curr的NestedInteger结束了，则把最后一个数字加入到当前这个NestedInteger中，然后取出Stack的第一个元素，再加Curr加入到它里面，然后把Curr设置为那个Stack里取出来的NestedInteger，也就是相当于回到了上一层。


This approach will just iterate through every char in the string (no recursion).

If encounters '[', push current NestedInteger to stack and start a new one.
If encounters ']', end current NestedInteger and pop a NestedInteger from stack to continue.
If encounters ',', append a new number to curr NestedInteger, if this comma is not right after a brackets.
Update index l and r, where l shall point to the start of a integer substring, while r shall points to the end+1 of substring.

*/

public NestedInteger deserialize(String s) {
    if (s.isEmpty())
        return null;
    if (s.charAt(0) != '[') // ERROR: special case 第一个字符不是[的话，说明就是一个但数字直接返回即可。
        return new NestedInteger(Integer.valueOf(s));
        
    Stack<NestedInteger> stack = new Stack<>();
    NestedInteger curr = null;
    int l = 0; // l shall point to the start of a number substring; 
               // r shall point to the end+1 of a number substring
    for (int r = 0; r < s.length(); r++) {
        char ch = s.charAt(r);
        if (ch == '[') {
            if (curr != null) {
                stack.push(curr);
            }
            curr = new NestedInteger();
            l = r+1;
        } else if (ch == ']') {
            String num = s.substring(l, r);
            if (!num.isEmpty()) //注意这里得判断这个字符串是否为空
                curr.add(new NestedInteger(Integer.valueOf(num)));
            if (!stack.isEmpty()) {
                NestedInteger pop = stack.pop();
                pop.add(curr);
                curr = pop;
            }
            l = r+1;
        } else if (ch == ',') {
            if (s.charAt(r-1) != ']') {
                String num = s.substring(l, r);
                curr.add(new NestedInteger(Integer.valueOf(num)));
            }
            l = r+1;
        }
    }
    
    return curr;
}

